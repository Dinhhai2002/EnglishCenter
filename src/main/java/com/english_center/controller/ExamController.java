package com.english_center.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.english_center.common.utils.Pagination;
import com.english_center.entity.Exam;
import com.english_center.entity.Question;
import com.english_center.model.StoreProcedureListResult;
import com.english_center.response.BaseListDataResponse;
import com.english_center.response.BaseResponse;
import com.english_center.response.ExamResponse;

@RestController
@RequestMapping("/api/v1/exam")
public class ExamController extends BaseController {

	@GetMapping("")
	public ResponseEntity<BaseResponse<BaseListDataResponse<ExamResponse>>> getAll(
			@RequestParam(name = "category_exam_id", required = false, defaultValue = "-1") int categoryExamId,
			@RequestParam(name = "topic_exam_id", required = false, defaultValue = "-1") int topicExamId,
			@RequestParam(name = "status", required = false, defaultValue = "-1") int status,
			@RequestParam(name = "key_search", required = false, defaultValue = "") String keySearch,
			@RequestParam(name = "page", required = false, defaultValue = "0") int page,
			@RequestParam(name = "limit", required = false, defaultValue = "20") int limit) throws Exception {

		BaseResponse<BaseListDataResponse<ExamResponse>> response = new BaseResponse<>();
		Pagination pagination = new Pagination(page, limit);

		StoreProcedureListResult<Exam> exams = examService.spGListExam(categoryExamId, topicExamId, keySearch, status,
				pagination, 1);

		List<ExamResponse> listExamResponses = getExamResponses(exams.getResult());

		BaseListDataResponse<ExamResponse> listData = new BaseListDataResponse<>();
		listData.setList(listExamResponses);
		listData.setTotalRecord(exams.getTotalRecord());

		response.setData(listData);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@GetMapping("/{id}/get-audio")
	public ResponseEntity<BaseResponse> findAudioByExam(@PathVariable("id") int id) throws Exception {
		BaseResponse response = new BaseResponse();

		response.setData(audioService.findByExamId(id));

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@GetMapping("/{id}/detail")
	public ResponseEntity<BaseResponse> findOne(@PathVariable("id") int id) throws Exception {
		BaseResponse response = new BaseResponse();
		int countUser = this.countUserExam(id);
		int countComments = commentsService.countComments(id);
		List<Question> listQuestions = questionService.getListByExamId(id);
		response.setData(new ExamResponse(examService.findOne(id), countUser, 0, countComments, listQuestions));

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
