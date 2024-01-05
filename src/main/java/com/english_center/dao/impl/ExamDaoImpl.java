package com.english_center.dao.impl;

import java.util.List;

import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import com.english_center.common.enums.StoreProcedureStatusCodeEnum;
import com.english_center.common.exception.TechresHttpException;
import com.english_center.dao.AbstractDao;
import com.english_center.dao.ExamDao;
import com.english_center.entity.Exam;
import com.english_center.model.StoreProcedureListResult;

@Repository("ExamDao")
@SuppressWarnings("unchecked")
@Transactional
public class ExamDaoImpl extends AbstractDao<Integer, Exam> implements ExamDao {

	@Override
	public void create(Exam exam) throws Exception {
		this.getSession().save(exam);

	}

	@Override
	public Exam findOne(int id) throws Exception {
		return this.getSession().find(Exam.class, id);
	}

	@Override
	public void update(Exam exam) throws Exception {
		this.getSession().update(exam);

	}

	@Override
	public Exam findByName(String name) throws Exception {
		CriteriaBuilder builder = this.getBuilder();
		CriteriaQuery<Exam> query = builder.createQuery(Exam.class);
		Root<Exam> root = query.from(Exam.class);
		query.where(builder.equal(root.get("name"), name));

		return this.getSession().createQuery(query).getResultList().stream().findFirst().orElse(null);
	}

	@Override
	public List<Exam> findByTopicId(int topicId) throws Exception {
		CriteriaBuilder builder = this.getBuilder();
		CriteriaQuery<Exam> query = builder.createQuery(Exam.class);
		Root<Exam> root = query.from(Exam.class);

		Predicate condition1 = builder.equal(root.get("topicId"), topicId);
		Predicate condition2 = builder.equal(root.get("status"), 1);
		Predicate combinedCondition = builder.and(condition1, condition2);

		query.where(combinedCondition);

		return this.getSession().createQuery(query).getResultList();
	}

	@Override
	public List<Exam> findByCategory(int categoryExamId) throws Exception {
		CriteriaBuilder builder = this.getBuilder();
		CriteriaQuery<Exam> query = builder.createQuery(Exam.class);
		Root<Exam> root = query.from(Exam.class);
		Predicate condition1 = builder.equal(root.get("categoryExamId"), categoryExamId);
		Predicate condition2 = builder.equal(root.get("status"), 1);
		Predicate combinedCondition = builder.and(condition1, condition2);

		query.where(combinedCondition);

		return this.getSession().createQuery(query).getResultList();
	}

	@Override
	public StoreProcedureListResult<Exam> spGListExam(int categoryExamId, int topicExamId, String keySearch, int status,
			int limit, int page, int isPagination) throws Exception {
		StoredProcedureQuery query = this.getSession().createStoredProcedureQuery("sp_g_list_exam", Exam.class)
				.registerStoredProcedureParameter("categoryExamId", Integer.class, ParameterMode.IN)
				.registerStoredProcedureParameter("topicExamId", Integer.class, ParameterMode.IN)
				.registerStoredProcedureParameter("status", Integer.class, ParameterMode.IN)
				.registerStoredProcedureParameter("keySearch", String.class, ParameterMode.IN)
				.registerStoredProcedureParameter("_limit", Integer.class, ParameterMode.IN)
				.registerStoredProcedureParameter("_offset", Integer.class, ParameterMode.IN)
				.registerStoredProcedureParameter("isPagination", Integer.class, ParameterMode.IN)

				.registerStoredProcedureParameter("total_record", Integer.class, ParameterMode.OUT)
				.registerStoredProcedureParameter("status_code", Integer.class, ParameterMode.OUT)
				.registerStoredProcedureParameter("message_error", String.class, ParameterMode.OUT);

		query.setParameter("categoryExamId", categoryExamId);
		query.setParameter("topicExamId", topicExamId);
		query.setParameter("status", status);
		query.setParameter("keySearch", keySearch);
		query.setParameter("_limit", limit);
		query.setParameter("_offset", page);
		query.setParameter("isPagination", isPagination);

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
