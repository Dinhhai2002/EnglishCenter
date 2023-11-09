package com.english_center.request;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
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

	

}
