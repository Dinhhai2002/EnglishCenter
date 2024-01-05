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
import com.english_center.dao.UserCourseProgressDao;
import com.english_center.entity.UserCourseProgress;
import com.english_center.model.StoreProcedureListResult;

@Repository("UserCourseProgressDao")
@Transactional
public class UserCourseProgressDaoImpl extends AbstractDao<Integer, UserCourseProgress>
		implements UserCourseProgressDao {

	@Override
	public void create(UserCourseProgress userCourseProgress) throws Exception {
		this.getSession().save(userCourseProgress);

	}

	@Override
	public UserCourseProgress findOne(int id) throws Exception {
		return this.getSession().find(UserCourseProgress.class, id);
	}

	@Override
	public void update(UserCourseProgress userCourseProgress) throws Exception {
		this.getSession().update(userCourseProgress);

	}

	@Override
	public UserCourseProgress findByLessonsAndUser(int lessonsId, int userId) throws Exception {
		CriteriaBuilder builder = this.getBuilder();
		CriteriaQuery<UserCourseProgress> query = builder.createQuery(UserCourseProgress.class);
		Root<UserCourseProgress> root = query.from(UserCourseProgress.class);
		Predicate condition1 = builder.equal(root.get("lessonsId"), lessonsId);
		Predicate condition2 = builder.equal(root.get("userId"), userId);
		Predicate combinedCondition = builder.and(condition1, condition2);

		query.where(combinedCondition);

		return this.getSession().createQuery(query).getResultList().stream().findFirst().orElse(null);
	}

	@Override
	public List<UserCourseProgress> findByCourseAndUser(int courseId, int userId) throws Exception {
		CriteriaBuilder builder = this.getBuilder();
		CriteriaQuery<UserCourseProgress> query = builder.createQuery(UserCourseProgress.class);
		Root<UserCourseProgress> root = query.from(UserCourseProgress.class);
		Predicate condition1 = builder.equal(root.get("courseId"), courseId);
		Predicate condition2 = builder.equal(root.get("userId"), userId);
		Predicate combinedCondition = builder.and(condition1, condition2);

		query.where(combinedCondition);

		return this.getSession().createQuery(query).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public StoreProcedureListResult<UserCourseProgress> spGUserCourseProgress(int userId, int courseId, int chapterId,
			int lessonsId, int isCompleted) throws Exception {
		StoredProcedureQuery query = this.getSession()
				.createStoredProcedureQuery("sp_g_user_course_progress", UserCourseProgress.class)

				.registerStoredProcedureParameter("userId", Integer.class, ParameterMode.IN)
				.registerStoredProcedureParameter("courseId", Integer.class, ParameterMode.IN)
				.registerStoredProcedureParameter("chapterId", Integer.class, ParameterMode.IN)
				.registerStoredProcedureParameter("lessonsId", Integer.class, ParameterMode.IN)
				.registerStoredProcedureParameter("isCompleted", Integer.class, ParameterMode.IN)

				.registerStoredProcedureParameter("status_code", Integer.class, ParameterMode.OUT)
				.registerStoredProcedureParameter("message_error", String.class, ParameterMode.OUT);

		query.setParameter("userId", userId);
		query.setParameter("courseId", courseId);
		query.setParameter("chapterId", chapterId);
		query.setParameter("lessonsId", lessonsId);
		query.setParameter("isCompleted", isCompleted);

		int statusCode = (int) query.getOutputParameterValue("status_code");
		String messageError = query.getOutputParameterValue("message_error").toString();

		switch (StoreProcedureStatusCodeEnum.valueOf(statusCode)) {
		case SUCCESS:
			return new StoreProcedureListResult<>(statusCode, messageError, query.getResultList());
		case INPUT_INVALID:
			throw new TechresHttpException(HttpStatus.BAD_REQUEST, messageError);
		default:
			throw new Exception(messageError);
		}
	}

}
