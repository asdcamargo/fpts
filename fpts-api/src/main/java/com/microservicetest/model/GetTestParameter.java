package com.microservicetest.model;

import java.io.Serializable;

public class GetTestParameter implements Serializable {

	private String parameter, value;

	public GetTestParameter(String parameter, String value) {
		super();
		this.parameter = parameter;
		this.value = value;
	}

	public String getParameter() {
		return this.parameter;
	}

	public String getValue() {
		return this.value;
	}
}
