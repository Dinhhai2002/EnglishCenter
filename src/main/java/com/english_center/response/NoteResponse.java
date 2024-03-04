package com.english_center.response;

import java.util.List;
import java.util.stream.Collectors;

import com.english_center.entity.Note;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class NoteResponse {
	private int id;

	private String content;

	@JsonProperty("course_id")
	private int courseId;

	@JsonProperty("chapter_id")
	private int chapterId;

	@JsonProperty("lessons_id")
	private int lessonsId;

	private int status;

	public NoteResponse() {
	}

	public NoteResponse(Note entity) {
		this.id = entity.getId();
		this.content = entity.getContent();
		this.courseId = entity.getCourseId();
		this.chapterId = entity.getChapterId();
		this.lessonsId = entity.getLessonsId();
		this.status = entity.getStatus();
	}
	
	public List<NoteResponse> mapToList(List<Note> entities) {
		return entities.stream().map(x -> new NoteResponse(x)).collect(Collectors.toList());
	}

}
