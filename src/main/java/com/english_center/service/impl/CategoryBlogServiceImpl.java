package com.english_center.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.english_center.dao.CategoryBlogDao;
import com.english_center.entity.CategoryBlog;
import com.english_center.service.CategoryBlogService;

@Service("CategoryBlogService")
@Transactional(rollbackFor = Error.class)
public class CategoryBlogServiceImpl implements CategoryBlogService {

	@Autowired
	CategoryBlogDao categoryBlogDao;

	@Override
	public void create(CategoryBlog categoryBlog) throws Exception {
		categoryBlogDao.create(categoryBlog);
	}

	@Override
	public CategoryBlog findOne(int id) throws Exception {
		return categoryBlogDao.findOne(id);
	}

	@Override
	public void update(CategoryBlog categoryBlog) throws Exception {
		categoryBlogDao.update(categoryBlog);

	}

	@Override
	public List<CategoryBlog> getAll(int status) throws Exception {
		return categoryBlogDao.getAll(status);
	}

	@Override
	public CategoryBlog findByName(String categoryName) throws Exception {
		return categoryBlogDao.findByName(categoryName);
	}

}
