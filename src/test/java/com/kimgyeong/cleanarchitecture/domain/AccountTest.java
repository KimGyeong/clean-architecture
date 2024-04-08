package com.kimgyeong.cleanarchitecture.domain;

import static com.kimgyeong.cleanarchitecture.common.AccountTestData.*;
import static com.kimgyeong.cleanarchitecture.common.ActivityTestData.*;
import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

class AccountTest {

	@Test
	void withdrawalSucceeds() {
		Account.AccountId accountId = new Account.AccountId(1L);
		Account account = defaultAccount()
			.withAccountId(accountId)
			.withBaselineBalance(Money.of(555L))
			.withActivityWindow(new ActivityWindow(
				defaultActivity()
					.withTargetAccount(accountId)
					.withMoney(Money.of(999L)).build(),
				defaultActivity()
					.withTargetAccount(accountId)
					.withMoney(Money.of(1L)).build()
			))
			.build();

		boolean success = account.withdraw(Money.of(555L), new Account.AccountId(99L));

		assertThat(success).isTrue();
		assertThat(account.getActivityWindow().getActivities()).hasSize(3);
		assertThat(account.calculateBalance()).isEqualTo(Money.of(1000L));
	}
}