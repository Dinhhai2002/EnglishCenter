package com.english_center.request;

import javax.validation.constraints.Min;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CRUDClassStudentRequest {
	
	@Min(value = 1, message = "giá trị nhỏ nhất là 1")
	@JsonProperty("class_id")
	private int classId;
	
	@Min(value = 1, message = "giá trị nhỏ nhất là 1")
	@JsonProperty("student_id")
	private int studentId;

	public int getClassId() {
		return classId;
	}

	public void setClassId(int classId) {
		this.classId = classId;
	}

	public int getStudentId() {
		return studentId;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}
	
	
}
