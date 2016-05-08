package com.microservicetest.model.validation.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.Serializable;

import org.junit.Test;

import com.microservicestest.model.validation.core.TestValidationsBuilder;
import com.microservicetest.util.HttpHeaderFields;

public class TestValidationsBuildTest {

	static String jsonEntity = "{\"BODY\":\"{\\\"field1\\\":\\\"field1\\\",\\\"field\\\":true,\\\"field2\\\":\\\"field2\\\"}\"}";
	static String jsonHeader200WithBody = "{\"HEADER\":[\"{\\\"Status\\\":\\\"200\\\"}]\","
			+ "\"BODY\":\"{\\\"field1\\\":\\\"field1\\\",\\\"field\\\":true,\\\"field2\\\":\\\"field2\\\"}\"}";
	EntityForTest testEntity = new EntityForTest("field1", "field2", true);
	static String jsonHeaderWith3Fields = "{\"HEADER\":[\"{\\\"Status\\\":\\\"400\\\"}\",\"{\\\"Content-Type\\\":\\\"application/json\\\"}\",\"{\\\"Content-Length\\\":\\\"1024\\\"}\"]}";

	@Test
	public void testBodyValidationWithEntity() {
		try {
			TestValidationsBuilder builder = new TestValidationsBuilder();
			builder.buildBodyValidationFromEntity(testEntity);
			org.junit.Assert.assertTrue(builder.getJSONRepresentation().equals(jsonEntity));
		} catch (Exception e) {
			e.printStackTrace();
			org.junit.Assert.fail();
		}
	}

	@Test
	public void testHeader200AndBodyWithEntity() {
		try {
			TestValidationsBuilder builder = new TestValidationsBuilder();
			builder.buildHeaderStatus200AndEntityBody(testEntity);
			assertTrue(builder.getJSONRepresentation().equals(jsonHeader200WithBody));
		} catch (Exception e) {
			e.printStackTrace();
			org.junit.Assert.fail();
		}
	}

	@Test
	public void testHeaderWithMultipleFields() {
		try {
			TestValidationsBuilder builder = new TestValidationsBuilder();
			builder.addHeaderParameter(HttpHeaderFields.STATUS, "400");
			builder.addHeaderParameter(HttpHeaderFields.CONTENT_TYPE, "application/json");
			builder.addHeaderParameter(HttpHeaderFields.CONTENT_LENGTH, "1024");
			assertEquals(jsonHeaderWith3Fields, builder.getJSONRepresentation());
		} catch (Exception e) {
			e.printStackTrace();
			org.junit.Assert.fail();
		}
	}

	class EntityForTest implements Serializable {

		/**
		 *
		 */
		private static final long serialVersionUID = -8345421747894580237L;
		String field1, field2;
		boolean isField;

		public EntityForTest(String field1, String field2, boolean isField) {
			super();
			this.field1 = field1;
			this.field2 = field2;
			this.isField = isField;
		}

		public String getField1() {
			return field1;
		}

		public void setField1(String field1) {
			this.field1 = field1;
		}

		public String getField2() {
			return field2;
		}

		public void setField2(String field2) {
			this.field2 = field2;
		}

		public boolean isField() {
			return isField;
		}

		public void setField(boolean isField) {
			this.isField = isField;
		}

	}

}
