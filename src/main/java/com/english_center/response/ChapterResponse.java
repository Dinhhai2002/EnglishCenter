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

	public int getCourseId() {
		return courseId;
	}

	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}

	public List<LessonsResponse> getLessonsResponses() {
		return lessonsResponses;
	}

	public void setLessonsResponses(List<LessonsResponse> lessonsResponses) {
		this.lessonsResponses = lessonsResponses;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public int getIsFree() {
		return isFree;
	}

	public void setIsFree(int isFree) {
		this.isFree = isFree;
	}

	public int getCountLessonsStudied() {
		return countLessonsStudied;
	}

	public void setCountLessonsStudied(int countLessonsStudied) {
		this.countLessonsStudied = countLessonsStudied;
	}

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
