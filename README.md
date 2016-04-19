# FPTS - Framework for Performance Test Specification for Microservices

This framework was build to improve the performance test execution for applications build under Microservices architecture.

Knowing that the test task is one of the main challenges in a Microservices application, the main goal is to provide a manner to automate the performance tests on each Microservice. For that, the developers need to define a test specification for each operation of the service. The specification will then be used by an external application to run performance tests in an automated way.

## Application

* **Web Framework:** [Spring Web MVC](https://github.com/spring-projects/spring-framework.git) v4.1.6

* **JSON library:** [Jackson](https://github.com/FasterXML/jackson.git) v2.5.4 

* **Components:**
              
*API : Provide means to allow developers to define methods that will return test specifications 
 for the service operations. 
 *CORE: Work on runtime to process income requests and return the test specification.

## Main Features

* Framework API: This allows the developer to remove the framework from the application during the build. This is particularly useful when we are building the project for production deployment as we may not want to expose the test methods.
* Annotation capabilities: This provide a clean and simple way for the developer to define the specifications. In addition, this facility allows the developer to choose which service methods will be exposed. 
* Dynamic data for testing: This refers to the possibility of using the database to fetch data for the test specification and validation. With this, the developer can bypass the changes that the data may have which could lead to failure in the tests. Let us say that we want to test a GET operation and we will get some data by its ID. Therefore, we can query the database and get the object with ID = 1 and provide the same object for validation in the specification. Although this object may change, the Framework will always build the updated data and the test application will have the refreshed data to use. 
* Annotation processor: This feature checks if the return type for the annotated method is valid (must be of type ‘TestSpec’). This check happens during compile time, which prevents errors in runtime and allows the developer to fix these inconsistencies quickly. 
* Build features to validate data: common validation types have been created in order to append the validation data to the specification, such as: validate only header status and validate for both header and body. 

## How does it work

* 1) The test application will send a OPTIONS request to the service URI. 
* 2) The Framework will filter this request and compose the test specification using the information defined by the developer during the implementation of the service.
* 3) The specification is returned in JSON format to the test application.

All other requests that are not OPTIONS request will be forwarded to the service endpoint. 

The framework will only perform its action if the core library is found in the build path. So, if we are building the application for restricted environments such as production we can remove this library from the build and no code change is needed as we will keep the API.

## How to use

* **Dependency** This framework is design to be used along with Spring Boot Framework [https://github.com/spring-projects/spring-boot.git]
            
* **First step** The first thing to do is to add fpts-core and fpts-api to your project

* **Second step** The next step is to define your methods that will return the specification. Mainly it will be one for each service operation. This methods will be placed in your service class (the RESTController class). 
 
```
  @PerformanceTest(path = "/rest/finance", httpMethod = HttpMethodEnum.GET, description = "Get transaction by id")
	public TestSpec<GetTestParameter> getTestSpecForGetById() {
		GetTestParameter testParameter = new GetTestParameter("id", "1");
		FinancialTransaction testObj = new FinancialTransaction("000023989", "766", "10", "2015");
		TestSpec<GetTestParameter> testSpec = new TestSpec<GetTestParameter>(testParameter,
				ValidationData.buildWithBodyAndHeader200(testObj));
		return testSpec;
	}
```
 
 * **Third step** The final step if to add the framework filter in your application configuration. If you choose to place the configuration on code you will add the following code to your Configuration class.

```
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
```

## Licenses

* This code is licensed under the [MIT License].
