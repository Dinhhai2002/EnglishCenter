package com.english_center.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.english_center.dao.TargetDao;
import com.english_center.entity.Target;
import com.english_center.service.TargetService;

@Service("TargetService")
@Transactional(rollbackFor = Error.class)
public class TargetServiceImpl implements TargetService {
	@Autowired
	TargetDao targetDao;

	@Override
	public void create(Target target) throws Exception {
		targetDao.create(target);
	}

	@Override
	public Target findOne(int id) throws Exception {
		return targetDao.findOne(id);
	}

	@Override
	public void update(Target target) throws Exception {
			targetDao.update(target);
	}

	@Override
	public Target findByUserId(int userId) throws Exception {
		return targetDao.findByUserId(userId);
	}
	
	
}
