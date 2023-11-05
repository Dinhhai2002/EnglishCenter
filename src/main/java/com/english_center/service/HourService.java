package com.english_center.service;

import java.util.List;

import com.english_center.common.utils.Pagination;
import com.english_center.entity.Hour;
import com.english_center.model.StoreProcedureListResult;

public interface HourService {
	void create(Hour hour) throws Exception;

	Hour findOne(int id) throws Exception;

	void update(Hour hour) throws Exception;
	
	List<Hour> getAll() throws Exception;
	
	StoreProcedureListResult<Hour> getAllHavePagination(int weekdayId, Pagination pagination) throws Exception;
}
