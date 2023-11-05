package com.english_center.request;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

public class CRUDLevelRequest {
	@NotEmpty(message = "tên danh mục khóa học không được phép để trống")
	@Length(max = 255, message = "Không được phép lớn hơn 255 kí tự")
	private String name;
	
	@NotEmpty(message = "mã code không được phép để trống")
	private String code;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
}
