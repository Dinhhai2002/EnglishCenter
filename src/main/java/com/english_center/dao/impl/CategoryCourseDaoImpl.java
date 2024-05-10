package com.english_center.dao.impl;

import java.util.List;

import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import com.english_center.common.enums.StoreProcedureStatusCodeEnum;
import com.english_center.common.exception.TechresHttpException;
import com.english_center.common.utils.Pagination;
import com.english_center.dao.AbstractDao;
import com.english_center.dao.CategoryCourseDao;
import com.english_center.entity.CategoryCourse;
import com.english_center.model.StoreProcedureListResult;

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

//	@Override
//	public List<CategoryCourse> findAllHavePagination(int page, int limit) throws Exception {
//		CriteriaBuilder builder = this.getBuilder();
//		CriteriaQuery<CategoryCourse> query = builder.createQuery(CategoryCourse.class);
//		Root<CategoryCourse> root = query.from(CategoryCourse.class);
//		int offset = (page - 1) * limit;
//
//		return this.getSession().createQuery(query).setFirstResult(offset).setMaxResults(limit).getResultList();
//	}

	@Override
	public List<CategoryCourse> findAll() throws Exception {
		CriteriaBuilder builder = this.getBuilder();
		CriteriaQuery<CategoryCourse> query = builder.createQuery(CategoryCourse.class);
		Root<CategoryCourse> root = query.from(CategoryCourse.class);

		return this.getSession().createQuery(query).getResultList();
	}

	@Override
	public StoreProcedureListResult<CategoryCourse> spGListCategoryCourse(String keySearch, int status,
			Pagination pagination) throws Exception {
		StoredProcedureQuery query = this.getSession().createStoredProcedureQuery("sp_g_list_category_course", CategoryCourse.class)
				.registerStoredProcedureParameter("keySearch", String.class, ParameterMode.IN)
				.registerStoredProcedureParameter("status", Integer.class, ParameterMode.IN)
				.registerStoredProcedureParameter("_limit", Integer.class, ParameterMode.IN)
				.registerStoredProcedureParameter("_offset", Integer.class, ParameterMode.IN)

				.registerStoredProcedureParameter("total_record", Integer.class, ParameterMode.OUT)
				.registerStoredProcedureParameter("status_code", Integer.class, ParameterMode.OUT)
				.registerStoredProcedureParameter("message_error", String.class, ParameterMode.OUT);

		query.setParameter("keySearch", keySearch);
		query.setParameter("status", status);
		query.setParameter("_limit", pagination.getLimit());
		query.setParameter("_offset", pagination.getOffset());

		int statusCode = (int) query.getOutputParameterValue("status_code");
		String messageError = query.getOutputParameterValue("message_error").toString();

		switch (StoreProcedureStatusCodeEnum.valueOf(statusCode)) {
		case SUCCESS:
			int totalRecord = (int) query.getOutputParameterValue("total_record");
			return new StoreProcedureListResult<>(statusCode, messageError, totalRecord, query.getResultList());
		case INPUT_INVALID:
			throw new TechresHttpException(HttpStatus.BAD_REQUEST, messageError);
		default:
			throw new Exception(messageError);
		}
	}

}
