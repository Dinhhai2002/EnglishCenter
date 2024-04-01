package com.english_center.response;

import java.util.List;
import java.util.stream.Collectors;

import com.english_center.entity.Rating;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class RatingResponse {
	private int id;

	private int point;

	@JsonProperty("post_id")
	private int postId;

	@JsonProperty("user_id")
	private int userId;

	private int status;

	public RatingResponse() {
	}

	public RatingResponse(Rating rating) {
		this.id = rating.getId();
		this.point = rating.getPoint();
		this.postId = rating.getPostId();
		this.userId = rating.getUserId();
		this.status = rating.getStatus();
	}

	public List<RatingResponse> mapToList(List<Rating> entities) {
		return entities.stream().map(x -> new RatingResponse(x)).collect(Collectors.toList());
	}

}
