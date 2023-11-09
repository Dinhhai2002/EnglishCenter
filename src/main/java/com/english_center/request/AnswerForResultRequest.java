package com.english_center.request;

import javax.validation.constraints.Min;

import lombok.Data;

@Data
public class AnswerForResultRequest {

	@Min(value = 1, message = "giá trị tối thiểu là 1")
	private int id;

	private String key;


}
