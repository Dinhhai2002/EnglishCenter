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
import com.english_center.dao.PaymentDao;
import com.english_center.entity.Payment;
import com.english_center.model.StoreProcedureListResult;

@Repository("PaymentDao")
@Transactional
public class PaymentDaoImpl extends AbstractDao<Integer, Payment> implements PaymentDao {

	@Override
	public void create(Payment payment) throws Exception {
		this.getSession().save(payment);
	}

	@Override
	public Payment findOne(int id) throws Exception {
		return this.getSession().find(Payment.class, id);
	}

	@Override
	public void update(Payment payment) throws Exception {
		this.getSession().update(payment);
	}

	@SuppressWarnings("unchecked")
	@Override
	public StoreProcedureListResult<Payment> spGPayment(int courseId, int studentId, Pagination pagination,
			int isPagination) throws Exception {
		StoredProcedureQuery query = this.getSession().createStoredProcedureQuery("sp_g_payment", Payment.class)

				.registerStoredProcedureParameter("courseId", Integer.class, ParameterMode.IN)
				.registerStoredProcedureParameter("studentId", Integer.class, ParameterMode.IN)
				.registerStoredProcedureParameter("isPagination", Integer.class, ParameterMode.IN)
				.registerStoredProcedureParameter("_limit", Integer.class, ParameterMode.IN)
				.registerStoredProcedureParameter("_offset", Integer.class, ParameterMode.IN)

				.registerStoredProcedureParameter("total_record", Integer.class, ParameterMode.OUT)
				.registerStoredProcedureParameter("status_code", Integer.class, ParameterMode.OUT)
				.registerStoredProcedureParameter("message_error", String.class, ParameterMode.OUT);

		query.setParameter("courseId", courseId);
		query.setParameter("studentId", studentId);
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

}
