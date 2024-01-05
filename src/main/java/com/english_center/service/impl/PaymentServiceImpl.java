package com.english_center.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.english_center.common.utils.Pagination;
import com.english_center.dao.PaymentDao;
import com.english_center.entity.Payment;
import com.english_center.model.StoreProcedureListResult;
import com.english_center.service.PaymentService;

@Service("PaymentService")
@Transactional(rollbackFor = Error.class)
public class PaymentServiceImpl implements PaymentService {

	@Autowired
	PaymentDao paymentDao;

	@Override
	public void create(Payment payment) throws Exception {
		paymentDao.create(payment);
	}

	@Override
	public Payment findOne(int id) throws Exception {
		return paymentDao.findOne(id);
	}

	@Override
	public void update(Payment payment) throws Exception {
		paymentDao.update(payment);
	}

	@Override
	public StoreProcedureListResult<Payment> spGPayment(int courseId, int studentId, Pagination pagination,
			int isPagination) throws Exception {
		return paymentDao.spGPayment(courseId, studentId, pagination, isPagination);
	}

}
