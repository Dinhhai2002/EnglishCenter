package com.english_center.dao;

import java.util.List;

import com.english_center.entity.Districts;

public interface DistrictDao {
	List<Districts> findByCityId(int cityId) throws Exception;
}
