package com.english_center.service;

import java.util.List;

import com.english_center.entity.Cities;

public interface CityService {
	List<Cities> getAll() throws Exception;
}
