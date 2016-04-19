package com.microservicetest.util;

/**
 * This enum have the fields that will be included in the response for the test
 * specification.
 *
 * @author andre
 *
 */
public enum SpecResponseFields {
	PARAMETER("parameter"), PARAMETER_SCHEMA("parameter-schema"), DESCRIPTION("description"), VALIDATION("validation");

	private String fieldName;

	private SpecResponseFields(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getFieldName() {
		return this.fieldName;
	}
}
