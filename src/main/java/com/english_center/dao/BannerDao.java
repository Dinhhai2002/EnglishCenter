package com.english_center.dao;

import com.english_center.common.utils.Pagination;
import com.english_center.entity.Banner;
import com.english_center.model.StoreProcedureListResult;
import java.util.List;

public interface BannerDao {
	void create(Banner banner) throws Exception;

	Banner findOne(int id) throws Exception;

	void update(Banner banner) throws Exception;

	StoreProcedureListResult<Banner> getAll(int status, Pagination pagination) throws Exception;
}
