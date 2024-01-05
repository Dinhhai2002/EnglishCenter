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
import org.springframework.web.bind.annotation.RestController;

import com.english_center.common.utils.StringErrorValue;
import com.english_center.entity.Class;
import com.english_center.entity.ClassStudent;
import com.english_center.entity.Course;
import com.english_center.entity.UserCourse;
import com.english_center.entity.Users;
import com.english_center.request.AddStudentJoinClassRequest;
import com.english_center.request.CRUDClassRequest;
import com.english_center.request.SetTeacherClassRequest;
import com.english_center.response.BaseResponse;
import com.english_center.response.ClassResponse;
import com.english_center.service.ClassService;
import com.english_center.service.CourseService;
import com.english_center.service.UserService;

@RestController
@RequestMapping("/api/v1/class")
public class ClassController extends BaseController {
	@Autowired
	ClassService classService;

	@Autowired
	UserService userService;

	@Autowired
	CourseService courseService;

	@SuppressWarnings("rawtypes")
	@PostMapping("/create")
	public ResponseEntity<BaseResponse> create(@Valid @RequestBody CRUDClassRequest wrapper) throws Exception {

		BaseResponse response = new BaseResponse();

		Course checkCourse = courseService.findOne(wrapper.getCourseId());

		if (checkCourse == null) {
			response.setStatus(HttpStatus.BAD_REQUEST);
			response.setMessageError(StringErrorValue.CATEGORY_COURSE_NOT_FOUND);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

//		Users checkUser=userService.findOne(wrapper.getTeacherId());
//		
//		if (checkUser == null) {
//			response.setStatus(HttpStatus.BAD_REQUEST);
//			response.setMessageError("người dùng không tồn tại!");
//			return new ResponseEntity<>(response, HttpStatus.OK);
//		}
//		
//		if(checkUser!=null && checkUser.getRole()!=2)
//		{
//			response.setStatus(HttpStatus.BAD_REQUEST);
//			response.setMessageError("người dùng không phải là giáo viên!");
//			return new ResponseEntity<>(response, HttpStatus.OK);
//		}

		Class newClass = new Class();
		newClass.setName(wrapper.getName());
		newClass.setStartDate(this.formatDate(wrapper.getStartDate()));
		newClass.setEndDate(this.formatDate(wrapper.getEndDate()));
		newClass.setCourseId(wrapper.getCourseId());
		newClass.setCourseName(checkCourse.getName());
		newClass.setTotalStudent(0);
//		newClass.setTeacherId(wrapper.getTeacherId());

		classService.create(newClass);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@GetMapping("/{id}")
	public ResponseEntity<BaseResponse<ClassResponse>> findOne(@PathVariable("id") int id) throws Exception {

		BaseResponse<ClassResponse> response = new BaseResponse();

		Class checkClass = classService.findOne(id);

		if (checkClass == null) {
			response.setStatus(HttpStatus.BAD_REQUEST);
			response.setMessageError(StringErrorValue.CLASS_NOT_FOUND);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
		response.setData(new ClassResponse(checkClass));

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@GetMapping("")
	public ResponseEntity<BaseResponse<List<ClassResponse>>> getAll() throws Exception {

		BaseResponse<List<ClassResponse>> response = new BaseResponse();

		List<Class> listClass = classService.getAll();

		response.setData(new ClassResponse().mapToList(listClass));

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@PostMapping("/{id}/set-teacher")
	public ResponseEntity<BaseResponse> setTeacherClass(@PathVariable("id") int id,
			@Valid @RequestBody SetTeacherClassRequest wrapper) throws Exception {

		BaseResponse response = new BaseResponse();

		Class checkClass = classService.findOne(id);

		if (checkClass == null) {
			response.setStatus(HttpStatus.BAD_REQUEST);
			response.setMessageError(StringErrorValue.CLASS_NOT_FOUND);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
		Users checkUser = userService.findOne(wrapper.getTeacherId());

		if (checkUser == null) {
			response.setStatus(HttpStatus.BAD_REQUEST);
			response.setMessageError(StringErrorValue.USER_NOT_FOUND);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

		if (checkUser.getRole() != 2) {
			response.setStatus(HttpStatus.BAD_REQUEST);
			response.setMessageError(StringErrorValue.USER_IS_NOT_TEACHER);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

		checkClass.setTeacherId(wrapper.getTeacherId());
		checkClass.setTeacherName(checkUser.getFullName());

		classService.update(checkClass);

		response.setData(new ClassResponse(checkClass));

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@SuppressWarnings({ "rawtypes" })
	@PostMapping("/{id}/add-student")
	public ResponseEntity<BaseResponse> addStudentClass(@PathVariable("id") int id,
			@Valid @RequestBody AddStudentJoinClassRequest wrapper) throws Exception {

		BaseResponse response = new BaseResponse();

		Class checkClass = classService.findOne(id);

		if (checkClass == null) {
			response.setStatus(HttpStatus.BAD_REQUEST);
			response.setMessageError(StringErrorValue.CLASS_NOT_FOUND);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
		if (checkClass.getTotalStudent() == 100) {
			response.setStatus(HttpStatus.BAD_REQUEST);
			response.setMessageError(StringErrorValue.LIST_STUDENT_CLASS_IS_LIMITED);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

		if (checkClass.getTotalStudent() + wrapper.getUserCourseId().size() > 100) {
			response.setStatus(HttpStatus.BAD_REQUEST);
			response.setMessageError(String.format(
					"Danh sách thêm sinh viên vượt quá giới hạn số lượng của lớp học.Số lượng hiện tại là %s",
					checkClass.getTotalStudent()));
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

		wrapper.getUserCourseId().forEach(x -> {
			UserCourse userCourse = new UserCourse();
			try {
				userCourse = this.userCourseService.findOne(x);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (userCourse == null) {
				response.setStatus(HttpStatus.BAD_REQUEST);
				response.setMessageError(StringErrorValue.USER_COURSE_NOT_FOUND);
				return;
			}

			/*
			 * Tạo lớp học cho sinh viên update lại tổng số lượng sinh viên trong lớp học
			 * update trường isJoinClass trong bảng userCourse để xác nhận sinh viên đã join
			 * vào lớp
			 */
			ClassStudent classStudent = new ClassStudent();
			classStudent.setStudentId(userCourse.getStudentId());
			classStudent.setClassId(id);

			try {
				this.classStudentService.create(classStudent);
			} catch (Exception e) {
				e.printStackTrace();
			}

			checkClass.setTotalStudent(checkClass.getTotalStudent() + 1);
			try {
				classService.update(checkClass);
			} catch (Exception e) {
				e.printStackTrace();
			}

			userCourse.setIsJoinClass(1);
			try {
				this.userCourseService.update(userCourse);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});

		// handle ngoài vòng lặp
		if (response.getStatus() == 400) {
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
