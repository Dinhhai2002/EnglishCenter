package com.english_center.common.enums;

public enum CommentTypeEnum {
	EXAM(0), BLOG(1), COURSE(2);

	private int value;

	private CommentTypeEnum(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public static CommentTypeEnum valueOf(int value) {
		switch (value) {
		case 0:
			return EXAM;
		case 1:
			return BLOG;
		case 2:
			return COURSE;

		default:
			return EXAM;
		}
	}
}
