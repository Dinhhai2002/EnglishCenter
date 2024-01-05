package com.english_center.dao.impl;

import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;
import javax.transaction.Transactional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import com.english_center.common.enums.StoreProcedureStatusCodeEnum;
import com.english_center.common.exception.TechresHttpException;
import com.english_center.common.utils.Pagination;
import com.english_center.dao.AbstractDao;
import com.english_center.dao.UserCourseDao;
import com.english_center.entity.UserCourse;
import com.english_center.model.StoreProcedureListResult;

@Repository("UserCourseDao")
@Transactional
public class UserCourseDaoImpl extends AbstractDao<Integer, UserCourse> implements UserCourseDao {

	@Override
	public void create(UserCourse userCourse) throws Exception {
		this.getSession().save(userCourse);
	}

	@Override
	public UserCourse findOne(int id) throws Exception {
		return this.getSession().find(UserCourse.class, id);
	}

	@Override
	public void update(UserCourse userCourse) throws Exception {
		this.getSession().update(userCourse);

	}

	@SuppressWarnings("unchecked")
	@Override
	public StoreProcedureListResult<UserCourse> spGUserCourse(int courseId, int isJoinClass, int userId, int isExpired,
			int type, Pagination pagination, int isPagination) throws Exception {
		StoredProcedureQuery query = this.getSession().createStoredProcedureQuery("sp_g_user_course", UserCourse.class)

				.registerStoredProcedureParameter("courseId", Integer.class, ParameterMode.IN)
				.registerStoredProcedureParameter("isJoinClass", Integer.class, ParameterMode.IN)
				.registerStoredProcedureParameter("userId", Integer.class, ParameterMode.IN)
				.registerStoredProcedureParameter("isExpired", Integer.class, ParameterMode.IN)
				.registerStoredProcedureParameter("type", Integer.class, ParameterMode.IN)
				.registerStoredProcedureParameter("isPagination", Integer.class, ParameterMode.IN)
				.registerStoredProcedureParameter("_limit", Integer.class, ParameterMode.IN)
				.registerStoredProcedureParameter("_offset", Integer.class, ParameterMode.IN)

				.registerStoredProcedureParameter("total_record", Integer.class, ParameterMode.OUT)
				.registerStoredProcedureParameter("status_code", Integer.class, ParameterMode.OUT)
				.registerStoredProcedureParameter("message_error", String.class, ParameterMode.OUT);

		query.setParameter("courseId", courseId);
		query.setParameter("isJoinClass", isJoinClass);
		query.setParameter("userId", userId);
		query.setParameter("isExpired", isExpired);
		query.setParameter("type", type);
		query.setParameter("isPagination", isPagination);
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
	public void spUUserCourse(int userCourseId) throws Exception {
		StoredProcedureQuery query = this.getSession().createStoredProcedureQuery("sp_u_user_course", UserCourse.class)

				.registerStoredProcedureParameter("userCourseId", Integer.class, ParameterMode.IN)

				.registerStoredProcedureParameter("status_code", Integer.class, ParameterMode.OUT)
				.registerStoredProcedureParameter("message_error", String.class, ParameterMode.OUT);

		query.setParameter("userCourseId", userCourseId);

		int statusCode = (int) query.getOutputParameterValue("status_code");
		String messageError = query.getOutputParameterValue("message_error").toString();

		switch (StoreProcedureStatusCodeEnum.valueOf(statusCode)) {
		case SUCCESS:

			return;
		case INPUT_INVALID:
			throw new TechresHttpException(HttpStatus.BAD_REQUEST, messageError);
		default:
			throw new Exception(messageError);
		}

	}

//	@Override
//	public List<UserCourse> findByCourseId(int courseId, int isJoinClass) throws Exception {
//		CriteriaBuilder builder = this.getBuilder();
//		CriteriaQuery<UserCourse> query = builder.createQuery(UserCourse.class);
//		Root<UserCourse> root = query.from(UserCourse.class);
//		Predicate condition1 = builder.equal(root.get("courseId"), courseId);
//		Predicate condition2 = builder.equal(root.get("isJoinClass"), isJoinClass);
//		Predicate combinedCondition = builder.and(condition1, condition2);
//
//		query.where(combinedCondition);
//
//		return this.getSession().createQuery(query).getResultList();
//	}

}
