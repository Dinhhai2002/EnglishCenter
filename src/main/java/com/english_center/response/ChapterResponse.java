package com.english_center.response;

import java.util.List;
import java.util.stream.Collectors;

import com.english_center.entity.Chapter;
import com.english_center.entity.Course;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ChapterResponse {
	private int id;

	private String name;

	@JsonProperty("course_id")
	private int courseId;

	@JsonProperty("course_name")
	private String courseName;

	@JsonProperty("is_free")
	private int isFree;

	private List<LessonsResponse> lessonsResponses;

	private int status;

	@JsonProperty("count_lessons_studied")
	private int countLessonsStudied;

	public ChapterResponse() {
	}

	public ChapterResponse(Chapter entity, Course course) {
		this.id = entity.getId();
		this.name = entity.getName();
		this.courseId = entity.getCourseId();
		this.courseName = course.getName();
		this.isFree = entity.getIsFree();
		this.status = entity.getStatus();
	}

	public ChapterResponse(Chapter entity) {
		this.id = entity.getId();
		this.name = entity.getName();
		this.courseId = entity.getCourseId();
		this.isFree = entity.getIsFree();
		this.status = entity.getStatus();
	}

	public ChapterResponse(Chapter entity, List<LessonsResponse> lessonsResponses) {
		this.id = entity.getId();
		this.name = entity.getName();
		this.courseId = entity.getCourseId();
		this.isFree = entity.getIsFree();
		this.lessonsResponses = lessonsResponses;
		this.status = entity.getStatus();
	}

	public ChapterResponse(Chapter entity, List<LessonsResponse> lessonsResponses, int countLessonsStudied) {
		this.id = entity.getId();
		this.name = entity.getName();
		this.courseId = entity.getCourseId();
		this.isFree = entity.getIsFree();
		this.lessonsResponses = lessonsResponses;
		this.status = entity.getStatus();
		this.countLessonsStudied = countLessonsStudied;
	}

//	public List<ChapterResponse> mapToListAlone(List<Chapter> entities) {
//		return entities.stream().map(x -> new ChapterResponse(x)).collect(Collectors.toList());
//	}

	public List<ChapterResponse> mapToList(List<Chapter> entities, List<LessonsResponse> lessonsResponses) {
		return entities.stream().map(x -> new ChapterResponse(x, lessonsResponses)).collect(Collectors.toList());
	}

}
