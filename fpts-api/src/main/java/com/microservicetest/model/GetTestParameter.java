package com.microservicetest.model;

import java.io.Serializable;

/**
 * This class represents a pair of values to be used as parameter in get
 * requests.<br>
 * A typical GET request will contain a list of parameter and value pairs.
 *
 * @author andre
 *
 */
public class GetTestParameter implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -457401449569195463L;
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
