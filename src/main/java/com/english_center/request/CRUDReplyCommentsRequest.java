package com.english_center.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CRUDReplyCommentsRequest {

	@NotEmpty(message = "content không được phép để trống")
	@Length(max = 255, message = "Không được phép lớn hơn 255 kí tự")
	private String content;

	
	@Min(value = 1, message = "giá trị nhỏ nhất là 1")
	@JsonProperty("comments_id")
	private int commentsId;

	

	public int getCommentsId() {
		return commentsId;
	}

	public void setCommentsId(int commentsId) {
		this.commentsId = commentsId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	
}
