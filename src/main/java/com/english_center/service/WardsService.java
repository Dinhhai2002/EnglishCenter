package com.english_center.service;

import java.util.List;

import com.english_center.entity.Wards;

public interface WardsService {
	List<Wards> findByDistrictId(int districtId) throws Exception;
}
