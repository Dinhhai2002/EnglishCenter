package com.english_center.controller.admin;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.english_center.controller.BaseController;

@RestController
@RequestMapping("/api/v1/admin/category-course")
public class CategoryCourseAdminController extends BaseController {

//	@GetMapping("")
//	@PreAuthorize("hasAnyAuthority('ADMIN')")
//	public ResponseEntity<BaseResponse<List<CategoryCourseResponse>>> getAll(@RequestParam("page") int page,
//			@RequestParam("limit") int limit) throws Exception {
//		BaseResponse<List<CategoryCourseResponse>> response = new BaseResponse<>();
//
//		List<CategoryCourse> lisCategoryCourse = categoryCourseService.findAllHavePagination(page, limit);
//
//		response.setData(new CategoryCourseResponse().mapToList(lisCategoryCourse));
//
//		return new ResponseEntity<>(response, HttpStatus.OK);
//	}
}
