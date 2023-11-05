package com.english_center.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "video_watch_history")
public class VideoWatchHistory extends BaseEntity {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "user_id")
	private int userId;

	@Column(name = "lessons_id")
	private int lessonsId;

	@Column(name = "watch_time")
	private long watchTime;

	@Column(name = "is_done")
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

}
