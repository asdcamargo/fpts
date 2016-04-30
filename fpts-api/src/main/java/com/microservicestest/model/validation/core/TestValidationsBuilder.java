package com.microservicestest.model.validation.core;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.microservicetest.util.HttpHeaderFields;
import com.microservicetest.util.ResourceController;
import com.microservicetest.util.ResourceType;
import com.microservicetest.util.ValidationType;

public class TestValidationsBuilder {

	private Map<ValidationType, List<ValidationProcessorAbstract>> validations = new HashMap<ValidationType, List<ValidationProcessorAbstract>>();
	private ObjectMapper mapper = ResourceController.getResource(ResourceType.OBJECT_MAPPER);
	private ObjectNode validationJson = new ObjectNode(mapper.getNodeFactory());

	public TestValidationsBuilder() {
	}

	public void addValidation(ValidationProcessorAbstract validation) throws JsonProcessingException {
		// Add to validations list
		if (!validations.containsKey(validation.getValidationType())) {
			validations.put(validation.getValidationType(), new ArrayList<ValidationProcessorAbstract>());
		}
		validations.get(validation.getValidationType()).add(validation);
		appendJSON(validation);
	}

	public TestValidationsBuilder buildBodyValidationFromEntity(Serializable entity)
			throws JsonParseException, JsonMappingException, IOException {
		BodyValidationProcessor bodyValidation = (BodyValidationProcessor) ValidationProcessorFactory
				.getProcessorForType(ValidationType.BODY);
		bodyValidation.putFieldsFromEntity(entity);
		this.addValidation(bodyValidation);
		return this;
	}

	public TestValidationsBuilder buildHeaderStatus200AndEntityBody(Serializable entity) throws IOException {
		// Validation for header 200
		HeaderValidationProcessor headerValidation = (HeaderValidationProcessor) ValidationProcessorFactory
				.getProcessorForType(ValidationType.HEADER);
		headerValidation.putContent(HttpHeaderFields.STATUS.getField(), "200");
		this.addValidation(headerValidation);

		// Validation for the body
		buildBodyValidationFromEntity(entity);
		return this;
	}

	private void appendJSON(ValidationProcessorAbstract validation) throws JsonProcessingException {
		if (validationJson.get(validation.getValidationType().toString()) == null) {
			validationJson.putObject(validation.getValidationType().toString());
		}
		validationJson.putObject(validation.getJSONForValidation());
	}

	public String getJSONRepresentation() {
		return this.validationJson.toString();
	}
}
