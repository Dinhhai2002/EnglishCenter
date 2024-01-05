package com.english_center.dao;

import java.util.List;

import com.english_center.entity.ResultDetail;

public interface ResultDetailDao {
	void create(ResultDetail resultDetail) throws Exception;

	ResultDetail findOne(int id) throws Exception;

	void update(ResultDetail resultDetail) throws Exception;

	List<ResultDetail> getListByResultId(int resultId) throws Exception;

}
