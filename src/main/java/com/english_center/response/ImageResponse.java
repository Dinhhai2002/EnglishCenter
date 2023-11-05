package com.english_center.response;

import com.english_center.entity.Image;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ImageResponse {
	private int id;

	
	private String url;

	@JsonProperty("user_id")
	private int userId;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	public ImageResponse() {

	}

	public ImageResponse(Image entity) {
		this.id = entity.getId();
		this.url=entity.getUrl();
		this.userId=entity.getUserId();
	}
}
