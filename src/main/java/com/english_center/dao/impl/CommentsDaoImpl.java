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
import com.english_center.dao.AbstractDao;
import com.english_center.dao.CommentsDao;
import com.english_center.entity.Comments;

@Repository("CommentsDao")
@Transactional
public class CommentsDaoImpl extends AbstractDao<Integer, Comments> implements CommentsDao {

	@Override
	public void create(Comments comments) throws Exception {
		this.getSession().save(comments);
	}

	@Override
	public Comments findOne(int id) throws Exception {
		return this.getSession().find(Comments.class, id);
	}

	@Override
	public void update(Comments comments) throws Exception {
		this.getSession().update(comments);
	}

	@Override
	public void delete(Comments comments) {
		this.getSession().delete(comments);
	}

	@Override
	public List<Comments> findByExamId(int examId) throws Exception {
		CriteriaBuilder builder = this.getBuilder();
		CriteriaQuery<Comments> query = builder.createQuery(Comments.class);
		Root<Comments> root = query.from(Comments.class);
		query.where(builder.equal(root.get("examId"), examId));

		return this.getSession().createQuery(query).getResultList();
	}

	@Override
	public int countComments(int examId) throws Exception {
		StoredProcedureQuery query = this.getSession().createStoredProcedureQuery("sp_g_count_comments")
				.registerStoredProcedureParameter("examId", Integer.class, ParameterMode.IN)

				.registerStoredProcedureParameter("total_record", Integer.class, ParameterMode.OUT)
				.registerStoredProcedureParameter("status_code", Integer.class, ParameterMode.OUT)
				.registerStoredProcedureParameter("message_error", String.class, ParameterMode.OUT);

		query.setParameter("examId", examId);

		int statusCode = (int) query.getOutputParameterValue("status_code");
		String messageError = query.getOutputParameterValue("message_error").toString();

		switch (StoreProcedureStatusCodeEnum.valueOf(statusCode)) {
		case SUCCESS:
			int totalRecord = (int) query.getOutputParameterValue("total_record");
			return totalRecord;
		case INPUT_INVALID:
			throw new TechresHttpException(HttpStatus.BAD_REQUEST, messageError);
		default:
			throw new Exception(messageError);
		}
	}

}
