package com.english_center.dao.impl;

import java.math.BigDecimal;
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
import com.english_center.dao.CourseDao;
import com.english_center.entity.Course;
import com.english_center.model.StoreProcedureListResult;

@Repository("CourseDao")
@Transactional
public class CourseDaoImpl extends AbstractDao<Integer, Course> implements CourseDao {

	@Override
	public Course findOne(int id) throws Exception {
		return this.getSession().find(Course.class, id);
	}

	@Override
	public void update(Course course) throws Exception {
		this.getSession().update(course);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Course spUCreateCourse(String name, String description, BigDecimal price, int isFree) throws Exception {
		StoredProcedureQuery query = this.getSession().createStoredProcedureQuery("sp_u_create_course", Course.class)
				.registerStoredProcedureParameter("_name", String.class, ParameterMode.IN)
				.registerStoredProcedureParameter("_description", String.class, ParameterMode.IN)
				.registerStoredProcedureParameter("_price", BigDecimal.class, ParameterMode.IN)
				.registerStoredProcedureParameter("isFree", Integer.class, ParameterMode.IN)

				.registerStoredProcedureParameter("status_code", Integer.class, ParameterMode.OUT)
				.registerStoredProcedureParameter("message_error", String.class, ParameterMode.OUT);

		query.setParameter("_name", name);
		query.setParameter("_description", description);
		query.setParameter("_price", price);
		query.setParameter("isFree", isFree);

		int statusCode = (int) query.getOutputParameterValue("status_code");
		String messageError = query.getOutputParameterValue("message_error").toString();

		switch (StoreProcedureStatusCodeEnum.valueOf(statusCode)) {
		case SUCCESS:
			return (Course) query.getResultList().stream().findFirst().orElse(null);
		case INPUT_INVALID:
			throw new TechresHttpException(HttpStatus.BAD_REQUEST, messageError);
		default:
			throw new Exception(messageError);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Course spUUpdateCourse(int id, String name, int lessons, String description, BigDecimal price, int isFree,
			BigDecimal discountPercent) throws Exception {
		StoredProcedureQuery query = this.getSession().createStoredProcedureQuery("sp_u_update_course", Course.class)
				.registerStoredProcedureParameter("_id", Integer.class, ParameterMode.IN)
				.registerStoredProcedureParameter("_name", String.class, ParameterMode.IN)
				.registerStoredProcedureParameter("_lessons", Integer.class, ParameterMode.IN)
				.registerStoredProcedureParameter("_description", String.class, ParameterMode.IN)
				.registerStoredProcedureParameter("_price", BigDecimal.class, ParameterMode.IN)
				.registerStoredProcedureParameter("isFree", Integer.class, ParameterMode.IN)
				.registerStoredProcedureParameter("discountPercent", BigDecimal.class, ParameterMode.IN)

				.registerStoredProcedureParameter("status_code", Integer.class, ParameterMode.OUT)
				.registerStoredProcedureParameter("message_error", String.class, ParameterMode.OUT);

		query.setParameter("_id", id);
		query.setParameter("_name", name);
		query.setParameter("_lessons", lessons);
		query.setParameter("_description", description);
		query.setParameter("_price", price);
		query.setParameter("isFree", isFree);
		query.setParameter("discountPercent", discountPercent);

		int statusCode = (int) query.getOutputParameterValue("status_code");
		String messageError = query.getOutputParameterValue("message_error").toString();

		switch (StoreProcedureStatusCodeEnum.valueOf(statusCode)) {
		case SUCCESS:
			return (Course) query.getResultList().stream().findFirst().orElse(null);
		case INPUT_INVALID:
			throw new TechresHttpException(HttpStatus.BAD_REQUEST, messageError);
		default:
			throw new Exception(messageError);
		}

	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	public List<Course> findAll() throws Exception {
		return (List<Course>) this.getSession().createCriteria(Course.class).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public StoreProcedureListResult<Course> spGCourse(int categoryCourseId, String keySearch, int status,
			int isPagination, Pagination pagination) throws Exception {
		StoredProcedureQuery query = this.getSession().createStoredProcedureQuery("sp_g_list_course", Course.class)
				.registerStoredProcedureParameter("categoryCourseId", Integer.class, ParameterMode.IN)
				.registerStoredProcedureParameter("keySearch", String.class, ParameterMode.IN)
				.registerStoredProcedureParameter("status", Integer.class, ParameterMode.IN)
				.registerStoredProcedureParameter("isPagination", Integer.class, ParameterMode.IN)
				.registerStoredProcedureParameter("_limit", Integer.class, ParameterMode.IN)
				.registerStoredProcedureParameter("_offset", Integer.class, ParameterMode.IN)

				.registerStoredProcedureParameter("total_record", Integer.class, ParameterMode.OUT)
				.registerStoredProcedureParameter("status_code", Integer.class, ParameterMode.OUT)
				.registerStoredProcedureParameter("message_error", String.class, ParameterMode.OUT);

		query.setParameter("categoryCourseId", categoryCourseId);
		query.setParameter("keySearch", keySearch);
		query.setParameter("status", status);
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
	public Course findByName(String name) throws Exception {
		CriteriaBuilder builder = this.getBuilder();
		CriteriaQuery<Course> query = builder.createQuery(Course.class);
		Root<Course> root = query.from(Course.class);
		query.where(builder.equal(root.get("name"), name));

		return this.getSession().createQuery(query).getResultList().stream().findFirst().orElse(null);
	}

}
