package com.english_center.request;

import javax.validation.constraints.Min;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SetTeacherClassRequest {
	@JsonProperty("teacher_id")
	@Min(value = 1, message = "giá trị nhỏ nhất là 1")
	private int teacherId;

	public int getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(int teacherId) {
		this.teacherId = teacherId;
	}

}
