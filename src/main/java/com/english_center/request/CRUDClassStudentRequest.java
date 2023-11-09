package com.english_center.request;

import javax.validation.constraints.Min;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class CRUDClassStudentRequest {
	
	@Min(value = 1, message = "giá trị nhỏ nhất là 1")
	@JsonProperty("class_id")
	private int classId;
	
	@Min(value = 1, message = "giá trị nhỏ nhất là 1")
	@JsonProperty("student_id")
	private int studentId;

	
	
	
}
