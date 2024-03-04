package com.english_center.controller;

import java.util.List;

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

@RestController
@RequestMapping("/api/v1/category-exam")
public class CategoryExamController extends BaseController {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@GetMapping("")
	public ResponseEntity<BaseResponse<List<CategoryExamResponse>>> getAll() throws Exception {

		BaseResponse<List<CategoryExamResponse>> response = new BaseResponse();

		List<CategoryExam> categoryExams = categoryExamService.getAll();

		response.setData(new CategoryExamResponse().mapToList(categoryExams));

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<BaseResponse<CategoryExamResponse>> findOne(@PathVariable("id") int id) throws Exception {

		BaseResponse<CategoryExamResponse> response = new BaseResponse<>();

		CategoryExam categoryExam = categoryExamService.findOne(id);

		if (categoryExam == null) {
			response.setStatus(HttpStatus.BAD_REQUEST);
			response.setMessageError(StringErrorValue.CATEGORY_EXAM_IS_EXIST);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

		response.setData(new CategoryExamResponse(categoryExam));

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/{id}/get-topic-exam-by-category")
	public ResponseEntity<BaseResponse<List<TopicExamReponse>>> getAllByCategory(@PathVariable("id") int id)
			throws Exception {

		BaseResponse<List<TopicExamReponse>> response = new BaseResponse<>();

		response.setData(new TopicExamReponse().mapToList(topicExamService.findByCategoryExamId(id)));

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
