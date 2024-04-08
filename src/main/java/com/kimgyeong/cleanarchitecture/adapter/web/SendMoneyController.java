package com.kimgyeong.cleanarchitecture.adapter.web;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kimgyeong.cleanarchitecture.application.port.in.SendMoneyCommand;
import com.kimgyeong.cleanarchitecture.application.port.in.SendMoneyUseCase;
import com.kimgyeong.cleanarchitecture.domain.Account.AccountId;
import com.kimgyeong.cleanarchitecture.domain.Money;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
public class SendMoneyController {
	private final SendMoneyUseCase sendMoneyUseCase;

	@PostMapping("/accounts/send/{sourceAccountId}/{targetAccountId}/{amount}")
	void sendMoney(
		@PathVariable("sourceAccountId") Long sourceAccountId,
		@PathVariable("targetAccountId") Long targetAccountId,
		@PathVariable("amount") Long amount) {
		log.info("send money source: {}, target: {}, amount: {}", sourceAccountId, targetAccountId, amount);

		SendMoneyCommand command = new SendMoneyCommand(
			new AccountId(sourceAccountId),
			new AccountId(targetAccountId),
			Money.of(amount)
		);

		sendMoneyUseCase.sendMoney(command);
	}
}