package com.english_center.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.english_center.dao.RatingDao;
import com.english_center.entity.Rating;
import com.english_center.service.RatingService;

@Service("RatingService")
@Transactional(rollbackFor = Error.class)
public class RatingServiceImpl implements RatingService {
	@Autowired
	RatingDao ratingDao;

	@Override
	public void create(Rating rating) throws Exception {
		ratingDao.create(rating);

	}

	@Override
	public Rating findOne(int id) throws Exception {
		return ratingDao.findOne(id);
	}

	@Override
	public void update(Rating rating) throws Exception {
		ratingDao.update(rating);

	}

	@Override
	public Rating findOneByUserAndPost(int userId, int postId) throws Exception {
		return ratingDao.findOneByUserAndPost(userId, postId);
	}

}
