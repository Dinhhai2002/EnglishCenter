package com.english_center.response;

import com.english_center.entity.Image;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ImageResponse {
	private int id;

	private String url;

	@JsonProperty("user_id")
	private int userId;

	public ImageResponse() {

	}

	public ImageResponse(Image entity) {
		this.id = entity.getId();
		this.url = entity.getUrl();
		this.userId = entity.getUserId();
	}
}
