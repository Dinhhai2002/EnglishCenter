package com.english_center.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.english_center.common.utils.Pagination;
import com.english_center.dao.BannerDao;
import com.english_center.entity.Banner;
import com.english_center.model.StoreProcedureListResult;
import com.english_center.service.BannerService;

@Service("BannerService")
@Transactional(rollbackFor = Error.class)
public class BannerServiceImpl implements BannerService {
	@Autowired
	BannerDao bannerDao;

	@Override
	public void create(Banner banner) throws Exception {
		bannerDao.create(banner);

	}

	@Override
	public Banner findOne(int id) throws Exception {
		return bannerDao.findOne(id);
	}

	@Override
	public void update(Banner banner) throws Exception {
		bannerDao.update(banner);

	}

	@Override
	public StoreProcedureListResult<Banner> getAll(int status, Pagination pagination) throws Exception {
		return bannerDao.getAll(status, pagination);
	}

}
