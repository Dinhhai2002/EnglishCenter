package com.english_center.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.english_center.dao.StatisticalDao;
import com.english_center.service.StatisticalService;

@Service("StatisticalService")
@Transactional(rollbackFor = Error.class)
public class StatisticalServiceImpl implements StatisticalService {

	@Autowired
	StatisticalDao statisticalDao;

	@Override
	public List<Object> statisticalAmount(int numberWeek, String fromDate, String toDate, int type) throws Exception {
		return statisticalDao.statisticalAmount(numberWeek, fromDate, toDate, type);
	}

	@Override
	public List<Object> statisticalNumberUserDoExam(int numberWeek, String fromDate, String toDate, int type)
			throws Exception {
		return statisticalDao.statisticalNumberUserDoExam(numberWeek, fromDate, toDate, type);
	}

}
