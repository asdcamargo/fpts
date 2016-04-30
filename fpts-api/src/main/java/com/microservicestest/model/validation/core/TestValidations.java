package com.microservicestest.model.validation.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.microservicetest.util.ResourceController;
import com.microservicetest.util.ResourceType;
import com.microservicetest.util.ValidationType;

public class TestValidations {

	private Map<ValidationType, List<ValidationProcessorAbstract>> validations = new HashMap<ValidationType, List<ValidationProcessorAbstract>>();
	private ObjectMapper mapper = ResourceController.getResource(ResourceType.OBJECT_MAPPER);
	private ObjectNode validationJson = new ObjectNode(mapper.getNodeFactory());

	public TestValidations() {
	}

	public void addValidation(ValidationProcessorAbstract validation) throws JsonProcessingException {
		// Add to validations list
		if (!validations.containsKey(validation.getValidationType())) {
			validations.put(validation.getValidationType(), new ArrayList<ValidationProcessorAbstract>());
		}
		validations.get(validation.getValidationType()).add(validation);
		appendJSON(validation);
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
