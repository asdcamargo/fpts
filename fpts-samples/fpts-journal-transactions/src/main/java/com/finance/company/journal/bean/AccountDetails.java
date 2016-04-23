package com.finance.company.journal.bean;

import org.springframework.data.annotation.Id;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class AccountDetails {

	@Id
	private String id;

	private double amountUsd;

	private double amountPlan;

	private String account;

	public static AccountDetails build(String account, double amtUsd, double amtPln) {
		return new AccountDetails(amtUsd, amtPln, account);
	}

	public AccountDetails(double amountUsd, double amountPlan, String account) {
		super();
		this.amountUsd = amountUsd;
		this.amountPlan = amountPlan;
		this.account = account;
	}

}
