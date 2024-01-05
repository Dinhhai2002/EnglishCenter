package com.english_center.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.english_center.common.utils.Pagination;
import com.english_center.dao.ChapterDao;
import com.english_center.entity.Chapter;
import com.english_center.model.StoreProcedureListResult;
import com.english_center.service.ChapterService;

@Service("ChapterService")
@Transactional(rollbackFor = Error.class)
public class ChapterServiceImpl implements ChapterService {

	@Autowired
	ChapterDao chapterDao;

	@Override
	public void create(Chapter chapter) throws Exception {
		chapterDao.create(chapter);

	}

	@Override
	public Chapter findOne(int id) throws Exception {
		return chapterDao.findOne(id);
	}

	@Override
	public void update(Chapter chapter) throws Exception {
		chapterDao.update(chapter);
	}

	@Override
	public List<Chapter> findByCourseId(int courseId) throws Exception {
		return chapterDao.findByCourseId(courseId);
	}

	@Override
	public StoreProcedureListResult<Chapter> spGListChapter(int courseId, String keySearch, int status,
			Pagination pagination, int isPagination) throws Exception {
		return chapterDao.spGListChapter(courseId, keySearch, status, pagination, isPagination, -1);
	}

	@Override
	public Chapter findByNameAndCourse(String chapterName, int courseId) throws Exception {
		return chapterDao.findByNameAndCourse(chapterName, courseId);
	}

}
