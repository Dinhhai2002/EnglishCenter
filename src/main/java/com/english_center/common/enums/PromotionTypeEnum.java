package com.english_center.common.enums;

public enum PromotionTypeEnum {
	PERCENT(1), CASH(2);

	private int value;

	private PromotionTypeEnum(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public static PromotionTypeEnum valueOf(int value) {
		switch (value) {
		case 1:
			return PERCENT;
		case 2:
			return CASH;

		default:
			return PERCENT;
		}
	}

	public String getName() {
		switch (this) {
		case PERCENT:
			return "Phần trăm";
		case CASH:
			return "Tiền mặt";

		default:
			return "Phần trăm";
		}
	}
}
