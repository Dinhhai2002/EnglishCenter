package com.english_center.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.english_center.common.utils.Pagination;
import com.english_center.common.utils.StringErrorValue;
import com.english_center.common.utils.Utils;
import com.english_center.entity.Exam;
import com.english_center.entity.Result;
import com.english_center.entity.ResultDetail;
import com.english_center.entity.Users;
import com.english_center.model.StoreProcedureListResult;
import com.english_center.model.StoreProcedureResult;
import com.english_center.request.CRUDResultRequest;
import com.english_center.response.BaseListDataResponse;
import com.english_center.response.BaseResponse;
import com.english_center.response.ResultResponse;

@RestController
@RequestMapping("/api/v1/result")
public class ResultController extends BaseController {

	@PostMapping("/create")
	public ResponseEntity<BaseResponse<ResultResponse>> create(@Valid @RequestBody CRUDResultRequest wrapper)
			throws Exception {

		BaseResponse<ResultResponse> response = new BaseResponse<>();
		Users user = this.getUser();
		Exam exam = examService.findOne(wrapper.getExamId());

		if (exam == null) {
			response.setStatus(HttpStatus.BAD_REQUEST);
			response.setMessageError(StringErrorValue.EXAM_NOT_FOUND);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

		StoreProcedureResult<Result> result = resultService.spUCreateResult(user.getId(), exam.getId(),
				Utils.convertListObjectToJsonArray(wrapper.getListAnswer()), wrapper.getTimeComplete(),
				wrapper.getTotalQuestionSkip());

		List<ResultDetail> resultDetails = resultDetailService.getListByResultId(result.getResult().getId());

		response.setData(new ResultResponse(result.getResult(), resultDetails));
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<BaseResponse<ResultResponse>> findOne(@PathVariable("id") int id) throws Exception {

		BaseResponse<ResultResponse> response = new BaseResponse<>();
		Result result = resultService.findOne(id);

		if (result == null) {
			response.setStatus(HttpStatus.BAD_REQUEST);
			response.setMessageError(StringErrorValue.RESULTS_NOT_FOUND);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
		List<ResultDetail> resultDetails = resultDetailService.getListByResultId(result.getId());
		response.setData(new ResultResponse(result, resultDetails));

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/get-by-user")
	public ResponseEntity<BaseResponse<List<ResultResponse>>> findByUserId() throws Exception {

		BaseResponse<List<ResultResponse>> response = new BaseResponse<>();
		Users user = this.getUser();

		List<Result> listResult = resultService.findByUserId(user.getId());

		response.setData(new ResultResponse().mapToList(listResult));

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("")
	public ResponseEntity<BaseResponse<BaseListDataResponse<ResultResponse>>> findByUserId(
			@RequestParam(name = "exam_id", required = false, defaultValue = "-1") int examId,
			@RequestParam(name = "key_search", required = false, defaultValue = "") String keySearch,
			@RequestParam(name = "page", required = false, defaultValue = "1") int page,
			@RequestParam(name = "limit", required = false, defaultValue = "10") int limit) throws Exception {

		BaseResponse<BaseListDataResponse<ResultResponse>> response = new BaseResponse<>();
		Users user = this.getUser();
		Pagination pagination = new Pagination(page, limit);
		StoreProcedureListResult<Result> data = resultService.spGResult(user.getId(), examId, keySearch, pagination);

		BaseListDataResponse<ResultResponse> listData = new BaseListDataResponse<>();
		listData.setList(new ResultResponse().mapToList(data.getResult()));
		listData.setTotalRecord(data.getTotalRecord());

		response.setData(listData);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
