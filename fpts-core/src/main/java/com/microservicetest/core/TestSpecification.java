package com.microservicetest.core;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.microservicestest.enums.HttpMethodEnum;
import com.microservicestest.enums.ResourceType;
import com.microservicestest.enums.SpecResponseFields;
import com.microservicestest.model.validation.core.TestValidationsBuilder;
import com.microservicetest.util.ResourceController;

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
public class TestSpecification {

	static Logger logger = LoggerFactory.getLogger(TestSpecification.class);

	static ObjectMapper mapper = ResourceController.getResource(ResourceType.OBJECT_MAPPER);

	private ObjectNode spec = new ObjectNode(mapper.getNodeFactory());

	protected TestSpecification(HttpMethodEnum httpMethod, String description, Serializable parameter,
			TestValidationsBuilder validation, String jsonSchema) {
		ObjectNode methodObj = this.spec.putObject(httpMethod.toString());

		methodObj.put(SpecResponseFields.DESCRIPTION.getFieldName(), description);
		try {
			methodObj.put(SpecResponseFields.PARAMETER.getFieldName(), mapper.writeValueAsString(parameter));
			methodObj.put(SpecResponseFields.PARAMETER_SCHEMA.getFieldName(), jsonSchema);
			methodObj.put(SpecResponseFields.VALIDATION.getFieldName(), validation.getJSONRepresentation());
		} catch (JsonProcessingException e) {
			logger.error("Error while building the specification", e);
		}
	}

	public static TestSpecification build(HttpMethodEnum httpMethod, String description, Serializable parameter,
			TestValidationsBuilder validation, String jsonSchema) {
		return new TestSpecification(httpMethod, description, parameter, validation, jsonSchema);
	}

	/**
	 * This method append a new specification to an already existing one.
	 *
	 * @param spec
	 *            The specification to be append
	 * @param httpMethod
	 *            Http method for the new specification
	 * @param description
	 *            Description for the new specification
	 * @param parameter
	 *            The request parameter for the new specification
	 * @param validation
	 *            The validation data for the new specification
	 * @param jsonSchema
	 *            The JSON Schema for the paramater
	 * @return The result specification with the new one added
	 */
	public static TestSpecification joinSpec(TestSpecification spec, HttpMethodEnum httpMethod, String description,
			Serializable parameter, TestValidationsBuilder validation, String jsonSchema) {
		ObjectNode methodObj = spec.getSpec().putObject(httpMethod.toString());

		methodObj.put(SpecResponseFields.DESCRIPTION.getFieldName(), description);
		try {
			methodObj.put(SpecResponseFields.PARAMETER.getFieldName(), mapper.writeValueAsString(parameter));
			methodObj.put(SpecResponseFields.PARAMETER_SCHEMA.getFieldName(), jsonSchema);
			methodObj.put(SpecResponseFields.VALIDATION.getFieldName(), mapper.writeValueAsString(validation));
		} catch (JsonProcessingException e) {
			logger.error("Error while joining the specification", e);
		}
		return spec;
	}

	public ObjectNode getSpec() {
		return this.spec;
	}

}
