package com.english_center.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.english_center.dao.VideoWatchHistoryDao;
import com.english_center.entity.VideoWatchHistory;
import com.english_center.service.VideoWatchHistoryService;

@Service("VideoWatchHistoryService")
@Transactional(rollbackFor = Error.class)
public class VideoWatchHistoryServiceImpl implements VideoWatchHistoryService {

	@Autowired
	VideoWatchHistoryDao videoWatchHistoryDao;

	@Override
	public void create(VideoWatchHistory videoWatchHistory) throws Exception {
		videoWatchHistoryDao.create(videoWatchHistory);
	}

	@Override
	public VideoWatchHistory findOne(int id) throws Exception {
		return videoWatchHistoryDao.findOne(id);
	}

	@Override
	public void update(VideoWatchHistory videoWatchHistory) throws Exception {
		videoWatchHistoryDao.update(videoWatchHistory);

	}

	@Override
	public VideoWatchHistory findByLessonsAndUser(int lessonsId, int userId) throws Exception {
		return videoWatchHistoryDao.findByLessonsAndUser(lessonsId, userId);
	}

}
