package com.english_center.service;

import java.util.List;

import com.english_center.entity.Level;

public interface LevelService {
	void create(Level level) throws Exception;

	Level findOne(int id) throws Exception;

	void update(Level level) throws Exception;
	
	Level findByNameAndCode(String name,String code) throws Exception;
	
	List<Level> findAll() throws Exception;
}
