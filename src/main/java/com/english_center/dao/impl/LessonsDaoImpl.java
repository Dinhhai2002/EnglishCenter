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
import com.english_center.dao.LessonsDao;
import com.english_center.entity.Lessons;
import com.english_center.model.StoreProcedureListResult;

@Repository("LessonsDao")
@Transactional
public class LessonsDaoImpl extends AbstractDao<Integer, Lessons> implements LessonsDao {

	@Override
	public void create(Lessons lessons) throws Exception {
		this.getSession().save(lessons);
	}

	@Override
	public Lessons findOne(int id) throws Exception {
		return this.getSession().find(Lessons.class, id);
	}

	@Override
	public void update(Lessons lessons) throws Exception {
		this.getSession().update(lessons);
	}

	@Override
	public List<Lessons> findByChapterId(int chapterId) throws Exception {
		CriteriaBuilder builder = this.getBuilder();
		CriteriaQuery<Lessons> query = builder.createQuery(Lessons.class);
		Root<Lessons> root = query.from(Lessons.class);
		query.where(builder.equal(root.get("chapterId"), chapterId));

		return this.getSession().createQuery(query).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public StoreProcedureListResult<Lessons> spGListLessons(int courseId, int chapterId, String keySearch, int status,
			Pagination pagination, int isPagination, int sort) throws Exception {
		StoredProcedureQuery query = this.getSession().createStoredProcedureQuery("sp_g_list_lessons", Lessons.class)
				.registerStoredProcedureParameter("courseId", Integer.class, ParameterMode.IN)
				.registerStoredProcedureParameter("chapterId", Integer.class, ParameterMode.IN)
				.registerStoredProcedureParameter("keySearch", String.class, ParameterMode.IN)
				.registerStoredProcedureParameter("status", Integer.class, ParameterMode.IN)
				.registerStoredProcedureParameter("isPagination", Integer.class, ParameterMode.IN)
				.registerStoredProcedureParameter("sort", Integer.class, ParameterMode.IN)
				.registerStoredProcedureParameter("_limit", Integer.class, ParameterMode.IN)
				.registerStoredProcedureParameter("_offset", Integer.class, ParameterMode.IN)

				.registerStoredProcedureParameter("total_record", Integer.class, ParameterMode.OUT)
				.registerStoredProcedureParameter("status_code", Integer.class, ParameterMode.OUT)
				.registerStoredProcedureParameter("message_error", String.class, ParameterMode.OUT);

		query.setParameter("courseId", courseId);
		query.setParameter("chapterId", chapterId);
		query.setParameter("keySearch", keySearch);
		query.setParameter("status", status);
		query.setParameter("isPagination", isPagination);
		query.setParameter("sort", sort);
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
