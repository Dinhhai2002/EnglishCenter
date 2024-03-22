package com.english_center.request;

import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class CRUDPostRequest {
	@NotEmpty(message = "title không được phép để trống")
	private String title;

	@NotEmpty(message = "description không được phép để trống")
	private String description;

	@NotEmpty(message = "content không được phép để trống")
	private String content;

	@JsonProperty("category_blog_id")
	private int categoryBlogId;

	private int status;

}
