package com.microservicestest.model.validation.core;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.microservicestest.model.validation.interfaces.IKeyValueValidationProcessor;
import com.microservicetest.util.ResourceController;
import com.microservicetest.util.ResourceType;

public abstract class ValidationProcessorAbstract implements IKeyValueValidationProcessor {

	/**
	 * Holds the pairs: field, value to be used during validation
	 */
	protected Map<String, String> valuesMap = new HashMap<String, String>();

	public abstract JsonNode getJSONForValidation() throws IOException;

	protected ObjectMapper mapper = ResourceController.getResource(ResourceType.OBJECT_MAPPER);
	protected ObjectNode node = mapper.createObjectNode();

	@Override
	public void putContent(String field, String data) {
		valuesMap.put(field, data);
	}

	public void setContentMap(Map<String, String> valuesMap) {
		this.valuesMap.putAll(valuesMap);
	}

}
