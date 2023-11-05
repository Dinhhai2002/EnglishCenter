package com.english_center.request;

import javax.validation.constraints.Min;

public class AnswerForResultRequest {

	@Min(value = 1, message = "giá trị tối thiểu là 1")
	private int id;

	private String key;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

}
