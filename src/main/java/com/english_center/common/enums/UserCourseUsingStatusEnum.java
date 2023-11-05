package com.english_center.common.enums;

public enum UserCourseUsingStatusEnum {
	NO_REGISTER(0), REGISTERED(1), EXPIRED(2);

	private final int value;

	private UserCourseUsingStatusEnum(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public static UserCourseUsingStatusEnum valueOf(int value) {
		switch (value) {
		case 0:
			return UserCourseUsingStatusEnum.NO_REGISTER;
		case 1:
			return UserCourseUsingStatusEnum.REGISTERED;
		case 2:
			return UserCourseUsingStatusEnum.EXPIRED;

		default:
			return UserCourseUsingStatusEnum.NO_REGISTER;
		}
	}

	public String getName() {
		switch (value) {
		case 0:
			return "Chưa Đăng kí";
		case 1:
			return "Đang sử dụng";
		case 2:
			return "Đã hết hạn";
		default:
			return "Chưa Đăng kí";
		}
	}
}
