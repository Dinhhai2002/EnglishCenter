package com.english_center.service.impl;

import com.english_center.entity.Class;
import com.english_center.model.StoreProcedureListResult;
import com.english_center.service.ClassService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.english_center.common.utils.Pagination;
import com.english_center.dao.ClassDao;

@Service("ClassService")
@Transactional(rollbackFor = Error.class)
public class ClassServiceImpl implements ClassService {

	@Autowired
	ClassDao classDao;

	@Override
	public void create(Class _class) throws Exception {
		classDao.create(_class);
	}

	@Override
	public Class findOne(int id) throws Exception {
		return classDao.findOne(id);
	}

	@Override
	public void update(Class _class) throws Exception {
		classDao.update(_class);
	}

	@Override
	public List<Class> getAllByTeacherId(int teacherId) throws Exception {
		return classDao.getAllByTeacherId(teacherId);
	}

	@Override
	public StoreProcedureListResult<Class> spGClass(int courseId, int teacherId, String keySearch, int status,
			Pagination pagination) throws Exception {

		return classDao.spGClass(courseId,teacherId,keySearch, status, pagination);
	}

	@Override
	public List<Class> getAllByCourseId(int courseId) throws Exception {
		return classDao.getAllByCourseId(courseId);
	}

	@Override
	public List<Class> getAll() throws Exception {
		return classDao.getAll();
	}

}
