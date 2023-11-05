package com.english_center.response;

import java.util.List;
import java.util.stream.Collectors;

import com.english_center.entity.Chapter;
import com.english_center.entity.Course;
import com.english_center.entity.Lessons;
import com.fasterxml.jackson.annotation.JsonProperty;

public class LessonsResponse {
	private int id;

	private String name;

	private String description;

	private String content;

	@JsonProperty("id_video")
	private String idVideo;

	@JsonProperty("video_type")
	private int videoType;

	private int sort;

	@JsonProperty("course_id")
	private int courseId;

	@JsonProperty("course_name")
	private String courseName;

	@JsonProperty("chapter_id")
	private int chapterId;

	@JsonProperty("chapter_name")
	private String chapterName;

	@JsonProperty("is_upload_video")
	private int isUploadVideo;

	@JsonProperty("is_free")
	private int isFree;

	@JsonProperty("is_lock")
	private int isLock;

	@JsonProperty("is_done")
	private int isDone;

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
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

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public int getChapterId() {
		return chapterId;
	}

	public void setChapterId(int chapterId) {
		this.chapterId = chapterId;
	}

	public String getChapterName() {
		return chapterName;
	}

	public void setChapterName(String chapterName) {
		this.chapterName = chapterName;
	}

	public String getIdVideo() {
		return idVideo;
	}

	public void setIdVideo(String idVideo) {
		this.idVideo = idVideo;
	}

	public int getVideoType() {
		return videoType;
	}

	public void setVideoType(int videoType) {
		this.videoType = videoType;
	}

	public int getIsUploadVideo() {
		return isUploadVideo;
	}

	public void setIsUploadVideo(int isUploadVideo) {
		this.isUploadVideo = isUploadVideo;
	}

	public int getIsFree() {
		return isFree;
	}

	public void setIsFree(int isFree) {
		this.isFree = isFree;
	}

	public int getIsLock() {
		return isLock;
	}

	public void setIsLock(int isLock) {
		this.isLock = isLock;
	}

	public int getIsDone() {
		return isDone;
	}

	public void setIsDone(int isDone) {
		this.isDone = isDone;
	}

	public LessonsResponse() {
	}

	public LessonsResponse(Lessons entity) {
		this.id = entity.getId();
		this.name = entity.getName();
		this.description = entity.getDescription();
		this.content = entity.getContent();
		this.idVideo = entity.getIdVideo();
		this.videoType = entity.getVideoType();
		this.sort = entity.getSort();
		this.courseId = entity.getCourseId();
		this.chapterId = entity.getChapterId();
		this.isFree = entity.getIsFree();
		this.status = entity.getStatus();
	}

	public LessonsResponse(Lessons entity, int isLock, int isDone) {
		this.id = entity.getId();
		this.name = entity.getName();
		this.description = entity.getDescription();
		this.content = entity.getContent();
		this.idVideo = entity.getIdVideo();
		this.videoType = entity.getVideoType();
		this.sort = entity.getSort();
		this.courseId = entity.getCourseId();
		this.chapterId = entity.getChapterId();
		this.isFree = entity.getIsFree();
		this.isLock = isLock;
		this.isDone = isDone;
		this.status = entity.getStatus();
	}

	public LessonsResponse(Lessons entity, Course course, Chapter chapter) {
		this.id = entity.getId();
		this.name = entity.getName();
		this.description = entity.getDescription();
		this.content = entity.getContent();
		this.idVideo = entity.getIdVideo();
		this.videoType = entity.getVideoType();
		this.sort = entity.getSort();
		this.courseId = entity.getCourseId();
		this.courseName = course.getName();
		this.chapterId = entity.getChapterId();
		this.chapterName = chapter.getName();
		this.isUploadVideo = entity.getIdVideo().equals("") ? 0 : 1;
		this.isFree = entity.getIsFree();
		this.status = entity.getStatus();
	}

	public List<LessonsResponse> mapToList(List<Lessons> entities) {
		return entities.stream().map(x -> new LessonsResponse(x)).collect(Collectors.toList());
	}

}
