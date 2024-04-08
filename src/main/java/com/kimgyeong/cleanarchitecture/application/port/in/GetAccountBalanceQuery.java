package com.kimgyeong.cleanarchitecture.application.port.in;

import com.kimgyeong.cleanarchitecture.domain.Account.AccountId;
import com.kimgyeong.cleanarchitecture.domain.Money;

public interface GetAccountBalanceQuery {
	Money getAccountBalance(AccountId accountId);
}
