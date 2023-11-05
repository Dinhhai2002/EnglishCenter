package com.english_center.request;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AddStudentJoinClassRequest {

	@JsonProperty("user_course_id")
	private List<Integer> userCourseId;

	public List<Integer> getUserCourseId() {
		return userCourseId;
	}

	public void setUserCourseId(List<Integer> userCourseId) {
		this.userCourseId = userCourseId;
	}

	

}
