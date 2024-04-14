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
import com.english_center.dao.CategoryExamDao;
import com.english_center.entity.CategoryExam;
import com.english_center.model.StoreProcedureListResult;

@Repository("CategoryExamDao")
@Transactional
public class CategoryExamDaoImpl extends AbstractDao<Integer, CategoryExam> implements CategoryExamDao {

	@Override
	public void create(CategoryExam categoryExam) throws Exception {
		this.getSession().save(categoryExam);
		
	}

	@Override
	public CategoryExam findOne(int id) throws Exception {
		return this.getSession().find(CategoryExam.class,id);
	}

	@Override
	public void update(CategoryExam categoryExam) throws Exception {
		this.getSession().update(categoryExam);		
		
	}

	@Override
	public CategoryExam findByName(String name) throws Exception {
		CriteriaBuilder builder = this.getBuilder();
		CriteriaQuery<CategoryExam> query = builder.createQuery(CategoryExam.class);
		Root<CategoryExam> root = query.from(CategoryExam.class);
		query.where(builder.equal(root.get("name"), name));

		return this.getSession().createQuery(query).getResultList().stream().findFirst().orElse(null);
	}

	@Override
	public List<CategoryExam> getAll() throws Exception {
		CriteriaBuilder builder = this.getBuilder();
		CriteriaQuery<CategoryExam> query = builder.createQuery(CategoryExam.class);
		Root<CategoryExam> root = query.from(CategoryExam.class);
//		query.where(builder.equal(root.get("name"), name));

		return this.getSession().createQuery(query).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public StoreProcedureListResult<CategoryExam> spGListCategoryExam(String keySearch, int status, Pagination pagination)
			throws Exception {
		StoredProcedureQuery query = this.getSession().createStoredProcedureQuery("sp_g_list_category_exam", CategoryExam.class)
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
