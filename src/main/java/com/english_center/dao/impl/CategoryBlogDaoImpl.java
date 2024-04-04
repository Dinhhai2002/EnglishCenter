package com.english_center.dao.impl;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.english_center.dao.AbstractDao;
import com.english_center.dao.CategoryBlogDao;
import com.english_center.entity.CategoryBlog;

@Repository("CategoryBlogDao")
@Transactional
public class CategoryBlogDaoImpl extends AbstractDao<Integer, CategoryBlog> implements CategoryBlogDao {

	@Override
	public void create(CategoryBlog categoryBlog) throws Exception {
		this.getSession().save(categoryBlog);

	}

	@Override
	public CategoryBlog findOne(int id) throws Exception {
		return this.getSession().find(CategoryBlog.class, id);
	}

	@Override
	public void update(CategoryBlog categoryBlog) throws Exception {
		this.getSession().update(categoryBlog);

	}

	@Override
	public List<CategoryBlog> getAll(int status) throws Exception {
		CriteriaBuilder builder = this.getBuilder();
		CriteriaQuery<CategoryBlog> query = builder.createQuery(CategoryBlog.class);
		Root<CategoryBlog> root = query.from(CategoryBlog.class);
		if (status >= 0) {
			query.where(builder.equal(root.get("status"), status));
		}

		return this.getSession().createQuery(query).getResultList();
	}

	@Override
	public CategoryBlog findByName(String categoryName) throws Exception {
		CriteriaBuilder builder = this.getBuilder();
		CriteriaQuery<CategoryBlog> query = builder.createQuery(CategoryBlog.class);
		Root<CategoryBlog> root = query.from(CategoryBlog.class);
		query.where(builder.equal(root.get("name"), categoryName));

		return this.getSession().createQuery(query).getResultList().stream().findFirst().orElse(null);
	}

}
