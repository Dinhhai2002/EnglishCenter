package com.english_center.response;

import com.english_center.entity.ClassStudent;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ClassStudentResponse {
	private int id;
	
	@JsonProperty("class_id")
	private int classId;
	
	@JsonProperty("student_id")
	private int studentId;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

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

	public ClassStudentResponse(ClassStudent entity) {
		super();
		this.id = entity.getId();
		this.classId = entity.getClassId();
		this.studentId = entity.getStudentId();
	}

	public ClassStudentResponse() {
	}
	
	
}
