package com.english_center.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.english_center.common.utils.StringErrorValue;
import com.english_center.entity.CategoryExam;
import com.english_center.response.BaseResponse;
import com.english_center.response.CategoryExamResponse;
import com.english_center.response.TopicExamReponse;
import com.english_center.service.CategoryExamService;
import com.english_center.service.ExamService;
import com.english_center.service.QuestionService;
import com.english_center.service.TopicExamService;

@RestController
@RequestMapping("/api/v1/category-exam")
public class CategoryExamController extends BaseController {

	@Autowired
	CategoryExamService categoryExamService;

	@Autowired
	TopicExamService topicExamService;

	@Autowired
	ExamService examService;

	@Autowired
	QuestionService questionService;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@GetMapping("")
	public ResponseEntity<BaseResponse<List<CategoryExamResponse>>> getAll() throws Exception {

		BaseResponse<List<CategoryExamResponse>> response = new BaseResponse();

		List<CategoryExam> categoryExams = categoryExamService.getAll();

		response.setData(new CategoryExamResponse().mapToList(categoryExams));

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@GetMapping("/{id}")
	public ResponseEntity<BaseResponse<CategoryExamResponse>> findOne(@PathVariable("id") int id) throws Exception {

		BaseResponse<CategoryExamResponse> response = new BaseResponse();

		CategoryExam categoryExam = categoryExamService.findOne(id);

		if (categoryExam == null) {
			response.setStatus(HttpStatus.BAD_REQUEST);
			response.setMessageError(StringErrorValue.CATEGORY_EXAM_IS_EXIST);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

		response.setData(new CategoryExamResponse(categoryExam));

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

//	@SuppressWarnings({ "rawtypes", "unchecked" })
//	@GetMapping("/{id}/get-exams-by-category")
//	public ResponseEntity<BaseResponse<List<ExamResponse>>> getAllByCategory(@PathVariable("id") int id)
//			throws Exception {
//
//		BaseResponse<List<ExamResponse>> response = new BaseResponse();
//		List<Exam> exams = examService.findByCategory(id);
//		List<ExamResponse> listExamResponses = exams.stream().map(x -> {
//			// trả về tổng user của đề thi
//			int totaluser = 0;
//			totaluser = this.countUserExam(x.getId());
//
//			// kiểm tra đề thi đó đã có câu hỏi chưa
//			int isQuestion = 0;
//			List<Question> listQuestion = getListWithExceptionHandler(() -> questionService.getListByExamId(x.getId()));
//
//			if (!listQuestion.isEmpty()) {
//				isQuestion = 1;
//			}
//
//			return new ExamResponse(x, totaluser, isQuestion, this.countComment(x.getId()));
//		}).collect(Collectors.toList());
//
//		response.setData(listExamResponses);
//		return new ResponseEntity<>(response, HttpStatus.OK);
//	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@GetMapping("/{id}/get-topic-exam-by-category")
	public ResponseEntity<BaseResponse<List<TopicExamReponse>>> getAllByCategory(@PathVariable("id") int id)
			throws Exception {

		BaseResponse<List<TopicExamReponse>> response = new BaseResponse();
		List<TopicExamReponse> listExamReponses = new TopicExamReponse()
				.mapToList(topicExamService.findByCategoryExamId(id));

		response.setData(listExamReponses);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
