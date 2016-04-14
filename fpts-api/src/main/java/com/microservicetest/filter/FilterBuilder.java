package com.microservicetest.filter;

import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

public class FilterBuilder {

	public static javax.servlet.Filter buildPerformanceFilter(RequestMappingHandlerMapping requestMappingHandler) {
		Class filter;
		try {
			filter = Class.forName("com.microservicetest.core.PerformanceTestFilter");
		} catch (Exception e) {
			// Filter not available
			return new DummyFilter();
		}
		try {
			return (javax.servlet.Filter) filter.getConstructor(RequestMappingHandlerMapping.class)
					.newInstance(requestMappingHandler);
		} catch (Exception e) {
			return new DummyFilter();
		}
	}

}
