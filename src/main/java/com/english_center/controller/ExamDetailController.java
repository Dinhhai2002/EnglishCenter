package com.english_center.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.english_center.entity.ExamDetail;
import com.english_center.request.CRUDExamDetailRequest;
import com.english_center.response.BaseResponse;
import com.english_center.response.ExamDetailResponse;
import com.english_center.service.ExamDetailService;

@RestController
@RequestMapping("/api/v1/exam-detail")
public class ExamDetailController extends BaseController {
	@Autowired
	ExamDetailService examDetailService;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PostMapping("/create")
	public ResponseEntity<BaseResponse<ExamDetailResponse>> create(@Valid @RequestBody CRUDExamDetailRequest wrapper)
			throws Exception {

		BaseResponse<ExamDetailResponse> response = new BaseResponse();

		ExamDetail examDetail = new ExamDetail();

		examDetail.setName(wrapper.getName());
		examDetail.setTotalQuestion(wrapper.getTotalQuestion());
		
		examDetailService.create(examDetail);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
