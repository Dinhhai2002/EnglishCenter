package com.english_center.dao;

import com.english_center.common.utils.Pagination;
import com.english_center.entity.Payment;
import com.english_center.model.StoreProcedureListResult;

public interface PaymentDao {
	void create(Payment payment) throws Exception;

	Payment findOne(int id) throws Exception;

	void update(Payment payment) throws Exception;
	
	StoreProcedureListResult<Payment> spGPayment(int courseId, int studentId,Pagination pagination, int isPagination) throws Exception;
}
