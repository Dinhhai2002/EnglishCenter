package com.english_center.request;

import java.math.BigDecimal;

import javax.validation.constraints.Min;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class PaymentRequest {

	@Min(value = 0, message = "giá trị nhỏ nhất là 0")
	private BigDecimal amount;

	@JsonProperty("promotion_id")
	private int promotionId;

}
