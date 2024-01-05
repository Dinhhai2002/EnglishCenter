package com.english_center.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.english_center.common.utils.Pagination;
import com.english_center.dao.HourDao;
import com.english_center.entity.Hour;
import com.english_center.model.StoreProcedureListResult;
import com.english_center.service.HourService;

@Service("HourService")
@Transactional(rollbackFor = Error.class)
public class HourServiceImpl implements HourService {

	@Autowired
	HourDao hourDao;

	@Override
	public void create(Hour hour) throws Exception {
		hourDao.create(hour);
	}

	@Override
	public Hour findOne(int id) throws Exception {
		return hourDao.findOne(id);
	}

	@Override
	public void update(Hour hour) throws Exception {
		hourDao.update(hour);
	}

	@Override
	public List<Hour> getAll() throws Exception {
		return hourDao.getAll();
	}

	@Override
	public StoreProcedureListResult<Hour> getAllHavePagination(int weekdayId, Pagination pagination) throws Exception {
		return hourDao.getAllHavePagination(weekdayId, pagination);
	}

}
