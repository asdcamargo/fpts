package com.microservicestest.model.validation.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.microservicestest.model.validation.interfaces.IValidation;
import com.microservicetest.util.ResourceController;
import com.microservicetest.util.ResourceType;

public class TestValidations implements IValidation {

	private List<ValidationProcessorAbstract> validations = new ArrayList<ValidationProcessorAbstract>();
	private ObjectMapper mapper = ResourceController.getResource(ResourceType.OBJECT_MAPPER);
	private ObjectNode validationJson = new ObjectNode(mapper.getNodeFactory());

	public TestValidations() {
	}

	public void addValidation(ValidationProcessorAbstract validation) throws JsonProcessingException {
		// Add to validations list
		validations.add(validation);
		if (validationJson.get(validation.getValidationType().toString()) == null) {
			validationJson.putObject(validation.getValidationType().toString());
		}
		validationJson.putObject(validation.getJSONForValidation());
	}

	@Override
	public boolean validate(Map<String, String> toCompare) {
		// TODO Auto-generated method stub
		return false;
	}

	public String getJSONRepresentation() {
		return this.validationJson.toString();
	}
}
