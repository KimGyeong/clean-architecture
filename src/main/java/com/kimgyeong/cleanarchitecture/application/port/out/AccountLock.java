package com.kimgyeong.cleanarchitecture.application.port.out;

import com.kimgyeong.cleanarchitecture.domain.Account.AccountId;

public interface AccountLock {
	void lockAccount(AccountId accountId);
	void releaseAccount(AccountId accountId);
}
