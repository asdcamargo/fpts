package com.microservicetest.util;

import java.io.IOException;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.module.jsonSchema.factories.SchemaFactoryWrapper;

public class JSONConverter {

	static ObjectMapper mapper = new ObjectMapper();

	static {

		mapper.setSerializationInclusion(Include.NON_NULL);
	}

	public static void setMapper(ObjectMapper newMapper) {
		mapper = newMapper;
	}

	public static final String getJSONAsString(Serializable object) {
		try {
			return mapper.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static String getJsonSchema(Class clazz) throws IOException {
		// There are other configuration options you can set. This is the one I
		// needed.
		mapper.configure(SerializationFeature.WRITE_ENUMS_USING_TO_STRING, true);

		SchemaFactoryWrapper visitor = new SchemaFactoryWrapper();
		mapper.acceptJsonFormatVisitor(mapper.constructType(clazz), visitor);
		com.fasterxml.jackson.module.jsonSchema.JsonSchema jsonSchema = visitor.finalSchema();
		return mapper.writeValueAsString(jsonSchema);

	}

}
