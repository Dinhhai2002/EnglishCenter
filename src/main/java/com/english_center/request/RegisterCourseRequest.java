package com.english_center.request;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

public class RegisterCourseRequest {
	@Min(value = 0, message = "Giá trị nhỏ nhất là 0")
	@Max(value = 1, message = "Giá trị lớn nhất là 1")
	private int type;

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

}
