package com.microservicestest.model.validation.core;

import java.util.Map;

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

}
