package com.english_center.request;

import javax.validation.constraints.Min;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class SetTeacherClassRequest {
	@JsonProperty("teacher_id")
	@Min(value = 1, message = "giá trị nhỏ nhất là 1")
	private int teacherId;

	

}
