package com.english_center.dao;

import java.util.List;

import com.english_center.common.utils.Pagination;
import com.english_center.entity.Result;
import com.english_center.model.StoreProcedureListResult;
import com.english_center.model.StoreProcedureResult;

public interface ResultDao {
	void create(Result result) throws Exception;

	Result findOne(int id) throws Exception;

	void update(Result result) throws Exception;

	List<Result> findByUserId(int userId) throws Exception;

	List<Result> findByExamId(int examId) throws Exception;

	StoreProcedureListResult<Result> spGResult(int userId, int examId, String keySearch, Pagination pagination)
			throws Exception;

	StoreProcedureResult<Result> spUCreateResult(int userId, int examId, String listAnswerJson, String timeComplete,
			int totalQuestionSkip) throws Exception;
}
