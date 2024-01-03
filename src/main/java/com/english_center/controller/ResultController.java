package com.english_center.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.english_center.entity.Exam;
import com.english_center.entity.Question;
import com.english_center.entity.Result;
import com.english_center.entity.Users;
import com.english_center.model.StoreProcedureListResult;
import com.english_center.request.CRUDResultRequest;
import com.english_center.response.BaseListDataResponse;
import com.english_center.response.BaseResponse;
import com.english_center.response.ResultResponse;
import com.english_center.service.ExamService;

@RestController
@RequestMapping("/api/v1/result")
public class ResultController extends BaseController {

	@Autowired
	ExamService examService;

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

		List<Question> questions = questionService.getListByExamId(wrapper.getExamId());
		List<Long> count = wrapper.getListAnswer().stream().map(x -> {
			return questions.stream().filter(y -> x.getId() == y.getSort() && x.getKey().equals(y.getResult())).count();
		}).collect(Collectors.toList());

		int sum = count.stream().mapToInt(Long::intValue).sum();

		Result result = new Result();

		result.setExamId(wrapper.getExamId());
		result.setNameExam(exam.getName());
		result.setTimeComplete(wrapper.getTimeComplete());
		result.setTotalQuestionCorrect(sum);
		result.setTotalQuestionSkip(wrapper.getTotalQuestionSkip());
		result.setTotalPoint(new BigDecimal(sum * 5));
		result.setUserId(user.getId());

		resultService.create(result);

		response.setData(new ResultResponse(result));
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

		response.setData(new ResultResponse(result));

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
