package com.english_center.dao;

import com.english_center.entity.VideoWatchHistory;

public interface VideoWatchHistoryDao {
	void create(VideoWatchHistory videoWatchHistory) throws Exception;

	VideoWatchHistory findOne(int id) throws Exception;

	void update(VideoWatchHistory videoWatchHistory) throws Exception;

	VideoWatchHistory findByLessonsAndUser(int lessonsId, int userId) throws Exception;
}
