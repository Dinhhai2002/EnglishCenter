package com.english_center.service;

import com.english_center.entity.Target;

public interface TargetService {
	void create(Target target) throws Exception;

	Target findOne(int id) throws Exception;

	void update(Target target) throws Exception;
	
	Target findByUserId(int userId) throws Exception;
}
