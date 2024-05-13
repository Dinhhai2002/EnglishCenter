package com.english_center.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.english_center.common.utils.Pagination;
import com.english_center.dao.PostDao;
import com.english_center.entity.Posts;
import com.english_center.model.PostModel;
import com.english_center.model.StoreProcedureListResult;
import com.english_center.service.PostService;

@Service("PostService")
@Transactional(rollbackFor = Error.class)
public class PostServiceImpl implements PostService {

	@Autowired
	PostDao postDao;

	@Override
	public void create(Posts posts) throws Exception {
		postDao.create(posts);
	}

	@Override
	public Posts findOne(int id) throws Exception {
		return postDao.findOne(id);
	}

	@Override
	public void update(Posts posts) throws Exception {
		postDao.update(posts);

	}

	@Override
	public List<Posts> getAll() throws Exception {
		return postDao.getAll();
	}

	@Override
	public StoreProcedureListResult<PostModel> spGPosts(int userId, int categoryBlogId, String keySearch, int status,
			Pagination pagination) throws Exception {
		return postDao.spGPosts(userId, categoryBlogId, keySearch, status, pagination);
	}

}
