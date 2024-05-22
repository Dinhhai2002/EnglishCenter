package com.english_center.controller.admin;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.multipart.MultipartFile;

import com.english_center.common.enums.StatusEnum;
import com.english_center.common.enums.UserCourseUsingStatusEnum;
import com.english_center.common.utils.Pagination;
import com.english_center.common.utils.StringErrorValue;
import com.english_center.controller.BaseController;
import com.english_center.entity.Course;
import com.english_center.entity.Image;
import com.english_center.entity.UserCourse;
import com.english_center.entity.Users;
import com.english_center.model.StoreProcedureListResult;
import com.english_center.request.CRUDCourseRequest;
import com.english_center.response.BaseListDataResponse;
import com.english_center.response.BaseResponse;
import com.english_center.response.ClassResponse;
import com.english_center.response.CourseResponse;
import com.english_center.response.ImageResponse;
import com.english_center.service.ClassService;

@RestController
@RequestMapping("/api/v1/admin/course")
public class CourseAdminController extends BaseController {

	@Autowired
	ClassService classService;

	@GetMapping("")
//	@PreAuthorize("hasAnyAuthority('ADMIN')")
	public ResponseEntity<BaseResponse<BaseListDataResponse<CourseResponse>>> getAll(
			@RequestParam(name = "category_course_id", required = false, defaultValue = "-1") int categoryCourseId,
			@RequestParam(name = "key_search", required = false, defaultValue = "") String keySearch,
			@RequestParam(name = "status", required = false, defaultValue = "-1") int status,
			@RequestParam(name = "page", required = false, defaultValue = "1") int page,
			@RequestParam(name = "limit", required = false, defaultValue = "10") int limit) throws Exception {
		BaseResponse<BaseListDataResponse<CourseResponse>> response = new BaseResponse<>();
		Pagination pagination = new Pagination(page, limit);
		StoreProcedureListResult<Course> listCourse = courseService.spGCourse(categoryCourseId, keySearch, status, 1,
				pagination);

		BaseListDataResponse<CourseResponse> listData = new BaseListDataResponse<>();
		Users currentUser = this.getUser();

		List<UserCourse> listUserCourses = userCourseService
				.spGUserCourse(-1, 1, currentUser.getId(), -1, 0, new Pagination(0, 1000), 0).getResult();

		/**
		 * typeUserUsing 0 : chưa đăng kí 1 : đã đăng kí 2 : đã hết hạn
		 */
		int[] typeUserUsing = { 0 };

//		xử lí trả về danh sách khóa học kèm theo user đó có đăng kí hay chưa
		List<CourseResponse> listCourseResponses = listCourse.getResult().stream().map(x -> {

			List<UserCourse> listCourses = listUserCourses.stream()
					.filter(userCourseItem -> userCourseItem.getCourseId() == x.getId()).collect(Collectors.toList());

			typeUserUsing[0] = determineUserCourseStatus(listCourses).getValue();

			return new CourseResponse(x, typeUserUsing[0], 0,
					this.getLessonsPresentCourse(x.getId(), currentUser.getId()));
		}).collect(Collectors.toList());

		listData.setList(listCourseResponses);
		listData.setTotalRecord(listCourse.getTotalRecord());
		listData.setLimit(limit);

		response.setData(listData);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping("/{id}/change-status")
	@PreAuthorize("hasAnyAuthority('ADMIN')")
	public ResponseEntity<BaseResponse<List<ClassResponse>>> changeStatus(@PathVariable("id") int id) throws Exception {
		BaseResponse<List<ClassResponse>> response = new BaseResponse<>();
		Course course = courseService.findOne(id);

		if (course == null) {
			response.setStatus(HttpStatus.BAD_REQUEST);
			response.setMessageError(StringErrorValue.COURSE_NOT_FOUND);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

		if (course.getStatus() == StatusEnum.ACTIVE.getValue()) {
			List<UserCourse> userCourses = userCourseService.spGUserCourse(id, -1, -1, 0, 0, new Pagination(0, 20), 0)
					.getResult();

			if (!userCourses.isEmpty()) {
				response.setStatus(HttpStatus.BAD_REQUEST);
				response.setMessageError(StringErrorValue.COURSE_IS_USER_REGISTED);
				return new ResponseEntity<>(response, HttpStatus.OK);
			}
		}

		course.setStatus(course.getStatus() == StatusEnum.ACTIVE.getValue() ? StatusEnum.NOT_ACTIVE.getValue()
				: StatusEnum.ACTIVE.getValue());

		courseService.update(course);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PreAuthorize("hasAnyAuthority('ADMIN')")
	@PostMapping("/create")
	public ResponseEntity<BaseResponse<CourseResponse>> create(@Valid @RequestBody CRUDCourseRequest wrapper)
			throws Exception {

		BaseResponse<CourseResponse> response = new BaseResponse();

		response.setData(new CourseResponse(courseService.spUCreateCourse(wrapper.getName(), wrapper.getDescription(),
				wrapper.getPrice(), wrapper.getIsFree())));

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PreAuthorize("hasAnyAuthority('ADMIN')")
	@PostMapping("/{id}/update")
	public ResponseEntity<BaseResponse<CourseResponse>> update(@PathVariable("id") int id,
			@Valid @RequestBody CRUDCourseRequest wrapper) throws Exception {

		BaseResponse<CourseResponse> response = new BaseResponse();

		Course course = courseService.findOne(id);

		if (course == null) {
			response.setStatus(HttpStatus.BAD_REQUEST);
			response.setMessageError(StringErrorValue.COURSE_NOT_FOUND);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
		// check name đã tồn tại hay chưa
		if (!course.getName().equals(wrapper.getName()) && courseService.findByName(wrapper.getName()) != null) {
			response.setStatus(HttpStatus.BAD_REQUEST);
			response.setMessageError(StringErrorValue.NAME_COURSE_IS_EXIST);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

		course.setName(wrapper.getName());
		course.setDescription(wrapper.getDescription());
		course.setIsFree(wrapper.getIsFree());
		course.setPrice(wrapper.getPrice());
		course.setDiscountPercent(wrapper.getDiscountPercent());

		if (wrapper.getIsFree() == 1) {
			course.setDiscountPercent(0);
		}

		courseService.update(course);

		response.setData(new CourseResponse(course));

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@PreAuthorize("hasAnyAuthority('ADMIN')")
	@PostMapping("/{id}/upload-banner")
	public ResponseEntity<BaseResponse> uploadBanner(@RequestParam(name = "file") MultipartFile file,
			@PathVariable("id") int id) throws Exception {
		BaseResponse response = new BaseResponse();
		Course checkCourse = courseService.findOne(id);

		if (checkCourse == null) {
			response.setStatus(HttpStatus.BAD_REQUEST);
			response.setMessageError(StringErrorValue.COURSE_NOT_FOUND);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

		String fileName = iFirebaseImageService.save(file);

		String imageUrl = iFirebaseImageService.getImageUrl(fileName);

		Image image = new Image();
		image.setUrl(imageUrl);
		image.setCourseId(id);

		imageService.create(image);

		checkCourse.setBanner(imageUrl);
		courseService.update(checkCourse);

		response.setData(new ImageResponse(image));

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	private UserCourseUsingStatusEnum determineUserCourseStatus(List<UserCourse> userCourses) {
		if (userCourses.isEmpty()) {
			return UserCourseUsingStatusEnum.NO_REGISTER;
		}

		if (userCourses.stream().anyMatch(userCourse -> userCourse.getIsExpired() == 0)) {
			return UserCourseUsingStatusEnum.REGISTERED;
		}

		return UserCourseUsingStatusEnum.EXPIRED;
	}

}
