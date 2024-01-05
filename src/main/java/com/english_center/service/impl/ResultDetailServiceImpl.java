package com.english_center.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.english_center.dao.ResultDetailDao;
import com.english_center.entity.ResultDetail;
import com.english_center.service.ResultDetailService;

@Service("ResultDetailService")
@Transactional(rollbackFor = Error.class)
public class ResultDetailServiceImpl implements ResultDetailService {

	@Autowired
	ResultDetailDao resultDetailDao;

	@Override
	public void create(ResultDetail resultDetail) throws Exception {
		resultDetailDao.create(resultDetail);
	}

	@Override
	public ResultDetail findOne(int id) throws Exception {
		return resultDetailDao.findOne(id);
	}

	@Override
	public void update(ResultDetail resultDetail) throws Exception {
		resultDetailDao.update(resultDetail);
	}

	@Override
	public List<ResultDetail> getListByResultId(int resultId) throws Exception {
		return resultDetailDao.getListByResultId(resultId);
	}

}
