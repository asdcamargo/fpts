package com.finance.company.journal.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.finance.company.journal.bean.FinancialTransaction;

public interface IJournalRepository extends MongoRepository<FinancialTransaction, String> {

	FinancialTransaction findTransactionByName(String name);

}
