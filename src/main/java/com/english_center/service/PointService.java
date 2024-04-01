package com.english_center.service;

import com.english_center.entity.Point;

public interface PointService {
	void create(Point point) throws Exception;

	Point findOne(int id) throws Exception;

	void update(Point point) throws Exception;

	Point findOneByUserId(int userId) throws Exception;
}
