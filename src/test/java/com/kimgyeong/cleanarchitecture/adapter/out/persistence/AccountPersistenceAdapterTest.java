package com.kimgyeong.cleanarchitecture.adapter.out.persistence;

import static com.kimgyeong.cleanarchitecture.common.AccountTestData.*;
import static com.kimgyeong.cleanarchitecture.common.ActivityTestData.*;
import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;

import com.kimgyeong.cleanarchitecture.domain.Account;
import com.kimgyeong.cleanarchitecture.domain.Account.AccountId;
import com.kimgyeong.cleanarchitecture.domain.ActivityWindow;
import com.kimgyeong.cleanarchitecture.domain.Money;

@DataJpaTest
@Import({AccountPersistenceAdapter.class, AccountMapper.class})
class AccountPersistenceAdapterTest {

	@Autowired
	private AccountPersistenceAdapter adapter;

	@Autowired
	private ActivityRepository activityRepository;

	@Test
	@Sql("classpath:AccountPersistenceAdapterTest.sql")
	void loadsAccount() {
		Account account = adapter.loadAccount(
			new AccountId(1L),
			LocalDateTime.of(2018, 8, 10, 0, 0)
		);

		assertThat(account.getActivityWindow().getActivities()).hasSize(2);
		assertThat(account.calculateBalance()).isEqualTo(Money.of(500));
	}

	@Test
	void updatesActivities() {
		Account account = defaultAccount()
			.withBaselineBalance(Money.of(555L))
			.withActivityWindow(new ActivityWindow(
				defaultActivity()
					.withId(null)
					.withMoney(Money.of(1L)).build()))
			.build();

		adapter.updateActivities(account);

		assertThat(activityRepository.count()).isEqualTo(1);

		ActivityJpaEntity savedActivity = activityRepository.findAll().get(0);
		assertThat(savedActivity.getAmount()).isEqualTo(1L);
	}
}