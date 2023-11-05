package com.english_center.dao;

import com.english_center.entity.ClassStudent;

public interface ClassStudentDao {
	void create(ClassStudent classStudent) throws Exception;

	ClassStudent findOne(int id) throws Exception;

	void update(ClassStudent classStudent) throws Exception;
	
	ClassStudent findByClassIdAndStudent(int classId,int studentId) throws Exception;

}
