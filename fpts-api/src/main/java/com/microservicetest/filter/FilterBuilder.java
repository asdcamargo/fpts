package com.microservicetest.filter;

import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

/**
 * Class that builds thru reflection the web filter during runtime. Can be the
 * {@link DummyFilter} if the core library is not available. <br>
 * Otherwise will be the web filter from the core library.
 * 
 * @author andre
 *
 */
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
