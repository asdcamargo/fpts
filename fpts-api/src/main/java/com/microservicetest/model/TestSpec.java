package com.microservicetest.model;

import java.io.Serializable;

public class TestSpec<T extends Serializable> {

	private Serializable testParameter;

	private ValidationData validationData;

	public TestSpec(Serializable testParameter, ValidationData validationData) {
		super();
		this.testParameter = testParameter;
		this.validationData = validationData;
	}

	public Serializable getTestParameter() {
		return this.testParameter;
	}

	public ValidationData getValidationData() {
		return this.validationData;
	}

}
