package com.english_center.controller;

import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RestController;

import com.english_center.common.utils.StringErrorValue;
import com.english_center.entity.CategoryExam;
import com.english_center.entity.Exam;
import com.english_center.entity.Question;
import com.english_center.entity.TopicExam;
import com.english_center.request.CRUDCategoryExamRequest;
import com.english_center.response.BaseResponse;
import com.english_center.response.CategoryExamResponse;
import com.english_center.response.ExamResponse;
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

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@GetMapping("/{id}/get-exams-by-category")
	public ResponseEntity<BaseResponse<List<ExamResponse>>> getAllByCategory(@PathVariable("id") int id)
			throws Exception {

		BaseResponse<List<ExamResponse>> response = new BaseResponse();
		List<Exam> exams = examService.findByCategory(id);
		List<ExamResponse> listExamResponses = exams.stream().map(x -> {
			// trả về tổng user của đề thi
			int totaluser = 0;
			try {
				totaluser = this.countUserExam(x.getId());
			} catch (Exception e) {
				e.printStackTrace();
			}

			// kiểm tra đề thi đó đã có câu hỏi chưa
			int isQuestion = 0;
			List<Question> listQuestion = new ArrayList<>();
			try {
				listQuestion = questionService.getListByExamId(x.getId());
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (!listQuestion.isEmpty()) {
				isQuestion = 1;
			}

			// Tổng số lượng comments của đề thi
			int countComments = 0;
			try {
				countComments = this.countComment(x.getId());
			} catch (Exception e) {
				e.printStackTrace();
			}
			return new ExamResponse(x, totaluser, isQuestion, countComments);
		}).collect(Collectors.toList());

		response.setData(listExamResponses);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@GetMapping("/{id}/get-topic-exam-by-category")
	public ResponseEntity<BaseResponse> getAllTopicExamByCategory(@PathVariable("id") int id) throws Exception {

		BaseResponse response = new BaseResponse();
		List<TopicExam> topicExams = topicExamService.findByCategoryExamId(id);

		List<Exam> exams = examService.findByCategory(id);

		List<TopicExamReponse> topicResponses = topicExams.stream().map(topicExam -> {
			List<ExamResponse> filteredExams = exams.stream().filter(exam -> exam.getTopicId() == topicExam.getId())
					.map(x -> {

						// trả về tổng user của đề thi
						int totaluser = 0;
						try {
							totaluser = this.countUserExam(x.getId());
						} catch (Exception e) {
							e.printStackTrace();
						}

						// kiểm tra đề thi đó đã có câu hỏi chưa
						int isQuestion = 0;
						List<Question> listQuestion = new ArrayList<>();
						try {
							listQuestion = questionService.getListByExamId(x.getId());
						} catch (Exception e) {
							e.printStackTrace();
						}
						if (!listQuestion.isEmpty()) {
							isQuestion = 1;
						}

						// Tổng số lượng comments của đề thi
						int countComments = 0;
						try {
							countComments = this.countComment(x.getId());
						} catch (Exception e) {
							e.printStackTrace();
						}
						return new ExamResponse(x, totaluser, isQuestion, countComments);
					}).collect(Collectors.toList());

			return new TopicExamReponse(topicExam, filteredExams);
		}).collect(Collectors.toList());
		response.setData(topicResponses);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
