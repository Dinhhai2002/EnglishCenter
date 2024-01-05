package com.english_center.controller.admin;

import java.util.List;
import java.util.stream.Collectors;

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

import com.english_center.common.utils.Pagination;
import com.english_center.common.utils.StringErrorValue;
import com.english_center.controller.BaseController;
import com.english_center.entity.CategoryExam;
import com.english_center.entity.Exam;
import com.english_center.model.StoreProcedureListResult;
import com.english_center.request.CRUDCategoryExamRequest;
import com.english_center.response.BaseListDataResponse;
import com.english_center.response.BaseResponse;
import com.english_center.response.CategoryExamResponse;
import com.english_center.response.ExamResponse;

@RestController
@RequestMapping("/api/v1/admin/category-exam")
public class CategoryExamAdminController extends BaseController {

	@GetMapping("")
//	@PreAuthorize("hasAnyAuthority('ADMIN')")
	public ResponseEntity<BaseResponse<BaseListDataResponse<CategoryExamResponse>>> getAll(
			@RequestParam(name = "key_search", required = false, defaultValue = "") String keySearch,
			@RequestParam(name = "status", required = false, defaultValue = "-1") int status,
			@RequestParam(name = "page", required = false, defaultValue = "1") int page,
			@RequestParam(name = "limit", required = false, defaultValue = "10") int limit) throws Exception {
		BaseResponse<BaseListDataResponse<CategoryExamResponse>> response = new BaseResponse<>();
		Pagination pagination = new Pagination(page, limit);
		StoreProcedureListResult<CategoryExam> listCategoryExam = categoryExamService.spGListCategoryExam(keySearch,
				status, pagination);

		BaseListDataResponse<CategoryExamResponse> listData = new BaseListDataResponse<>();

		listData.setList(new CategoryExamResponse().mapToList(listCategoryExam.getResult()));
		listData.setTotalRecord(listCategoryExam.getTotalRecord());

		response.setData(listData);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping("/{id}/change-status")
	@PreAuthorize("hasAnyAuthority('ADMIN')")
	public ResponseEntity<BaseResponse<List<ExamResponse>>> changeStatus(@PathVariable("id") int id) throws Exception {
		BaseResponse<List<ExamResponse>> response = new BaseResponse<>();
		CategoryExam categoryExam = categoryExamService.findOne(id);

		if (categoryExam == null) {
			response.setStatus(HttpStatus.BAD_REQUEST);
			response.setMessageError(StringErrorValue.CATEGORY_EXAM_NOT_FOUND);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

		List<Exam> listExams = examService.findByCategory(id).stream().filter(x -> (x.getStatus() == 1))
				.collect(Collectors.toList());

		if (!listExams.isEmpty() && categoryExam.getStatus() == 1) {
			response.setData(new ExamResponse().mapToList(listExams));
			response.setStatus(HttpStatus.BAD_REQUEST);
			response.setMessageError(StringErrorValue.CATEGORY_EXAM_IS_EXAM_ACTIVE);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

		categoryExam.setStatus(categoryExam.getStatus() == 1 ? 0 : 1);

		categoryExamService.update(categoryExam);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping("/create")
	public ResponseEntity<BaseResponse<CategoryExamResponse>> create(
			@Valid @RequestBody CRUDCategoryExamRequest wrapper) throws Exception {

		BaseResponse<CategoryExamResponse> response = new BaseResponse<>();
		CategoryExam categoryExamCheck = categoryExamService.findByName(wrapper.getName());

		if (categoryExamCheck != null) {
			response.setStatus(HttpStatus.BAD_REQUEST);
			response.setMessageError(StringErrorValue.CATEGORY_EXAM_IS_EXIST);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

		CategoryExam categoryExam = new CategoryExam();
		categoryExam.setName(wrapper.getName());
		categoryExam.setDescription(wrapper.getDescription());
		categoryExam.setStatus(1);

		categoryExamService.create(categoryExam);
		response.setData(new CategoryExamResponse(categoryExam));
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping("/{id}/update")
	public ResponseEntity<BaseResponse<CategoryExamResponse>> update(@PathVariable("id") int id,
			@Valid @RequestBody CRUDCategoryExamRequest wrapper) throws Exception {

		BaseResponse<CategoryExamResponse> response = new BaseResponse<>();
		CategoryExam categoryExam = categoryExamService.findOne(id);

		if (categoryExam == null) {
			response.setStatus(HttpStatus.BAD_REQUEST);
			response.setMessageError(StringErrorValue.CATEGORY_COURSE_NOT_FOUND);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

		// check name đã tồn tại hay chưa
		if (!categoryExam.getName().equals(wrapper.getName())
				&& categoryExamService.findByName(wrapper.getName()) != null) {
			response.setStatus(HttpStatus.BAD_REQUEST);
			response.setMessageError(StringErrorValue.CATEGORY_EXAM_IS_EXIST);
			return new ResponseEntity<>(response, HttpStatus.OK);

		}
		categoryExam.setName(wrapper.getName());
		categoryExam.setDescription(wrapper.getDescription());
		categoryExamService.update(categoryExam);

		response.setData(new CategoryExamResponse(categoryExam));
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
