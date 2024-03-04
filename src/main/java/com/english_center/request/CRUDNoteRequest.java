package com.english_center.request;

import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class CRUDNoteRequest {
	@NotEmpty(message = "Ghi chú bài học không được phép để trống")
	private String content;

	@JsonProperty("course_id")
	private int courseId;

	@JsonProperty("chapter_id")
	private int chapterId;
	
	@JsonProperty("lessons_id")
	private int lessonsId;
}
