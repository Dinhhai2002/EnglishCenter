package com.english_center.controller.admin;

import java.math.BigDecimal;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.english_center.common.utils.Pagination;
import com.english_center.controller.BaseController;
import com.english_center.entity.Payment;
import com.english_center.model.StoreProcedureListResult;
import com.english_center.response.BaseResponse;
import com.english_center.response.CountStatisticalResponse;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController extends BaseController {

	@GetMapping("/count-statistical")
	@PreAuthorize("hasAnyAuthority('ADMIN')")
	public ResponseEntity<BaseResponse<CountStatisticalResponse>> getCount() throws Exception {
		BaseResponse<CountStatisticalResponse> response = new BaseResponse<>();

		int countUser = (int) (userService.getAll()).stream().count();
		int countExam = (int) (examService.spGListExam(-1, -1, "", -1, new Pagination(0, 20), 0)).getResult().stream()
				.count();
		int countCourse = (int) (courseService.findAll()).stream().count();

		StoreProcedureListResult<Payment> listPayment = paymentService.spGPayment(-1, -1, new Pagination(0, 10), 0);

		BigDecimal totalAmount = listPayment.getResult().stream().map(Payment::getAmount).reduce(BigDecimal.ZERO,
				BigDecimal::add);

		response.setData(new CountStatisticalResponse(countUser, countExam, countCourse, totalAmount));

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
