package com.kimgyeong.cleanarchitecture.application.port.out;

import com.kimgyeong.cleanarchitecture.domain.Account;

public interface UpdateAccountStatePort {
	void updateActivities(Account account);
}
