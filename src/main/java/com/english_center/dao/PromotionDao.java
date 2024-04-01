package com.english_center.dao;

import com.english_center.entity.Promotion;

public interface PromotionDao {
	void create(Promotion promotion) throws Exception;

	Promotion findOne(int id) throws Exception;

	void update(Promotion promotion) throws Exception;
}
