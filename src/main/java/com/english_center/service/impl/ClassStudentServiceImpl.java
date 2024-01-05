package com.english_center.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.english_center.dao.ClassStudentDao;
import com.english_center.entity.ClassStudent;
import com.english_center.service.ClassStudentService;

@Service("ClassStudentService")
@Transactional(rollbackFor = Error.class)
public class ClassStudentServiceImpl implements ClassStudentService {
	
	@Autowired
	ClassStudentDao classStudentDao;

	@Override
	public void create(ClassStudent classStudent) throws Exception {
		classStudentDao.create(classStudent);
	}

	@Override
	public ClassStudent findOne(int id) throws Exception {
		
		return classStudentDao.findOne(id);
	}

	@Override
	public void update(ClassStudent classStudent) throws Exception {
		classStudentDao.update(classStudent);
	}

	@Override
	public ClassStudent findByClassIdAndStudent(int classId, int studentId) throws Exception {
		return classStudentDao.findByClassIdAndStudent(classId, studentId);
	}

}
