package com.english_center.response;

import com.english_center.entity.VideoWatchHistory;
import com.fasterxml.jackson.annotation.JsonProperty;

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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getLessonsId() {
		return lessonsId;
	}

	public void setLessonsId(int lessonsId) {
		this.lessonsId = lessonsId;
	}

	public long getWatchTime() {
		return watchTime;
	}

	public void setWatchTime(long watchTime) {
		this.watchTime = watchTime;
	}

	public int getIsDone() {
		return isDone;
	}

	public void setIsDone(int isDone) {
		this.isDone = isDone;
	}

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
