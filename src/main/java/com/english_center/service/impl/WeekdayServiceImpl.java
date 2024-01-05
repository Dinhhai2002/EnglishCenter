package com.english_center.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.english_center.dao.WeekdayDao;
import com.english_center.entity.Weekday;
import com.english_center.service.WeekdayService;

@Service("WeekdayService")
@Transactional(rollbackFor = Error.class)
public class WeekdayServiceImpl implements WeekdayService {
	@Autowired
	WeekdayDao weekdayDao;

	@Override
	public void create(Weekday weekday) throws Exception {
		weekdayDao.create(weekday);
	}

	@Override
	public Weekday findOne(int id) throws Exception {
		return weekdayDao.findOne(id);
	}

	@Override
	public void update(Weekday weekday) throws Exception {
		weekdayDao.update(weekday);
	}

	@Override
	public List<Weekday> getAll() throws Exception {
		return weekdayDao.getAll();
	}

}
