package com.english_center.request;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

public class CRUDTopicExamRequest {
	
	@NotEmpty(message = "name không được phép để trống")
	@Length(max = 255, message = "Không được phép lớn hơn 255 kí tự")
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
