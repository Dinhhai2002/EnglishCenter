package com.english_center.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.english_center.dao.ClassWeekdayDao;
import com.english_center.entity.ClassWeekday;
import com.english_center.service.ClassWeekdayService;

@Service("ClassWeekdayService")
@Transactional(rollbackFor = Error.class)
public class ClassWeekdayServiceImpl implements ClassWeekdayService {

	@Autowired
	ClassWeekdayDao classWeekdayDao;

	@Override
	public void create(ClassWeekday classWeekday) throws Exception {
		classWeekdayDao.create(classWeekday);

	}

	@Override
	public ClassWeekday findOne(int id) throws Exception {
		return classWeekdayDao.findOne(id);
	}

	@Override
	public void update(ClassWeekday classWeekday) throws Exception {
		classWeekdayDao.update(classWeekday);
	}

	@Override
	public ClassWeekday findByAll(int classId, int weekdayId, String fromHour, String toHour) throws Exception {
		return classWeekdayDao.findByAll(classId, weekdayId, fromHour, toHour);
	}

	@Override
	public List<ClassWeekday> getAllByClassId(int classId) throws Exception {
		return classWeekdayDao.getAllByClassId(classId);
	}

}
