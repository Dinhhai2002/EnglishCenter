package com.english_center.response;

import java.util.List;
import java.util.stream.Collectors;

import com.english_center.common.utils.Utils;
import com.english_center.entity.Class;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ClassResponse {
	private int id;

	private String name;

	@JsonProperty("start_date")
	private String startDate;

	@JsonProperty("end_date")
	private String endDate;

	@JsonProperty("teacher_id")
	private int teacherId;

	@JsonProperty("teacher_name")
	private String teacherName;

	@JsonProperty("course_id")
	private int courseId;

	@JsonProperty("course_name")
	private String courseName;

	@JsonProperty("total_student")
	private int totalStudent;

	private int status;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public int getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(int teacherId) {
		this.teacherId = teacherId;
	}

	public int getCourseId() {
		return courseId;
	}

	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public ClassResponse() {
	}

	public ClassResponse(Class entity) {
		this.id = entity.getId();
		this.name = entity.getName();
		this.startDate = Utils.getDateStringSupplier(entity.getStartDate());
		this.endDate = Utils.getDateStringSupplier(entity.getEndDate());
		this.teacherId = entity.getTeacherId();
		this.teacherName = entity.getTeacherName();
		this.courseId = entity.getCourseId();
		this.courseName = entity.getCourseName();
		this.totalStudent = entity.getTotalStudent();
		this.status = entity.getStatus();
	}

	public List<ClassResponse> mapToList(List<Class> entities) {
		return entities.stream().map(x -> new ClassResponse(x)).collect(Collectors.toList());
	}

}
