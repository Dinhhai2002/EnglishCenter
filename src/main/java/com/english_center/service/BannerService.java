package com.english_center.service;

import com.english_center.common.utils.Pagination;
import java.util.List;
import com.english_center.entity.Banner;
import com.english_center.model.StoreProcedureListResult;

public interface BannerService {
	void create(Banner banner) throws Exception;

	Banner findOne(int id) throws Exception;

	void update(Banner banner) throws Exception;

	StoreProcedureListResult<Banner> getAll(int status, Pagination pagination) throws Exception;
}
