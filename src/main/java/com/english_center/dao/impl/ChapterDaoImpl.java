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
import com.english_center.common.utils.Pagination;
import com.english_center.dao.AbstractDao;
import com.english_center.dao.ChapterDao;
import com.english_center.entity.Chapter;
import com.english_center.model.StoreProcedureListResult;

@Repository("ChapterDao")
@Transactional
public class ChapterDaoImpl extends AbstractDao<Integer, Chapter> implements ChapterDao {

	@Override
	public void create(Chapter chapter) throws Exception {
		this.getSession().save(chapter);
	}

	@Override
	public Chapter findOne(int id) throws Exception {
		return this.getSession().find(Chapter.class, id);
	}

	@Override
	public void update(Chapter chapter) throws Exception {
		this.getSession().update(chapter);
	}

	@Override
	public List<Chapter> findByCourseId(int courseId) throws Exception {
		CriteriaBuilder builder = this.getBuilder();
		CriteriaQuery<Chapter> query = builder.createQuery(Chapter.class);
		Root<Chapter> root = query.from(Chapter.class);
		query.where(builder.equal(root.get("courseId"), courseId));

		return this.getSession().createQuery(query).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public StoreProcedureListResult<Chapter> spGListChapter(int courseId, String keySearch, int status,
			Pagination pagination, int isPagination, int sort) throws Exception {
		StoredProcedureQuery query = this.getSession().createStoredProcedureQuery("sp_g_list_chapter", Chapter.class)
				.registerStoredProcedureParameter("courseId", Integer.class, ParameterMode.IN)
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

	@Override
	public Chapter findByNameAndCourse(String chapterName, int courseId) throws Exception {
		CriteriaBuilder builder = this.getBuilder();
		CriteriaQuery<Chapter> query = builder.createQuery(Chapter.class);
		Root<Chapter> root = query.from(Chapter.class);
		Predicate condition1 = builder.equal(root.get("courseId"), courseId);
		Predicate condition2 = builder.equal(root.get("name"), chapterName);
		Predicate combinedCondition = builder.and(condition1, condition2);

		query.where(combinedCondition);

		return this.getSession().createQuery(query).getResultList().stream().findFirst().orElse(null);
	}

}
