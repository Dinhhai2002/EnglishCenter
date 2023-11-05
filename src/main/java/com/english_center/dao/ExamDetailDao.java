package com.english_center.dao;

import com.english_center.entity.ExamDetail;

public interface ExamDetailDao {
	void create(ExamDetail examDetail) throws Exception;

	ExamDetail findOne(int id) throws Exception;

	void update(ExamDetail examDetail) throws Exception;
}
