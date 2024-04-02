package com.english_center.request;

import java.math.BigDecimal;

import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class CRUDPromotionRequest {
	private int point;

	@JsonProperty("promotion_type")
	private int promotionType;

	@JsonProperty("promotion_value")
	private BigDecimal promotionValue;

	@NotEmpty(message = "description không được phép để trống")
	private String description;
}
