package com.kimgyeong.cleanarchitecture.application.port.in;

public interface SendMoneyUseCase {
	boolean sendMoney(SendMoneyCommand command);
}
