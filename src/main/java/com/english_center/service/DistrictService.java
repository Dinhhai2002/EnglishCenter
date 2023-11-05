package com.english_center.service;

import java.util.List;

import com.english_center.entity.Districts;

public interface DistrictService {
	List<Districts> findByCityId(int cityId) throws Exception;
}
