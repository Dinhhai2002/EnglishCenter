package com.english_center.dao;

import java.util.List;

import com.english_center.entity.Cities;

public interface CityDao {
	List<Cities> getAll() throws Exception;
}
