package com.english_center.dao;

import com.english_center.entity.Rating;

public interface RatingDao {
	void create(Rating rating) throws Exception;

	Rating findOne(int id) throws Exception;

	Rating findOneByUserAndPost(int userId, int postId) throws Exception;

	void update(Rating rating) throws Exception;
}
