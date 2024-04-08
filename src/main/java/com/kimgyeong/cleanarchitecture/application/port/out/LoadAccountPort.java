package com.kimgyeong.cleanarchitecture.application.port.out;

import java.time.LocalDateTime;

import com.kimgyeong.cleanarchitecture.domain.Account;
import com.kimgyeong.cleanarchitecture.domain.Account.AccountId;

public interface LoadAccountPort {
	Account loadAccount(AccountId accountId, LocalDateTime baselineDate);
}
