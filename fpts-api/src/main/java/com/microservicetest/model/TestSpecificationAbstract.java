package com.microservicetest.model;

import java.io.Serializable;

import com.microservicestest.model.validation.core.TestValidations;

public class TestSpecificationAbstract<T extends Serializable> {

	protected TestValidations validationData;

}
