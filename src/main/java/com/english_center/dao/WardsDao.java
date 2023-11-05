package com.english_center.dao;

import java.util.List;

import com.english_center.entity.Wards;

public interface WardsDao {
	List<Wards> findByDistrictId(int districtId) throws Exception;
}
