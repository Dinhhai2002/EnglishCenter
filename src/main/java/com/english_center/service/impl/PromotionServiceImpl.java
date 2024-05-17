package com.english_center.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.english_center.common.utils.Pagination;
import com.english_center.dao.PromotionDao;
import com.english_center.entity.Promotion;
import com.english_center.model.StoreProcedureListResult;
import com.english_center.service.PromotionService;

@Service("PromotionService")
@Transactional(rollbackFor = Error.class)
public class PromotionServiceImpl implements PromotionService {

	@Autowired
	PromotionDao promotionDao;

	@Override
	public void create(Promotion promotion) throws Exception {
		promotionDao.create(promotion);
	}

	@Override
	public Promotion findOne(int id) throws Exception {
		return promotionDao.findOne(id);
	}

	@Override
	public void update(Promotion promotion) throws Exception {
		promotionDao.update(promotion);

	}

	@Override
	public List<Promotion> findAll() throws Exception {
		return promotionDao.findAll();
	}

	@Override
	public StoreProcedureListResult<Promotion> spGPromotion(String keySearch, int status, Pagination pagination)
			throws Exception {
		return promotionDao.spGPromotion(keySearch, status, pagination);
	}
}
