package com.english_center.controller.admin;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.english_center.controller.BaseController;
import com.english_center.entity.CategoryCourse;
import com.english_center.response.BaseResponse;
import com.english_center.response.CategoryCourseResponse;

@RestController
@RequestMapping("/api/v1/admin/category-course")
public class CategoryCourseAdminController extends BaseController {

	@GetMapping("")
	@PreAuthorize("hasAnyAuthority('ADMIN')")
	public ResponseEntity<BaseResponse<List<CategoryCourseResponse>>> getAll(@RequestParam("page") int page,
			@RequestParam("limit") int limit) throws Exception {
		BaseResponse<List<CategoryCourseResponse>> response = new BaseResponse<>();

		List<CategoryCourse> lisCategoryCourse = categoryCourseService.findAllHavePagination(page, limit);

		response.setData(new CategoryCourseResponse().mapToList(lisCategoryCourse));

		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
