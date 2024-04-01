package com.english_center.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class CRUDRatingRequest {
	@JsonProperty("post_id")
	private int postId;

	private int point;
}
