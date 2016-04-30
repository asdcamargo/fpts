package com.microservicestest.model.validation.interfaces;

import com.microservicetest.util.ValidationType;

/**
 * Basic interface that sets a class as a validator processor for key:value
 * pairs of data.<br>
 *
 * @author andre
 *
 */
public interface IKeyValueValidationProcessor extends IValidation {

	public void putContent(String field, String data);

	public ValidationType getValidationType();

}
