package com.microservicestest.model.validation.core;

import java.util.Map;

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

}
