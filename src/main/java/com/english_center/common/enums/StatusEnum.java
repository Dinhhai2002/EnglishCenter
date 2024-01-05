package com.english_center.common.enums;

public enum StatusEnum {
	NOT_ACTIVE(0), ACTIVE(1);

	private int value;

	private StatusEnum(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public static StatusEnum valueOf(int value) {
		switch (value) {
		case 0:
			return NOT_ACTIVE;
		case 1:
			return ACTIVE;

		default:
			return NOT_ACTIVE;
		}
	}

}
