package com.microservicestest.model.validation.core;

import java.io.IOException;
import java.io.Serializable;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservicetest.util.JSONConverter;
import com.microservicetest.util.ResourceController;
import com.microservicetest.util.ResourceType;
import com.microservicetest.util.ValidationType;

/**
 * This class is used to storage validation data to be used to validate the
 * response body for a test request.<br>
 *
 * @author andre
 *
 */
class BodyValidationProcessor extends ValidationProcessorAbstract {

	@Override
	public ValidationType getValidationType() {
		return ValidationType.BODY;
	}

	@Override
	public boolean validate(Map<String, String> toCompare) {
		// TODO Auto-generated method stub
		return false;
	}

	public void putFieldsFromEntity(Serializable entity) throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper om = ResourceController.getResource(ResourceType.OBJECT_MAPPER);
		Map<String, String> fieldMap = om.readValue(JSONConverter.getJSONAsString(entity), Map.class);
		super.setContentMap(fieldMap);
	}

}
