package com.english_center.dao;

import com.english_center.entity.Target;

public interface TargetDao {
	void create(Target target) throws Exception;

	Target findOne(int id) throws Exception;

	void update(Target target) throws Exception;
	
	Target findByUserId(int userId) throws Exception;
}
