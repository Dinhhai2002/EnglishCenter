package com.english_center.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.english_center.dao.CityDao;
import com.english_center.entity.Cities;
import com.english_center.service.CityService;

@Service("CityService")
@Transactional(rollbackOn = Error.class)
public class CityServiceImpl implements CityService {
	@Autowired
	CityDao cityDao;

	@Override
	public List<Cities> getAll() throws Exception {
		return cityDao.getAll();
	}

}
