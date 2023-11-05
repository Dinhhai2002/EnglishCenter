package com.english_center.dao;

import java.util.List;

import com.english_center.common.utils.Pagination;
import com.english_center.entity.Chapter;
import com.english_center.model.StoreProcedureListResult;

public interface ChapterDao {
	void create(Chapter chapter) throws Exception;

	Chapter findOne(int id) throws Exception;

	void update(Chapter chapter) throws Exception;

	List<Chapter> findByCourseId(int courseId) throws Exception;

	Chapter findByNameAndCourse(String chapterName, int courseId) throws Exception;

	StoreProcedureListResult<Chapter> spGListChapter(int courseId, String keySearch, int status, Pagination pagination,
			int isPagination, int sort) throws Exception;
}
