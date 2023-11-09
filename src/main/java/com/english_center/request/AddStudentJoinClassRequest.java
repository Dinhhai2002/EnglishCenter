package com.english_center.request;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class AddStudentJoinClassRequest {

	@JsonProperty("user_course_id")
	private List<Integer> userCourseId;

}
