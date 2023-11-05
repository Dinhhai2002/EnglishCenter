package com.english_center.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.english_center.dao.DistrictDao;
import com.english_center.entity.Districts;

@Service("DistrictService")
@Transactional(rollbackOn = Error.class )
public class DistrictServiceImpl implements DistrictService {
	
	@Autowired
	DistrictDao districtDao;
	
	@Override
	public List<Districts> findByCityId(int cityId) throws Exception {
		return districtDao.findByCityId(cityId);
	}

}
