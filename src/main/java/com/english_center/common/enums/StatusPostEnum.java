package com.english_center.common.enums;

public enum StatusPostEnum {
	PENDING(1), ACTIVE(2), NOT_ACTIVE(3);

	private int value;

	private StatusPostEnum(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public static StatusPostEnum valueOf(int value) {
		switch (value) {
		case 1:
			return PENDING;
		case 2:
			return ACTIVE;
		case 3:
			return NOT_ACTIVE;

		default:
			return PENDING;
		}
	}
}
