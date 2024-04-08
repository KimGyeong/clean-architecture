package com.kimgyeong.cleanarchitecture.application.service;

import java.time.LocalDateTime;

import com.kimgyeong.cleanarchitecture.application.port.in.GetAccountBalanceQuery;
import com.kimgyeong.cleanarchitecture.application.port.out.LoadAccountPort;
import com.kimgyeong.cleanarchitecture.domain.Account;
import com.kimgyeong.cleanarchitecture.domain.Money;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GetAccountBalanceService implements GetAccountBalanceQuery {
	private final LoadAccountPort loadAccountPort;

	@Override
	public Money getAccountBalance(Account.AccountId accountId) {
		return loadAccountPort.loadAccount(accountId, LocalDateTime.now())
			.calculateBalance();
	}
}
