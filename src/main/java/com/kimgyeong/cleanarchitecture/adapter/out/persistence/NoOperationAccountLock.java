package com.kimgyeong.cleanarchitecture.adapter.out.persistence;

import org.springframework.stereotype.Component;

import com.kimgyeong.cleanarchitecture.application.port.out.AccountLock;
import com.kimgyeong.cleanarchitecture.domain.Account;

@Component
class NoOperationAccountLock implements AccountLock {
	@Override
	public void lockAccount(Account.AccountId accountId) {
		// do nothing
	}

	@Override
	public void releaseAccount(Account.AccountId accountId) {
		// do nothing
	}
}
