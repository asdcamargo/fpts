package com.finance.company.journal.main;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.MultipartConfigElement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.boot.context.embedded.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import com.microservicetest.filter.FilterBuilder;

@Configuration
@EnableMongoRepositories(basePackages = "com.finance.company.journal.repository")
@ComponentScan(basePackages = "com.finance.company.journal.service")
class ApplicationConfig {

	@Bean
	public static PropertyPlaceholderConfigurer propertyPlaceholderConfigurer() {
		PropertyPlaceholderConfigurer ppc = new PropertyPlaceholderConfigurer();
		ppc.setLocations(new ClassPathResource("/application.properties"));
		return ppc;
	}

	@Bean
	public MultipartConfigElement multipartConfigElement() {
		MultipartConfigFactory factory = new MultipartConfigFactory();
		factory.setMaxFileSize(10240);
		factory.setMaxRequestSize(10240);
		return factory.createMultipartConfig();
	}

	@Bean
	@Autowired
	public FilterRegistrationBean performanceTestFilter(RequestMappingHandlerMapping requestMappingHandler) {
		FilterRegistrationBean filterRegistration = new FilterRegistrationBean();
		filterRegistration.setFilter(FilterBuilder.buildPerformanceFilter(requestMappingHandler));
		List<String> urlPatterns = new ArrayList<String>();
		urlPatterns.add("/*");
		filterRegistration.setUrlPatterns(urlPatterns);
		return filterRegistration;
	}

}