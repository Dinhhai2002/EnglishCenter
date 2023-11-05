package com.english_center.controller;

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

import com.english_center.entity.ClassStudent;
import com.english_center.entity.Users;
import com.english_center.request.CRUDClassStudentRequest;
import com.english_center.response.BaseResponse;
import com.english_center.response.ClassStudentResponse;
import com.english_center.service.ClassService;
import com.english_center.service.ClassStudentService;
import com.english_center.service.UserService;
import com.english_center.common.utils.StringErrorValue;
import com.english_center.entity.Class;

@RestController
@RequestMapping("/api/v1/class-student")
public class ClassStudentController extends BaseController {
	@Autowired
	ClassStudentService classStudentService;

	@Autowired
	ClassService classService;

	@Autowired
	UserService userService;

	@SuppressWarnings("rawtypes")
	@PostMapping("/create")
	public ResponseEntity<BaseResponse> create(@Valid @RequestBody CRUDClassStudentRequest wrapper) throws Exception {

		BaseResponse response = new BaseResponse();

		Class checkClass = classService.findOne(wrapper.getClassId());

		if (checkClass == null) {
			response.setStatus(HttpStatus.BAD_REQUEST);
			response.setMessageError(StringErrorValue.CLASS_NOT_FOUND);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

		Users user = userService.findOne(wrapper.getStudentId());

		if (user == null) {
			response.setStatus(HttpStatus.BAD_REQUEST);
			response.setMessageError(StringErrorValue.USER_NOT_FOUND);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

		ClassStudent checkClassStudent = classStudentService.findByClassIdAndStudent(wrapper.getClassId(),
				wrapper.getStudentId());

		if (checkClassStudent != null) {
			response.setStatus(HttpStatus.BAD_REQUEST);
			response.setMessageError(StringErrorValue.STUDENT_IS_EXIST_IN_CLASS);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

		ClassStudent classStudent = new ClassStudent();
		classStudent.setClassId(wrapper.getClassId());
		classStudent.setStudentId(wrapper.getStudentId());

		classStudentService.create(classStudent);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@GetMapping("/{id}")
	public ResponseEntity<BaseResponse<ClassStudentResponse>> findOne(@PathVariable("id") int id) throws Exception {

		BaseResponse<ClassStudentResponse> response = new BaseResponse();

		ClassStudent classStudent = classStudentService.findOne(id);

		if (classStudent == null) {
			response.setStatus(HttpStatus.BAD_REQUEST);
			response.setMessageError(StringErrorValue.CLASS_STUDENT_NOT_FOUND);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
		response.setData(new ClassStudentResponse(classStudent));

		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
