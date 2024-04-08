package com.kimgyeong.cleanarchitecture.application.service;

import java.time.LocalDateTime;

import org.springframework.transaction.annotation.Transactional;

import com.kimgyeong.cleanarchitecture.application.port.in.SendMoneyCommand;
import com.kimgyeong.cleanarchitecture.application.port.in.SendMoneyUseCase;
import com.kimgyeong.cleanarchitecture.application.port.out.AccountLock;
import com.kimgyeong.cleanarchitecture.application.port.out.LoadAccountPort;
import com.kimgyeong.cleanarchitecture.application.port.out.UpdateAccountStatePort;
import com.kimgyeong.cleanarchitecture.common.UseCase;
import com.kimgyeong.cleanarchitecture.domain.Account;
import com.kimgyeong.cleanarchitecture.domain.Account.AccountId;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@UseCase
@Transactional
public class SendMoneyService implements SendMoneyUseCase {
	private final LoadAccountPort loadAccountPort;
	private final AccountLock accountLock;
	private final UpdateAccountStatePort updateAccountStatePort;
	private final MoneyTransferProperties moneyTransferProperties;

	@Override
	public boolean sendMoney(SendMoneyCommand command) {
		checkThreshold(command);

		LocalDateTime baselineDate = LocalDateTime.now().minusDays(10);

		Account sourceAccount = loadAccountPort.loadAccount(
			command.getSourceAccountId(),
			baselineDate);

		Account targetAccount = loadAccountPort.loadAccount(
			command.getTargetAccountId(),
			baselineDate);

		AccountId sourceAccountId = sourceAccount.getId()
			.orElseThrow(() -> new IllegalStateException("expected source account ID not to be empty"));
		AccountId targetAccountId = targetAccount.getId()
			.orElseThrow(() -> new IllegalStateException("expected source account ID not to be empty"));

		accountLock.lockAccount(sourceAccountId);
		if (!sourceAccount.withdraw(command.getMoney(), targetAccountId)) {
			accountLock.releaseAccount(sourceAccountId);
			return false;
		}

		accountLock.lockAccount(targetAccountId);
		if (!targetAccount.deposit(command.getMoney(), sourceAccountId)) {
			accountLock.releaseAccount(sourceAccountId);
			accountLock.releaseAccount(targetAccountId);
			return false;
		}

		updateAccountStatePort.updateActivities(sourceAccount);
		updateAccountStatePort.updateActivities(targetAccount);

		accountLock.releaseAccount(sourceAccountId);
		accountLock.releaseAccount(targetAccountId);
		return true;
	}

	private void checkThreshold(SendMoneyCommand command) {
		if (command.getMoney().isGreaterThan(moneyTransferProperties.getMaximumTransferThreshold())) {
			throw new ThresholdExceededException(moneyTransferProperties.getMaximumTransferThreshold(), command.getMoney());
		}
	}
}
