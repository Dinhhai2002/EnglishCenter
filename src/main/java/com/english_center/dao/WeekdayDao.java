package com.english_center.dao;

import java.util.List;

import com.english_center.entity.Weekday;

public interface WeekdayDao {
	void create(Weekday weekday) throws Exception;

	Weekday findOne(int id) throws Exception;

	void update(Weekday weekday) throws Exception;

	List<Weekday> getAll() throws Exception;
}
