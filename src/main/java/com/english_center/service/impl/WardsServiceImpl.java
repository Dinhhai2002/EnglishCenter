package com.english_center.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.english_center.dao.WardsDao;
import com.english_center.entity.Wards;
import com.english_center.service.WardsService;

@Service("WardsService")
@Transactional(rollbackFor = Error.class)
public class WardsServiceImpl implements WardsService{
	
	@Autowired
	WardsDao wardsDao;

	@Override
	public List<Wards> findByDistrictId(int districtId) throws Exception {
		return wardsDao.findByDistrictId(districtId);
	}

}
