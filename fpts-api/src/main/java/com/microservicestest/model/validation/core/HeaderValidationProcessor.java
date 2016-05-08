package com.microservicestest.model.validation.core;

import java.io.IOException;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.microservicetest.util.ValidationType;

class HeaderValidationProcessor extends ValidationProcessorAbstract {

	@Override
	public ValidationType getValidationType() {
		return ValidationType.HEADER;
	}

	@Override
	public boolean validate(Map<String, String> toCompare) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public JsonNode getJSONForValidation() throws IOException {
		ArrayNode array = node.arrayNode();
		valuesMap.forEach((key, value) -> array.addObject().put(key, value));

		return array;
	}

}
