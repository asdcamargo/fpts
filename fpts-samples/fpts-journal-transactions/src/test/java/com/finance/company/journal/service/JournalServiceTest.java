package com.finance.company.journal.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.hamcrest.core.Is;
import org.hamcrest.core.IsNot;
import org.hamcrest.text.IsEmptyString;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import com.finance.company.journal.bean.FinancialTransaction;
import com.finance.company.journal.main.Application;
import com.microservicetest.util.JSONConverter;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebIntegrationTest
public class JournalServiceTest {

	@Value("${server.port}")
	private String serverPort;

	private String targetURI = "/rest/finance/transactions";
	private RestTemplate template = new TestRestTemplate();
	private StringBuilder uri;
	private HttpHeaders requestHeaders = new HttpHeaders();

	@Before
	public void setUp() {
		uri = new StringBuilder("http://localhost:").append(serverPort).append(targetURI);
		requestHeaders.setContentType(MediaType.APPLICATION_JSON);
	}

	@Test
	public void isServerUp() {

		HttpHeaders headers = template.getForEntity(uri.toString(), String.class).getHeaders();
		assertNotNull(headers);
	}

	@Test
	public void testAddTransaction() {
		// Lets add a new transaction and get the response
		ResponseEntity<FinancialTransaction> returned = insertNewTransaction();
		assertTrue(returned.getStatusCode().is2xxSuccessful());
		assertNotNull(returned.getBody());
		assertThat(returned.getBody().getId(), IsNot.not(IsEmptyString.isEmptyOrNullString()));
	}

	@Test
	public void testGetTransaction() {
		ResponseEntity<FinancialTransaction> returned = insertNewTransaction();

		String urlWithId = uri.toString() + "/" + returned.getBody().getId();

		ResponseEntity<FinancialTransaction> returnedGet = template.getForEntity(urlWithId, FinancialTransaction.class);

		assertTrue(returned.getStatusCode().is2xxSuccessful());
		assertNotNull(returnedGet.getBody());
		assertThat(returned.getBody().getId(), Is.is(returnedGet.getBody().getId()));

	}

	@Test
	public void testDeleteTransaction() {
		ResponseEntity<FinancialTransaction> returned = insertNewTransaction();

		String urlWithId = uri.toString() + "/" + returned.getBody().getId();
		template.delete(urlWithId);
		// Try to get with the id, need to return bad request because the entity
		// is not there anymore
		HttpStatus statusCode = template.getForEntity(urlWithId, String.class).getStatusCode();

		assertTrue(statusCode.is4xxClientError());
	}

	public ResponseEntity<FinancialTransaction> insertNewTransaction() {
		FinancialTransaction testObj = new FinancialTransaction("000023989", "766", "10", "2015");
		HttpEntity<String> httpEntity = new HttpEntity<String>(JSONConverter.getJSONAsString(testObj), requestHeaders);

		ResponseEntity<FinancialTransaction> returned = template.postForEntity(uri.toString(), httpEntity,
				FinancialTransaction.class, requestHeaders);

		assertTrue(returned.getStatusCode().is2xxSuccessful());
		assertNotNull(returned.getBody());
		assertThat(returned.getBody().getId(), IsNot.not(IsEmptyString.isEmptyOrNullString()));

		return returned;
	}

}
