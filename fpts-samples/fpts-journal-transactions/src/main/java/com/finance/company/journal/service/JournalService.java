package com.finance.company.journal.service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.finance.company.journal.bean.AccountDetails;
import com.finance.company.journal.bean.FinancialTransaction;
import com.finance.company.journal.repository.IJournalRepository;
import com.microservicestest.annotation.PerformanceTest;
import com.microservicestest.model.validation.core.TestValidationsBuilder;
import com.microservicetest.model.GetTestParameter;
import com.microservicetest.model.TestSpec;
import com.microservicetest.util.HttpMethodEnum;

@RestController
public class JournalService extends RESTService {

	static Logger logger = LoggerFactory.getLogger(JournalService.class);

	@Autowired
	private IJournalRepository journalRepository;

	@RequestMapping(method = RequestMethod.GET, value = "/transactions/{id}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<FinancialTransaction> find(@PathVariable String id) throws IOException {
		logger.info("Searching JournalHeader by id: " + id);
		FinancialTransaction journalHeader = this.journalRepository.findOne(id);
		if (journalHeader == null) {
			logger.info("Journal not found ");
			return new ResponseEntity<FinancialTransaction>(HttpStatus.NOT_FOUND);
		}
		logger.info("Returning JournalHeader: " + journalHeader);
		return new ResponseEntity<FinancialTransaction>(journalHeader, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/transactions")
	@ResponseStatus(HttpStatus.OK)
	public List<FinancialTransaction> findByName(@RequestParam("name") String name) throws IOException {
		logger.info("Searching JournalHeader by name: " + name);
		FinancialTransaction journalHeader = this.journalRepository.findTransactionByName(name);
		return Arrays.asList(journalHeader);
	}

	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(method = RequestMethod.POST, consumes = "application/json", value = "/transactions")
	public FinancialTransaction save(@RequestBody FinancialTransaction journalHeader) throws IOException {
		logger.info("Saving JournalHeader: " + journalHeader);
		FinancialTransaction newJournalHeader = this.journalRepository.save(journalHeader);
		logger.info("New Journal Header created with id: " + newJournalHeader.getId());
		return newJournalHeader;
	}

	@RequestMapping(value = "/transactions/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.OK)
	public void delete(@PathVariable String id) {
		logger.info("Removing JournalHeader with id: " + id);
		this.journalRepository.delete(id);
	}

	@PerformanceTest(path = "/rest/finance/transactions", httpMethod = HttpMethodEnum.POST, description = "Save a new financial transaction")
	public TestSpec<FinancialTransaction> getTestSpecForSave() throws IOException {
		FinancialTransaction testObj = new FinancialTransaction("000023989", "766", "10", "2015");
		AccountDetails accDetails = AccountDetails.build("0083289", 1000d, 1000d);
		testObj.setAccountLines(Arrays.asList(accDetails));
		TestValidationsBuilder validationBuilder = new TestValidationsBuilder();
		TestSpec<FinancialTransaction> testSpec = new TestSpec<FinancialTransaction>(testObj,
				validationBuilder.buildHeaderStatus200AndEntityBody(testObj));
		return testSpec;
	}

	@PerformanceTest(path = "/rest/finance/transactions", httpMethod = HttpMethodEnum.GET, description = "Get transaction by id")
	public TestSpec<GetTestParameter> getTestSpecForGetById() throws IOException {
		GetTestParameter testParameter = new GetTestParameter("id", "1");
		TestValidationsBuilder validationBuilder = new TestValidationsBuilder();
		FinancialTransaction testObj = new FinancialTransaction("000023989", "766", "10", "2015");
		TestSpec<GetTestParameter> testSpec = new TestSpec<GetTestParameter>(testParameter,
				validationBuilder.buildHeaderStatus200AndEntityBody(testObj));
		return testSpec;
	}

}
