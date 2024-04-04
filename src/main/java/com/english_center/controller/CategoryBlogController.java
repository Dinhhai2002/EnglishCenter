package com.english_center.controller;

import java.util.List;

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

import com.english_center.common.utils.StringErrorValue;
import com.english_center.entity.CategoryBlog;
import com.english_center.request.CRUDCategoryBlogRequest;
import com.english_center.response.BaseResponse;
import com.english_center.response.CategoryBlogResponse;
import com.english_center.service.CategoryBlogService;

@RestController
@RequestMapping("/api/v1/category-blog")
public class CategoryBlogController extends BaseController {

	@Autowired
	CategoryBlogService categoryBlogService;

	@GetMapping("/{id}")
	public ResponseEntity<BaseResponse<CategoryBlogResponse>> findOne(@PathVariable("id") int id) throws Exception {

		BaseResponse<CategoryBlogResponse> response = new BaseResponse<>();

		CategoryBlog categoryBlog = categoryBlogService.findOne(id);

		if (categoryBlog == null) {
			response.setStatus(HttpStatus.BAD_REQUEST);
			response.setMessageError(StringErrorValue.CATEGORY_BLOG_NOT_FOUND);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
		response.setData(new CategoryBlogResponse(categoryBlog));

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("")
	public ResponseEntity<BaseResponse<List<CategoryBlogResponse>>> findAll(
			@RequestParam(name = "status", required = false, defaultValue = "-1") int status) throws Exception {

		BaseResponse<List<CategoryBlogResponse>> response = new BaseResponse<>();

		List<CategoryBlog> categoryBlogs = categoryBlogService.getAll(status);

		response.setData(new CategoryBlogResponse().mapToList(categoryBlogs));

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping("/create")
	public ResponseEntity<BaseResponse<CategoryBlogResponse>> create(
			@Valid @RequestBody CRUDCategoryBlogRequest wrapper) throws Exception {

		BaseResponse<CategoryBlogResponse> response = new BaseResponse<>();

		CategoryBlog checkCategoryBlog = categoryBlogService.findByName(wrapper.getName());
		if (checkCategoryBlog != null) {
			response.setStatus(HttpStatus.BAD_REQUEST);
			response.setMessageError(StringErrorValue.CATEGORY_BLOG_IS_EXIST);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

		CategoryBlog categoryBlog = new CategoryBlog();
		categoryBlog.setName(wrapper.getName());
		categoryBlog.setStatus(1);

		categoryBlogService.create(categoryBlog);
		response.setData(new CategoryBlogResponse(categoryBlog));
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
