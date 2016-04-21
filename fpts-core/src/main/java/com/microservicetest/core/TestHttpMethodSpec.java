package com.microservicetest.core;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.microservicetest.model.ValidationData;
import com.microservicetest.util.HttpMethodEnum;
import com.microservicetest.util.SpecResponseFields;

/**
 * This class models the response for the OPTIONS request.<br>
 * All the fields that need to be present on the response are mapped in this
 * class and the response is wrapped in a JSON object. <br>
 * {@link SpecResponseFields#DESCRIPTION} - Contains a human readable
 * description for the service operation which the test specification is
 * designed to test <br>
 * {@link SpecResponseFields#PARAMETER} - Contains the data that will be used as
 * the parameter for the test, can be JSON objects or get parameters for GET
 * requests <br>
 * {@link SpecResponseFields#PARAMETER_SCHEMA} - The JSON-Schema for the
 * parameter <br>
 * {@link SpecResponseFields#VALIDATION} - Validation data to be used to
 * validate the response from the server after the request using the test
 * parameter
 *
 * @author andre
 *
 */
public class TestHttpMethodSpec {

	static ObjectMapper mapper = new ObjectMapper();

	static {
		mapper.setSerializationInclusion(Include.NON_NULL);
	}

	private ObjectNode spec = new ObjectNode(mapper.getNodeFactory());

	protected TestHttpMethodSpec(HttpMethodEnum httpMethod, String description, Serializable parameter,
			ValidationData validation, String jsonSchema) {
		ObjectNode methodObj = this.spec.putObject(httpMethod.toString());

		methodObj.put(SpecResponseFields.DESCRIPTION.getFieldName(), description);
		try {
			methodObj.put(SpecResponseFields.PARAMETER.getFieldName(), mapper.writeValueAsString(parameter));
			methodObj.put(SpecResponseFields.PARAMETER_SCHEMA.getFieldName(), jsonSchema);
			methodObj.put(SpecResponseFields.VALIDATION.getFieldName(), mapper.writeValueAsString(validation));
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static TestHttpMethodSpec build(HttpMethodEnum httpMethod, String description, Serializable parameter,
			ValidationData validation, String jsonSchema) {
		return new TestHttpMethodSpec(httpMethod, description, parameter, validation, jsonSchema);
	}

	public static TestHttpMethodSpec joinSpec(TestHttpMethodSpec spec, HttpMethodEnum httpMethod, String description,
			Serializable parameter, ValidationData validation, String jsonSchema) {
		ObjectNode methodObj = spec.getSpec().putObject(httpMethod.toString());

		methodObj.put(SpecResponseFields.DESCRIPTION.getFieldName(), description);
		try {
			methodObj.put(SpecResponseFields.PARAMETER.getFieldName(), mapper.writeValueAsString(parameter));
			methodObj.put(SpecResponseFields.PARAMETER_SCHEMA.getFieldName(), jsonSchema);
			methodObj.put(SpecResponseFields.VALIDATION.getFieldName(), mapper.writeValueAsString(validation));
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return spec;
	}

	public ObjectNode getSpec() {
		return this.spec;
	}

}
