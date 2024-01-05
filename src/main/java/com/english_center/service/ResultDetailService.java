package com.english_center.service;

import java.util.List;

import com.english_center.entity.ResultDetail;

public interface ResultDetailService {
	void create(ResultDetail resultDetail) throws Exception;

	ResultDetail findOne(int id) throws Exception;

	void update(ResultDetail resultDetail) throws Exception;
	
	List<ResultDetail> getListByResultId(int resultId) throws Exception;
}
