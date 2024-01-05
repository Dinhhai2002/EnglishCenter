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
import com.english_center.dao.ResultDao;
import com.english_center.entity.Result;
import com.english_center.model.StoreProcedureListResult;
import com.english_center.model.StoreProcedureResult;

@Repository("ResultDao")
@Transactional
public class ResultDaoImpl extends AbstractDao<Integer, Result> implements ResultDao {

	@Override
	public void create(Result result) throws Exception {
		this.getSession().save(result);

	}

	@Override
	public Result findOne(int id) throws Exception {
		return this.getSession().find(Result.class, id);
	}

	@Override
	public void update(Result result) throws Exception {
		this.getSession().update(result);

	}

	@Override
	public List<Result> findByUserId(int userId) throws Exception {
		CriteriaBuilder builder = this.getBuilder();
		CriteriaQuery<Result> query = builder.createQuery(Result.class);
		Root<Result> root = query.from(Result.class);
		query.where(builder.equal(root.get("userId"), userId));

		return this.getSession().createQuery(query).getResultList();
	}

	@Override
	public List<Result> findByExamId(int examId) throws Exception {
		CriteriaBuilder builder = this.getBuilder();
		CriteriaQuery<Result> query = builder.createQuery(Result.class);
		Root<Result> root = query.from(Result.class);
		query.where(builder.equal(root.get("examId"), examId));

		return this.getSession().createQuery(query).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public StoreProcedureListResult<Result> spGResult(int userId, int examId, String keySearch, Pagination pagination)
			throws Exception {
		StoredProcedureQuery query = this.getSession().createStoredProcedureQuery("sp_g_list_result", Result.class)
				.registerStoredProcedureParameter("userId", Integer.class, ParameterMode.IN)
				.registerStoredProcedureParameter("examId", Integer.class, ParameterMode.IN)
				.registerStoredProcedureParameter("keySearch", String.class, ParameterMode.IN)
				.registerStoredProcedureParameter("_limit", Integer.class, ParameterMode.IN)
				.registerStoredProcedureParameter("_offset", Integer.class, ParameterMode.IN)

				.registerStoredProcedureParameter("total_record", Integer.class, ParameterMode.OUT)
				.registerStoredProcedureParameter("status_code", Integer.class, ParameterMode.OUT)
				.registerStoredProcedureParameter("message_error", String.class, ParameterMode.OUT);

		query.setParameter("userId", userId);
		query.setParameter("examId", examId);
		query.setParameter("keySearch", keySearch);
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

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public StoreProcedureResult<Result> spUCreateResult(int userId, int examId, String listAnswerJson,
			String timeComplete, int totalQuestionSkip) throws Exception {
		StoredProcedureQuery query = this.getSession().createStoredProcedureQuery("sp_u_create_result", Result.class)
				.registerStoredProcedureParameter("userId", Integer.class, ParameterMode.IN)
				.registerStoredProcedureParameter("examId", Integer.class, ParameterMode.IN)
				.registerStoredProcedureParameter("listAnswerJson", String.class, ParameterMode.IN)
				.registerStoredProcedureParameter("timeComplete", String.class, ParameterMode.IN)
				.registerStoredProcedureParameter("totalQuestionSkip", Integer.class, ParameterMode.IN)

				.registerStoredProcedureParameter("status_code", Integer.class, ParameterMode.OUT)
				.registerStoredProcedureParameter("message_error", String.class, ParameterMode.OUT);

		query.setParameter("userId", userId);
		query.setParameter("examId", examId);
		query.setParameter("listAnswerJson", listAnswerJson);
		query.setParameter("timeComplete", timeComplete);
		query.setParameter("totalQuestionSkip", totalQuestionSkip);

		int statusCode = (int) query.getOutputParameterValue("status_code");
		String messageError = query.getOutputParameterValue("message_error").toString();

		switch (StoreProcedureStatusCodeEnum.valueOf(statusCode)) {
		case SUCCESS:
			return new StoreProcedureResult(statusCode, messageError,
					query.getResultList().stream().findFirst().orElse(null));
		case INPUT_INVALID:
			throw new TechresHttpException(HttpStatus.BAD_REQUEST, messageError);
		default:
			throw new Exception(messageError);
		}
	}

}
