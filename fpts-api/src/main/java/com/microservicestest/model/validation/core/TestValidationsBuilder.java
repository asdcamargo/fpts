package com.microservicestest.model.validation.core;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.microservicestest.enums.HttpHeaderFields;
import com.microservicestest.enums.ResourceType;
import com.microservicestest.enums.ValidationType;
import com.microservicetest.util.ResourceController;

public class TestValidationsBuilder {

	private Map<ValidationType, ValidationProcessorAbstract> validations = new HashMap<ValidationType, ValidationProcessorAbstract>();
	private ObjectMapper mapper = ResourceController.getResource(ResourceType.OBJECT_MAPPER);
	private ObjectNode validationJson = new ObjectNode(mapper.getNodeFactory());

	public TestValidationsBuilder() {
	}

	public void addValidation(ValidationProcessorAbstract validation) throws IOException {
		// Add to validations list
		validations.put(validation.getValidationType(), validation);

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

	public TestValidationsBuilder addHeaderParameter(HttpHeaderFields field, String value) throws IOException {
		// Validation for header 200
		HeaderValidationProcessor headerValidation;
		if (validations.containsKey(ValidationType.HEADER)) {
			headerValidation = (HeaderValidationProcessor) validations.get(ValidationType.HEADER);
		} else {
			headerValidation = (HeaderValidationProcessor) ValidationProcessorFactory
					.getProcessorForType(ValidationType.HEADER);
		}
		headerValidation.putContent(field.getField(), value);
		this.addValidation(headerValidation);
		return this;
	}

	private void appendJSON(ValidationProcessorAbstract validation) throws IOException {
		validationJson.replace(validation.getValidationType().toString(), validation.getJSONForValidation());
	}

	public String getJSONRepresentation() {
		return this.validationJson.toString();
	}
}
