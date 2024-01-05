package com.english_center.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.english_center.dao.ExamDetailDao;
import com.english_center.entity.ExamDetail;
import com.english_center.service.ExamDetailService;

@Service("ExamDetailService")
@Transactional(rollbackOn = Error.class )
public class ExamDetailServiceImpl implements ExamDetailService {
	
	@Autowired
	ExamDetailDao examDetailDao;

	@Override
	public void create(ExamDetail examDetail) throws Exception {
		examDetailDao.create(examDetail);
		
	}

	@Override
	public ExamDetail findOne(int id) throws Exception {
		return examDetailDao.findOne(id);
	}

	@Override
	public void update(ExamDetail examDetail) throws Exception {
		examDetailDao.update(examDetail);
		
	}
	
	

}
