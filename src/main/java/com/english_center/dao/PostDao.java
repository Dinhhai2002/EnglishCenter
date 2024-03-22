package com.english_center.dao;

import java.util.List;

import com.english_center.common.utils.Pagination;
import com.english_center.entity.Posts;
import com.english_center.model.PostModel;
import com.english_center.model.StoreProcedureListResult;

public interface PostDao {
	void create(Posts posts) throws Exception;

	Posts findOne(int id) throws Exception;

	void update(Posts posts) throws Exception;

	List<Posts> getAll() throws Exception;

	StoreProcedureListResult<PostModel> spGPosts(int categoryBlogId, String keySearch, int status, Pagination pagination) throws Exception;
}
