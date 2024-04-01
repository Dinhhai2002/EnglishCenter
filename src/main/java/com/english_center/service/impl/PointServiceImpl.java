package com.english_center.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.english_center.dao.PointDao;
import com.english_center.entity.Point;
import com.english_center.service.PointService;

@Service("PointService")
@Transactional(rollbackFor = Error.class)
public class PointServiceImpl implements PointService {
	@Autowired
	PointDao pointDao;

	@Override
	public void create(Point point) throws Exception {
		pointDao.create(point);

	}

	@Override
	public Point findOne(int id) throws Exception {
		return pointDao.findOne(id);
	}

	@Override
	public void update(Point point) throws Exception {
		pointDao.update(point);

	}

	@Override
	public Point findOneByUserId(int userId) throws Exception {
		return pointDao.findOneByUserId(userId);
	}

}
