package com.english_center.response;

import java.util.List;
import java.util.stream.Collectors;

import com.english_center.entity.UserCourse;
import com.english_center.entity.Users;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class UserCourseResponse {
	private int id;

	@JsonProperty("course_id")
	private int courseId;

	@JsonProperty("student_id")
	private int studentId;

	@JsonProperty("student_name")
	private String studentName;

	@JsonProperty("user_name")
	private String userName;

	@JsonProperty("is_join_class")
	private int isJoinClass;

	public UserCourseResponse() {
	}

	public UserCourseResponse(UserCourse entity) {
		this.id = entity.getId();
		this.courseId = entity.getCourseId();
		this.studentId = entity.getStudentId();
		this.isJoinClass = entity.getIsJoinClass();
	}

	public UserCourseResponse(UserCourse entity, Users user) {
		this.id = entity.getId();
		this.courseId = entity.getCourseId();
		this.studentId = entity.getStudentId();
		this.isJoinClass = entity.getIsJoinClass();
		this.studentName = user.getFullName();
		this.userName = user.getUserName();
	}

	public List<UserCourseResponse> mapToList(List<UserCourse> entities) {
		return entities.stream().map(x -> new UserCourseResponse(x)).collect(Collectors.toList());
	}

}
