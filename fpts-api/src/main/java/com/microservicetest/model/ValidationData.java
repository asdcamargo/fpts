package com.microservicetest.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ValidationData {

	private Map<String, String> headerValidation;

	private Serializable bodyData;

	public ValidationData(Map<String, String> headerValidation, Serializable bodyData) {
		super();
		this.headerValidation = headerValidation;
		this.bodyData = bodyData;
	}

	public boolean hasHeaderValidation() {
		return this.headerValidation != null && this.headerValidation.size() > 0;
	}

	public Serializable getBodyData() {
		return this.bodyData;
	}

	public Map<String, String> getHeaderData() {
		return this.headerValidation;
	}

	public static ValidationData buildWithHeaderStatus200() {
		Map<String, String> headerValidation = new HashMap<String, String>();
		headerValidation.put("status", "200");
		return new ValidationData(headerValidation, null);
	}

	public static ValidationData buildWithBodyAndHeader200(Serializable bodyData) {
		Map<String, String> headerValidation = new HashMap<String, String>();
		headerValidation.put("status", "200");
		return new ValidationData(headerValidation, bodyData);
	}
}
