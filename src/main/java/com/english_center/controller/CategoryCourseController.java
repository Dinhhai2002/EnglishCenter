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
import org.springframework.web.bind.annotation.RestController;

import com.english_center.common.utils.StringErrorValue;
import com.english_center.entity.CategoryCourse;
import com.english_center.request.CRUDCategoryCourseRequest;
import com.english_center.response.BaseResponse;
import com.english_center.response.CategoryCourseResponse;

@RestController
@RequestMapping("/api/v1/category-course")
public class CategoryCourseController extends BaseController {

	@SuppressWarnings("rawtypes")
	@PostMapping("/create")
	public ResponseEntity<BaseResponse> create(@Valid @RequestBody CRUDCategoryCourseRequest wrapper) throws Exception {

		BaseResponse response = new BaseResponse();
		CategoryCourse categoryCourseCheck = categoryCourseService.findByName(wrapper.getName());

		if (categoryCourseCheck != null) {
			response.setStatus(HttpStatus.BAD_REQUEST);
			response.setMessageError(StringErrorValue.CATEGORY_COURSE_NOT_FOUND);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

		CategoryCourse categoryCourse = new CategoryCourse();
		categoryCourse.setName(wrapper.getName());

		categoryCourseService.create(categoryCourse);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@SuppressWarnings("rawtypes")
	@PostMapping("/{id}/update")
	public ResponseEntity<BaseResponse> update(@Valid @RequestBody CRUDCategoryCourseRequest wrapper,
			@PathVariable("id") int id) throws Exception {

		BaseResponse response = new BaseResponse();

		CategoryCourse categoryCourse = categoryCourseService.findOne(id);

		if (categoryCourse == null) {
			response.setStatus(HttpStatus.BAD_REQUEST);
			response.setMessageError(StringErrorValue.CATEGORY_COURSE_NOT_FOUND);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

		CategoryCourse categoryCourseCheck = categoryCourseService.findByName(wrapper.getName());

		if (categoryCourseCheck != null) {
			response.setStatus(HttpStatus.BAD_REQUEST);
			response.setMessageError(StringErrorValue.CATEGORY_COURSE_IS_EXIST);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

		categoryCourse.setName(wrapper.getName());

		categoryCourseService.update(categoryCourse);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<BaseResponse<CategoryCourseResponse>> findOne(@PathVariable("id") int id) throws Exception {

		BaseResponse<CategoryCourseResponse> response = new BaseResponse<>();

		CategoryCourse categoryCourse = categoryCourseService.findOne(id);

		if (categoryCourse == null) {
			response.setStatus(HttpStatus.BAD_REQUEST);
			response.setMessageError(StringErrorValue.CATEGORY_COURSE_NOT_FOUND);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
		response.setData(new CategoryCourseResponse(categoryCourse));

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("")
	public ResponseEntity<BaseResponse<List<CategoryCourseResponse>>> findAll() throws Exception {

		BaseResponse<List<CategoryCourseResponse>> response = new BaseResponse<>();

		List<CategoryCourse> categoryCourses = categoryCourseService.findAll();

		response.setData(new CategoryCourseResponse().mapToList(categoryCourses));

		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
