package com.microservicestest.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.microservicetest.util.HttpMethodEnum;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface PerformanceTest {

	HttpMethodEnum httpMethod() default HttpMethodEnum.GET;

	String description() default "description";

	String path();

}
