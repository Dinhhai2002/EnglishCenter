package com.english_center.dao;

import java.util.List;

import com.english_center.entity.ClassWeekday;

public interface ClassWeekdayDao {
	
	void create(ClassWeekday classWeekday) throws Exception;

	ClassWeekday findOne(int id) throws Exception;

	void update(ClassWeekday classWeekday) throws Exception;
	
	ClassWeekday findByAll(int classId,int weekdayId,String fromHour,String toHour) throws Exception;
	
	List<ClassWeekday> getAllByClassId(int classId) throws Exception;
}
