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
import com.english_center.dao.ClassDao;
import com.english_center.entity.Class;
import com.english_center.model.StoreProcedureListResult;

@Repository("ClassDao")
@Transactional
public class ClassDaoImpl extends AbstractDao<Integer, Class> implements ClassDao {

	@Override
	public void create(Class _class) throws Exception {
		this.getSession().save(_class);

	}

	@Override
	public Class findOne(int id) throws Exception {
		return this.getSession().find(Class.class, id);
	}

	@Override
	public void update(Class _class) throws Exception {
		this.getSession().update(_class);

	}

	@Override
	public List<Class> getAllByTeacherId(int teacherId) throws Exception {
		CriteriaBuilder builder = this.getBuilder();
		CriteriaQuery<Class> query = builder.createQuery(Class.class);
		Root<Class> root = query.from(Class.class);
		query.where(builder.equal(root.get("teacherId"), teacherId));

		return this.getSession().createQuery(query).getResultList();
	}

	@Override
	public List<Class> getAllByCourseId(int courseId) throws Exception {
		CriteriaBuilder builder = this.getBuilder();
		CriteriaQuery<Class> query = builder.createQuery(Class.class);
		Root<Class> root = query.from(Class.class);
		query.where(builder.equal(root.get("courseId"), courseId));

		return this.getSession().createQuery(query).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public StoreProcedureListResult<Class> spGClass(int courseId, int teacherId, String keySearch, int status,
			Pagination pagination) throws Exception {
		StoredProcedureQuery query = this.getSession().createStoredProcedureQuery("sp_g_list_class", Class.class)
				.registerStoredProcedureParameter("courseId", Integer.class, ParameterMode.IN)
				.registerStoredProcedureParameter("teacherId", Integer.class, ParameterMode.IN)
				.registerStoredProcedureParameter("keySearch", String.class, ParameterMode.IN)
				.registerStoredProcedureParameter("status", Integer.class, ParameterMode.IN)
				.registerStoredProcedureParameter("_limit", Integer.class, ParameterMode.IN)
				.registerStoredProcedureParameter("_offset", Integer.class, ParameterMode.IN)

				.registerStoredProcedureParameter("total_record", Integer.class, ParameterMode.OUT)
				.registerStoredProcedureParameter("status_code", Integer.class, ParameterMode.OUT)
				.registerStoredProcedureParameter("message_error", String.class, ParameterMode.OUT);

		query.setParameter("courseId", courseId);
		query.setParameter("teacherId", teacherId);
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

	@Override
	public List<Class> getAll() throws Exception {
		CriteriaBuilder builder = this.getBuilder();
		CriteriaQuery<Class> query = builder.createQuery(Class.class);
//		Root<Class> root = query.from(Class.class);

		return this.getSession().createQuery(query).getResultList();
	}

}
