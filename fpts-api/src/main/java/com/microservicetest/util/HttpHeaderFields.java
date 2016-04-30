package com.microservicetest.util;

public enum HttpHeaderFields {
	CONTENT_ENCODING("Content-Encoding"), ALLOW("Allow"), STATUS("Status"), CONTENT_LENGTH(
			"Content-Length"), CONTENT_TYPE("Content-Type");

	private String field;

	private HttpHeaderFields(String field) {
		this.field = field;
	}

	public String getField() {
		return field;
	}
}
