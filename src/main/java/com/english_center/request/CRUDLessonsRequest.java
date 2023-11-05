package com.english_center.request;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CRUDLessonsRequest {

	@NotEmpty(message = "tên bài học không được phép để trống")
	@Length(max = 255, message = "Không được phép lớn hơn 255 kí tự")
	private String name;

	@NotEmpty(message = "Mô tả bài học không được phép để trống")
	private String description;

	@NotEmpty(message = "Nội dung bài học không được phép để trống")
	private String content;

	@JsonProperty("url_video")
	private String urlVideo;

	@JsonProperty("video_type")
	@Min(value = 0, message = "giá trị nhỏ nhất là 0")
	@Max(value = 1, message = "giá trị lớn nhất là 1")
	private int videoType;

	@JsonProperty("is_free")
	@Min(value = 0, message = "giá trị nhỏ nhất là 0")
	@Max(value = 1, message = "giá trị lớn nhất là 1")
	private int isFree;

//	@Min(value = 1, message = "giá trị lớn nhất là 1")
//	private int sort;

	@JsonProperty("course_id")
	private int courseId;

	@JsonProperty("chapter_id")
	private int chapterId;

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

	public String getUrlVideo() {
		return urlVideo;
	}

	public void setUrlVideo(String urlVideo) {
		this.urlVideo = urlVideo;
	}

	public int getCourseId() {
		return courseId;
	}

	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}

	public int getChapterId() {
		return chapterId;
	}

	public void setChapterId(int chapterId) {
		this.chapterId = chapterId;
	}

	public int getVideoType() {
		return videoType;
	}

	public void setVideoType(int videoType) {
		this.videoType = videoType;
	}

	public int getIsFree() {
		return isFree;
	}

	public void setIsFree(int isFree) {
		this.isFree = isFree;
	}

}
