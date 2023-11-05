package com.english_center.dao;

import java.util.List;

import com.english_center.entity.Level;

public interface LevelDao {
	void create(Level level) throws Exception;

	Level findOne(int id) throws Exception;

	void update(Level level) throws Exception;

	Level findByNameAndCode(String name, String code) throws Exception;

	List<Level> findAll() throws Exception;
}
