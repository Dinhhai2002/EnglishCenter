package com.english_center.request;

import java.math.BigDecimal;

import javax.validation.constraints.Min;

public class PaymentRequest {

	@Min(value = 0, message = "giá trị nhỏ nhất là 0")
	private BigDecimal amount;

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

}
