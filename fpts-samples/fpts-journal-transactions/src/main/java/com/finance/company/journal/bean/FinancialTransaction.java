package com.finance.company.journal.bean;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.annotation.Id;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class FinancialTransaction implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 840879551841560155L;

	@Id
	private String id;

	private String name;

	private String country;

	private String acctMonth;

	private String acctYear;

	private List<AccountDetails> accountLines;

	public FinancialTransaction(String name, String cty, String acctMonth, String acctYear) {
		super();
		this.name = name;
		this.country = cty;
		this.acctMonth = acctMonth;
		this.acctYear = acctYear;
	}

}
