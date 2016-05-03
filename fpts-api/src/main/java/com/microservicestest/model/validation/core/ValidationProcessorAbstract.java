package com.microservicestest.model.validation.core;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservicestest.model.validation.interfaces.IKeyValueValidationProcessor;
import com.microservicetest.util.ResourceController;
import com.microservicetest.util.ResourceType;

public abstract class ValidationProcessorAbstract implements IKeyValueValidationProcessor {

	/**
	 * Holds the pairs: field, value to be used during validation
	 */
	protected Map<String, String> valuesMap = new HashMap<String, String>();

	public String getJSONForValidation() throws JsonProcessingException {
		ObjectMapper mapper = ResourceController.getResource(ResourceType.OBJECT_MAPPER);
		return mapper.writeValueAsString(valuesMap);
	}

	@Override
	public void putContent(String field, String data) {
		valuesMap.put(field, data);
	}

	public void setContentMap(Map<String, String> valuesMap) {
		this.valuesMap.putAll(valuesMap);
	}

}
