package com.english_center.common.enums;

public enum RoleEnum {
	USER(0), STUDENT(1), TEACHER(2), ADMIN(3);

	private int value;

	private RoleEnum(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public static RoleEnum valueOf(int value) {
		switch (value) {
		case 0:
			return USER;
		case 1:
			return STUDENT;
		case 2:
			return TEACHER;
		case 3:
			return ADMIN;
		default:
			return USER;
		}
	}

	public String getName() {
		switch (this) {
		case USER:
			return "USER";
		case STUDENT:
			return "STUDENT";
		case TEACHER:
			return "TEACHER";
		case ADMIN:
			return "ADMIN";

		default:
			return "USER";
		}
	}
}
