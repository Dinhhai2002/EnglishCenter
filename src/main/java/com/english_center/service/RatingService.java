package com.english_center.service;

import com.english_center.entity.Rating;

public interface RatingService {
	void create(Rating rating) throws Exception;

	Rating findOne(int id) throws Exception;

	void update(Rating rating) throws Exception;

	Rating findOneByUserAndPost(int userId, int postId) throws Exception;
}
