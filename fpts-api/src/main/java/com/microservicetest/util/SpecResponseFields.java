package com.microservicetest.util;

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
