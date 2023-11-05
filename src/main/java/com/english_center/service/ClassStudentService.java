package com.english_center.service;

import com.english_center.entity.ClassStudent;


public interface ClassStudentService {
	void create(ClassStudent classStudent) throws Exception;

	ClassStudent findOne(int id) throws Exception;

	void update(ClassStudent classStudent) throws Exception;
	
	ClassStudent findByClassIdAndStudent(int classId,int studentId) throws Exception;
}
