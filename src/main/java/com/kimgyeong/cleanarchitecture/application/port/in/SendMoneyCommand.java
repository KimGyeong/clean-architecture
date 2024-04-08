package com.kimgyeong.cleanarchitecture.application.port.in;

import com.kimgyeong.cleanarchitecture.common.SelfValidating;
import com.kimgyeong.cleanarchitecture.domain.Account.AccountId;
import com.kimgyeong.cleanarchitecture.domain.Money;

import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = false)
public class SendMoneyCommand extends SelfValidating<SendMoneyCommand> {
	@NotNull
	private final AccountId sourceAccountId;
	@NotNull
	private final AccountId targetAccountId;
	@NotNull
	private final Money money;

	/**
	 * lombok의 Builder를 사용하지 않는 이유
	 * 필드가 추가되었을 때, 생성자를 호출한 곳의 수정을 안해줘도 런타임 오류가 발생하지 않음.
	 * IDE가 파라미터명 힌트를 주기에, 휴먼 폴트는 충분히 방지할 수 있음.
	 */
	public SendMoneyCommand(AccountId sourceAccountId, AccountId targetAccountId, Money money) {
		this.sourceAccountId = sourceAccountId;
		this.targetAccountId = targetAccountId;
		this.money = money;
		this.validateSelf();
	}
}
