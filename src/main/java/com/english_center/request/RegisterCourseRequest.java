package com.english_center.request;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import lombok.Data;

@Data
public class RegisterCourseRequest {
	@Min(value = 0, message = "Giá trị nhỏ nhất là 0")
	@Max(value = 1, message = "Giá trị lớn nhất là 1")
	private int type;

	

}
