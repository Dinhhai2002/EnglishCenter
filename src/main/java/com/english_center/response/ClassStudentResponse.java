package com.english_center.response;

import com.english_center.entity.ClassStudent;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ClassStudentResponse {
	private int id;

	@JsonProperty("class_id")
	private int classId;

	@JsonProperty("student_id")
	private int studentId;

	public ClassStudentResponse(ClassStudent entity) {
		super();
		this.id = entity.getId();
		this.classId = entity.getClassId();
		this.studentId = entity.getStudentId();
	}

	public ClassStudentResponse() {
	}

}
