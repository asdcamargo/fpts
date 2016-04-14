package com.microservicetest.core;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import com.microservicestest.annotation.PerformanceTest;
import com.microservicetest.model.TestSpec;
import com.microservicetest.util.HttpMethodEnum;
import com.microservicetest.util.JSONConverter;

@WebFilter(urlPatterns = "/*")
public class PerformanceTestFilter implements Filter {

	static Logger logger = LoggerFactory.getLogger(PerformanceTestFilter.class);

	private static final String OPTIONS_METHOD_STR = "OPTIONS";

	private RequestMappingHandlerMapping requestMappingHandler;

	public PerformanceTestFilter(RequestMappingHandlerMapping requestMappingHandler) {
		this.requestMappingHandler = requestMappingHandler;
	}

	private void extractTestSpec(RequestMappingHandlerMapping requestMappingHandler, ServletResponse response,
			String requestedPath) {
		Iterator<RequestMappingInfo> infoIterator = this.requestMappingHandler.getHandlerMethods().keySet().iterator();
		for (RequestMappingInfo info = infoIterator.next(); infoIterator.hasNext(); info = infoIterator.next()) {
			if (info.getPatternsCondition().getPatterns().contains(requestedPath)) {
				HandlerMethod handler = this.requestMappingHandler.getHandlerMethods().get(info);
				List<Method> methodsWithTestAnnotation = getMethodsAnnotatedWith(handler.getBeanType(),
						PerformanceTest.class);
				TestHttpMethodSpec spec = null;
				for (Method method : methodsWithTestAnnotation) {
					PerformanceTest perfAnnotation = method.getAnnotation(PerformanceTest.class);
					HttpMethodEnum httpMethod = perfAnnotation.httpMethod();
					String description = perfAnnotation.description();
					String path = perfAnnotation.path();
					// Check if the requested path is the same as the one
					// annotated in the service method
					if (path.equals(requestedPath)) {
						try {
							TestSpec parametersForTest = (TestSpec) method.invoke(handler.getBeanType().newInstance());
							String jsonSchema = JSONConverter
									.getJsonSchema(parametersForTest.getTestParameter().getClass());
							if (spec != null) {
								TestHttpMethodSpec.joinSpec(spec, httpMethod, description,
										parametersForTest.getTestParameter(), parametersForTest.getValidationData(),
										jsonSchema);
							} else {
								spec = TestHttpMethodSpec.build(httpMethod, description,
										parametersForTest.getTestParameter(), parametersForTest.getValidationData(),
										jsonSchema);
							}
							if (logger.isDebugEnabled()) {
								logger.debug("Generated spec: " + spec.getSpec().toString());
							}

						} catch (Exception e) {
							logger.error("Error while writing response to OPTIONS request", e);
						}
					}
				}
				try {
					if (spec != null) {
						response.getOutputStream().write(spec.getSpec().toString().getBytes());
						return;
					}
				} catch (IOException e) {
					logger.error("Error while writing response to OPTIONS request", e);
				}
			}
		}

	}

	private static List<Method> getMethodsAnnotatedWith(final Class<?> type,
			final Class<? extends Annotation> annotation) {
		final List<Method> methods = new ArrayList<Method>();
		Class<?> klass = type;
		// iterate though the list of methods declared in the class
		// represented by klass variable, and add those annotated with the
		// specified annotation
		final List<Method> allMethods = new ArrayList<Method>(Arrays.asList(klass.getDeclaredMethods()));
		for (final Method method : allMethods) {
			if (method.isAnnotationPresent(annotation)) {
				methods.add(method);
			}
		}
		return methods;
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;

		String uri = httpRequest.getRequestURL().toString();

		if (logger.isDebugEnabled()) {
			logger.debug("Entering PerformanceTestFilter");
		}

		if (OPTIONS_METHOD_STR.equals(httpRequest.getMethod())) {
			logger.info("Working on response for OPTIONS request to: " + uri);
			extractTestSpec(this.requestMappingHandler, response, httpRequest.getServletPath());
			return;
		}

		chain.doFilter(request, response);

	}

	public void destroy() {
		// TODO Auto-generated method stub

	}

	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub

	}

}
