package com.kimgyeong.cleanarchitecture.application.service;

import org.springframework.stereotype.Component;

import com.kimgyeong.cleanarchitecture.domain.Money;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Component
@AllArgsConstructor
@NoArgsConstructor
public class MoneyTransferProperties {
	private Money maximumTransferThreshold = Money.of(1_000_000L);
}
