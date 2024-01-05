package com.english_center.dao.impl;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.english_center.dao.AbstractDao;
import com.english_center.dao.CategoryCourseDao;
import com.english_center.entity.CategoryCourse;

@Repository("CategoryCourseDao")
@Transactional
public class CategoryCourseDaoImpl extends AbstractDao<Integer, CategoryCourse> implements CategoryCourseDao {

	@Override
	public void create(CategoryCourse categoryCourse) throws Exception {
		this.getSession().save(categoryCourse);

	}

	@Override
	public CategoryCourse findOne(int id) throws Exception {
		return this.getSession().find(CategoryCourse.class, id);
	}

	@Override
	public void update(CategoryCourse categoryCourse) throws Exception {
		this.getSession().update(categoryCourse);

	}

	@Override
	public CategoryCourse findByName(String name) throws Exception {
		CriteriaBuilder builder = this.getBuilder();
		CriteriaQuery<CategoryCourse> query = builder.createQuery(CategoryCourse.class);
		Root<CategoryCourse> root = query.from(CategoryCourse.class);
		query.where(builder.equal(root.get("name"), name));

		return this.getSession().createQuery(query).getResultList().stream().findFirst().orElse(null);
	}

	@Override
	public List<CategoryCourse> findAllHavePagination(int page, int limit) throws Exception {
		CriteriaBuilder builder = this.getBuilder();
		CriteriaQuery<CategoryCourse> query = builder.createQuery(CategoryCourse.class);
//		Root<CategoryCourse> root = query.from(CategoryCourse.class);
		int offset = (page - 1) * limit;

		return this.getSession().createQuery(query).setFirstResult(offset).setMaxResults(limit).getResultList();
	}

	@Override
	public List<CategoryCourse> findAll() throws Exception {
		CriteriaBuilder builder = this.getBuilder();
		CriteriaQuery<CategoryCourse> query = builder.createQuery(CategoryCourse.class);
//		Root<CategoryCourse> root = query.from(CategoryCourse.class);

		return this.getSession().createQuery(query).getResultList();
	}

}
