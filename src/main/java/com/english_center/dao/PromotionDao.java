package com.english_center.dao;

import java.util.List;
import com.english_center.entity.Promotion;

public interface PromotionDao {
	void create(Promotion promotion) throws Exception;

	Promotion findOne(int id) throws Exception;

	List<Promotion> findAll() throws Exception;

	void update(Promotion promotion) throws Exception;
}
