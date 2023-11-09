package com.english_center.request;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

@Data
public class CRUDCategoryCourseRequest {
	@NotEmpty(message = "tên danh mục khóa học không được phép để trống")
	@Length(max = 255, message = "Không được phép lớn hơn 255 kí tự")
	private String name;

	
	
	
	
	
}
