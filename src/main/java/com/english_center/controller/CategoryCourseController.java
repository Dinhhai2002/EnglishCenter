package com.english_center.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.english_center.common.enums.StatusEnum;
import com.english_center.common.utils.Pagination;
import com.english_center.common.utils.StringErrorValue;
import com.english_center.entity.CategoryCourse;
import com.english_center.entity.Course;
import com.english_center.model.StoreProcedureListResult;
import com.english_center.request.CRUDCategoryCourseRequest;
import com.english_center.response.BaseListDataResponse;
import com.english_center.response.BaseResponse;
import com.english_center.response.CategoryCourseResponse;
import com.english_center.response.CourseResponse;

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
		categoryCourse.setDescription(wrapper.getDescription());

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

	@PostMapping("/{id}/change-status")
	@PreAuthorize("hasAnyAuthority('ADMIN')")
	public ResponseEntity<BaseResponse<List<CourseResponse>>> changeStatus(@PathVariable("id") int id)
			throws Exception {
		BaseResponse<List<CourseResponse>> response = new BaseResponse<>();
		CategoryCourse categoryCourse = categoryCourseService.findOne(id);

		if (categoryCourse == null) {
			response.setStatus(HttpStatus.BAD_REQUEST);
			response.setMessageError(StringErrorValue.CATEGORY_COURSE_NOT_FOUND);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
		List<Course> listCourse = courseService.spGCourse(id, "", 1, 0, new Pagination(0, 20)).getResult();

		if (!listCourse.isEmpty() && categoryCourse.getStatus() == StatusEnum.ACTIVE.getValue()) {
			response.setData(new CourseResponse().mapToList(listCourse));
			response.setStatus(HttpStatus.BAD_REQUEST);
			response.setMessageError(StringErrorValue.CATEGORY_COURSE_IS_COURSE_ACTIVE);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

		categoryCourse
				.setStatus(categoryCourse.getStatus() == StatusEnum.ACTIVE.getValue() ? StatusEnum.NOT_ACTIVE.getValue()
						: StatusEnum.ACTIVE.getValue());

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
	public ResponseEntity<BaseResponse<BaseListDataResponse<CategoryCourseResponse>>> findAll(
			@RequestParam(name = "key_search", required = false, defaultValue = "") String keySearch,
			@RequestParam(name = "status", required = false, defaultValue = "-1") int status,
			@RequestParam(name = "page", required = false, defaultValue = "1") int page,
			@RequestParam(name = "limit", required = false, defaultValue = "10") int limit) throws Exception {
		BaseResponse<BaseListDataResponse<CategoryCourseResponse>> response = new BaseResponse<>();
		Pagination pagination = new Pagination(page, limit);
		StoreProcedureListResult<CategoryCourse> categoryCourses = categoryCourseService
				.spGListCategoryCourse(keySearch, status, pagination);

		BaseListDataResponse<CategoryCourseResponse> listData = new BaseListDataResponse<>();

		listData.setList(new CategoryCourseResponse().mapToList(categoryCourses.getResult()));
		listData.setTotalRecord(categoryCourses.getTotalRecord());

		response.setData(listData);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
