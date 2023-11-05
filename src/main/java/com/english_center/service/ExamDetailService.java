package com.english_center.service;

import com.english_center.entity.ExamDetail;

public interface ExamDetailService {
	void create(ExamDetail examDetail) throws Exception;

	ExamDetail findOne(int id) throws Exception;

	void update(ExamDetail examDetail) throws Exception;
}
