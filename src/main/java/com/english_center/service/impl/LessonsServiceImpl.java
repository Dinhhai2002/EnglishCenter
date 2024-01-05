package com.english_center.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.english_center.common.utils.Pagination;
import com.english_center.dao.LessonsDao;
import com.english_center.entity.Lessons;
import com.english_center.model.StoreProcedureListResult;
import com.english_center.service.LessonsService;

@Service("LessonsService")
@Transactional(rollbackFor = Error.class)
public class LessonsServiceImpl implements LessonsService {

	@Autowired
	LessonsDao lessonsDao;

	@Override
	public void create(Lessons lessons) throws Exception {
		lessonsDao.create(lessons);
	}

	@Override
	public Lessons findOne(int id) throws Exception {
		return lessonsDao.findOne(id);
	}

	@Override
	public void update(Lessons lessons) throws Exception {
		lessonsDao.update(lessons);
	}

	@Override
	public List<Lessons> findByChapterId(int chapterId) throws Exception {
		return lessonsDao.findByChapterId(chapterId);
	}

	@Override
	public StoreProcedureListResult<Lessons> spGListLessons(int courseId, int chapterId, String keySearch, int status,
			Pagination pagination, int isPagination) throws Exception {

		return lessonsDao.spGListLessons(courseId, chapterId, keySearch, status, pagination, isPagination, -1);
	}

}
