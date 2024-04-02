package com.english_center.response;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import com.english_center.entity.Promotion;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class PromotionResponse {
	private int id;

	private int point;

	@JsonProperty("promotion_type")
	private int promotionType;

	@JsonProperty("promotion_value")
	private BigDecimal promotionValue;

	private String description;

	public PromotionResponse() {
	}

	public PromotionResponse(Promotion entity) {
		super();
		this.id = entity.getId();
		this.point = entity.getPoint();
		this.promotionType = entity.getPromotionType();
		this.promotionValue = entity.getPromotionValue();
		this.description = entity.getDescription();
	}

	public List<PromotionResponse> mapToList(List<Promotion> entities) {
		return entities.stream().map(x -> new PromotionResponse(x)).collect(Collectors.toList());
	}

}
