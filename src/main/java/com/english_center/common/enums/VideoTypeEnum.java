package com.english_center.common.enums;

public enum VideoTypeEnum {
	YOUTUBE(0), 
	DRIVER(1);

	private final int value;

	private VideoTypeEnum(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public static VideoTypeEnum valueOf(int value) {
		switch (value) {
		case 0:
			return VideoTypeEnum.YOUTUBE;
		case 1:
			return VideoTypeEnum.DRIVER;

		default:
			return VideoTypeEnum.YOUTUBE;
		}
	}

	public String getName() {
		switch (value) {
		case 0:
			return "YOUTUBE";
		case 1:
			return "DRIVER";
		default:
			return "YOUTUBE";
		}
	}
}
