package com.kimgyeong.cleanarchitecture.adapter.out.persistence;

import java.time.LocalDateTime;
import java.util.List;

import com.kimgyeong.cleanarchitecture.application.port.out.LoadAccountPort;
import com.kimgyeong.cleanarchitecture.application.port.out.UpdateAccountStatePort;
import com.kimgyeong.cleanarchitecture.common.PersistenceAdapter;
import com.kimgyeong.cleanarchitecture.domain.Account;
import com.kimgyeong.cleanarchitecture.domain.Account.AccountId;
import com.kimgyeong.cleanarchitecture.domain.Activity;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@PersistenceAdapter
public class AccountPersistenceAdapter implements LoadAccountPort, UpdateAccountStatePort {
	private final AccountRepository accountRepository;
	private final ActivityRepository activityRepository;
	private final AccountMapper accountMapper;

	@Override
	public Account loadAccount(AccountId accountId, LocalDateTime baselineDate) {
		AccountJpaEntity account = accountRepository.findById(accountId.getValue())
			.orElseThrow(EntityNotFoundException::new);

		List<ActivityJpaEntity> activities = activityRepository.findByOwnerSince(
			accountId.getValue(),
			baselineDate
		);

		Long withdrawalBalance = activityRepository.getWithdrawalBalanceUntil(
			accountId.getValue(),
			baselineDate
		).orElse(0L);

		Long depositBalance = activityRepository.getDepositBalanceUntil(
			accountId.getValue(),
			baselineDate
		).orElse(0L);

		return accountMapper.mapToDomainEntity(
			account,
			activities,
			withdrawalBalance,
			depositBalance
		);
	}

	@Override
	public void updateActivities(Account account) {
		for (Activity activity : account.getActivityWindow().getActivities()) {
			if (activity.getId() == null) {
				activityRepository.save(accountMapper.mapToJpaEntity(activity));
			}
		}
	}
}
