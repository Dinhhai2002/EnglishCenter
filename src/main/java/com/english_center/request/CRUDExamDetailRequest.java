package com.english_center.request;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class CRUDExamDetailRequest {

	@NotEmpty(message = "name không được phép để trống")
	@Length(max = 255, message = "Không được phép lớn hơn 255 kí tự")
	private String name;

	@Min(value = 1, message = "giá trị nhỏ nhất là 1")
	@Max(value = 200, message = "giá trị lớn nhất là 200")
	@JsonProperty("total_question")
	private int totalQuestion;

	
	
}
