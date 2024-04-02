package com.english_center.service;

import java.util.List;

import com.english_center.entity.Promotion;

public interface PromotionService {
	void create(Promotion promotion) throws Exception;

	Promotion findOne(int id) throws Exception;

	void update(Promotion promotion) throws Exception;
	
	List<Promotion> findAll() throws Exception;
}
