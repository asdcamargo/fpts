package com.microservicestest.model.validation.interfaces;

import java.util.Map;

/**
 * Validate internal data using a {@link IValidationApproach}
 *
 * @author andre
 *
 */

public interface IValidation {

	public boolean validate(Map<String, String> toCompare);
}
