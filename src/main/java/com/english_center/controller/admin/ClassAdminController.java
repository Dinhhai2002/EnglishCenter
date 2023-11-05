package com.english_center.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.english_center.common.utils.Pagination;
import com.english_center.common.utils.StringErrorValue;
import com.english_center.entity.Class;
import com.english_center.entity.ClassWeekday;
import com.english_center.model.StoreProcedureListResult;
import com.english_center.response.BaseListDataResponse;
import com.english_center.response.BaseResponse;
import com.english_center.response.ClassResponse;
import com.english_center.service.ClassService;
import com.english_center.service.ClassWeekdayService;

@RestController
@RequestMapping("/api/v1/admin/class")
public class ClassAdminController {

	@Autowired
	ClassService classService;

	@Autowired
	ClassWeekdayService classWeekdayService;

	@GetMapping("")
	@PreAuthorize("hasAnyAuthority('ADMIN')")
	public ResponseEntity<BaseResponse<BaseListDataResponse<ClassResponse>>> getAll(
			@RequestParam(name = "course_id", required = false, defaultValue = "-1") int courseId,
			@RequestParam(name = "teacher_id", required = false, defaultValue = "-1") int teacherId,
			@RequestParam(name = "key_search", required = false, defaultValue = "") String keySearch,
			@RequestParam(name = "status", required = false, defaultValue = "-1") int status,
			@RequestParam(name = "page", required = false, defaultValue = "1") int page,
			@RequestParam(name = "limit", required = false, defaultValue = "10") int limit) throws Exception {
		BaseResponse<BaseListDataResponse<ClassResponse>> response = new BaseResponse<>();
		Pagination pagination = new Pagination(page, limit);
		StoreProcedureListResult<Class> listClass = classService.spGClass(courseId, teacherId, keySearch, status,
				pagination);

		BaseListDataResponse<ClassResponse> listData = new BaseListDataResponse<>();

		listData.setList(new ClassResponse().mapToList(listClass.getResult()));
		listData.setTotalRecord(listClass.getTotalRecord());

		response.setData(listData);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping("/{id}/change-status")
	@PreAuthorize("hasAnyAuthority('ADMIN')")
	public ResponseEntity<BaseResponse<ClassResponse>> changeStatus(@PathVariable("id") int id) throws Exception {
		BaseResponse<ClassResponse> response = new BaseResponse<>();
		Class checkClass = classService.findOne(id);

		if (checkClass == null) {
			response.setStatus(HttpStatus.BAD_REQUEST);
			response.setMessageError(StringErrorValue.CLASS_NOT_FOUND);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

		if (checkClass.getStatus() == 0) {
			if (checkClass.getTeacherId() == 0) {
				response.setStatus(HttpStatus.BAD_REQUEST);
				response.setMessageError(StringErrorValue.CHANGE_STATUS_CLASS_IS_NOT_TEACHER);
				return new ResponseEntity<>(response, HttpStatus.OK);
			}

			List<ClassWeekday> listClassWeekdays = classWeekdayService.getAllByClassId(checkClass.getId());
			if (listClassWeekdays.isEmpty()) {
				response.setStatus(HttpStatus.BAD_REQUEST);
				response.setMessageError(StringErrorValue.CLASS_IS_NOT_CLASS_WEEKDAY);
				return new ResponseEntity<>(response, HttpStatus.OK);
			}
		}

		checkClass.setStatus(checkClass.getStatus() == 1 ? 0 : 1);

		classService.update(checkClass);
		response.setData(new ClassResponse(checkClass));
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
