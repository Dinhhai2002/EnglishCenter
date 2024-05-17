package com.english_center.dao;

import java.util.List;

import com.english_center.common.utils.Pagination;
import com.english_center.entity.Promotion;
import com.english_center.model.StoreProcedureListResult;

public interface PromotionDao {
	void create(Promotion promotion) throws Exception;

	Promotion findOne(int id) throws Exception;

	List<Promotion> findAll() throws Exception;

	StoreProcedureListResult<Promotion> spGPromotion(String keySearch, int status, Pagination pagination)
			throws Exception;

	void update(Promotion promotion) throws Exception;
}
