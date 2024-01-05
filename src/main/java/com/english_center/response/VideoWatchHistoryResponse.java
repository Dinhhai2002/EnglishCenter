package com.english_center.response;

import com.english_center.entity.VideoWatchHistory;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class VideoWatchHistoryResponse {

	private int id;

	@JsonProperty("user_id")
	private int userId;

	@JsonProperty("lessons_id")
	private int lessonsId;

	@JsonProperty("watch_time")
	private long watchTime;

	@JsonProperty("is_done")
	private int isDone;

	public VideoWatchHistoryResponse() {

	}

	public VideoWatchHistoryResponse(VideoWatchHistory entity) {
		this.id = entity.getId();
		this.userId = entity.getUserId();
		this.lessonsId = entity.getLessonsId();
		this.watchTime = entity.getWatchTime();
		this.isDone = entity.getIsDone();
	}

}
