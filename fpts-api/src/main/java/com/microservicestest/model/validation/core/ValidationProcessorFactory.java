package com.microservicestest.model.validation.core;

import com.microservicestest.model.validation.interfaces.IKeyValueValidationProcessor;
import com.microservicetest.util.ValidationType;

/**
 * Factory to instantiate ValidationProcessors based on {@link ValidationType}
 *
 * @author andre
 *
 */
public class ValidationProcessorFactory {

	public static IKeyValueValidationProcessor getProcessorForType(ValidationType type) {
		switch (type) {
		case HEADER:
			return new HeaderValidationProcessor();
		case BODY:
			return new BodyValidationProcessor();
		default:
			return new BodyValidationProcessor();
		}
	}

}
