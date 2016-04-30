package com.microservicetest.model;

import java.io.Serializable;

import com.microservicestest.model.validation.core.TestValidationsBuilder;

public class TestSpecificationAbstract<T extends Serializable> {

	protected TestValidationsBuilder validationData;

}
