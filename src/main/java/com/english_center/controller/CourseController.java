package com.english_center.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import org.springframework.web.bind.annotation.RestController;

import com.english_center.common.enums.RoleEnum;
import com.english_center.common.utils.Pagination;
import com.english_center.common.utils.StringErrorValue;
import com.english_center.entity.Chapter;
import com.english_center.entity.Class;
import com.english_center.entity.Course;
import com.english_center.entity.Lessons;
import com.english_center.entity.UserCourseProgress;
import com.english_center.entity.Users;
import com.english_center.request.CRUDCourseRequest;
import com.english_center.response.BaseResponse;
import com.english_center.response.ChapterResponse;
import com.english_center.response.ClassResponse;
import com.english_center.response.CourseResponse;
import com.english_center.response.LessonsResponse;
import com.english_center.service.ClassService;
import com.english_center.service.ClassWeekdayService;

@RestController
@RequestMapping("/api/v1/course")
public class CourseController extends BaseController {

	@Autowired
	ClassService classService;

	@Autowired
	ClassWeekdayService classWeekdayService;

	@PreAuthorize("hasAnyAuthority('ADMIN')")
	@PostMapping("/{id}/update")
	public ResponseEntity<BaseResponse<CourseResponse>> update(@Valid @RequestBody CRUDCourseRequest wrapper,
			@PathVariable("id") int id) throws Exception {

		BaseResponse<CourseResponse> response = new BaseResponse<>();
		Course checkCourse = courseService.findOne(id);
		if (checkCourse == null) {
			response.setStatus(HttpStatus.BAD_REQUEST);
			response.setMessageError(StringErrorValue.COURSE_NOT_FOUND);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

		response.setData(
				new CourseResponse(courseService.spUUpdateCourse(id, wrapper.getName(), 0, wrapper.getDescription(),
						wrapper.getPrice(), wrapper.getIsFree(), new BigDecimal(wrapper.getDiscountPercent()))));

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("")
	public ResponseEntity<BaseResponse<List<CourseResponse>>> findAll() throws Exception {

		BaseResponse<List<CourseResponse>> response = new BaseResponse<>();

		List<Course> listCourse = courseService.findAll();

		response.setData(new CourseResponse().mapToList(listCourse));

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/{id}/detail")
	public ResponseEntity<BaseResponse<CourseResponse>> findOne(@PathVariable("id") int id) throws Exception {

		BaseResponse<CourseResponse> response = new BaseResponse<>();

		Users users = this.getUser();

		Course course = courseService.findOne(id);

		if (course == null) {
			response.setStatus(HttpStatus.BAD_REQUEST);
			response.setMessageError(StringErrorValue.COURSE_NOT_FOUND);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

		if (course.getStatus() == 0 && users.getRole() != RoleEnum.ADMIN.getValue()) {
			response.setStatus(HttpStatus.BAD_REQUEST);
			response.setMessageError(StringErrorValue.COURSE_NOT_FOUND);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

		int[] countLessons = { 0 };
		List<Chapter> listChapter = chapterService.findByCourseId(id);

		List<Lessons> listLessons = lessonsService.spGListLessons(-1, -1, "", 1, new Pagination(0, 20), 0).getResult();

		List<UserCourseProgress> listUserCourseProgress = userCourseProgressService
				.spGUserCourseProgress(users.getId(), id, -1, -1, -1).getResult();

		Map<Integer, UserCourseProgress> userCourseProgressMap = new HashMap<>();
		for (UserCourseProgress userCourseProgress : listUserCourseProgress) {
			userCourseProgressMap.put(userCourseProgress.getLessonsId(), userCourseProgress);
		}

		List<ChapterResponse> list = this.handleResponseChapter(listChapter, listLessons, userCourseProgressMap,
				countLessons, course, users);

		response.setData(new CourseResponse(course, list, 0, countLessons[0]));
		return new ResponseEntity<>(response, HttpStatus.OK);

	}

	public List<ChapterResponse> handleResponseChapter(List<Chapter> listChapter, List<Lessons> listLessons,
			Map<Integer, UserCourseProgress> userCourseProgressMap, int[] countLessons, Course course, Users users) {
		return listChapter.stream().map(x -> {

			List<Lessons> listLessonsMap = listLessons.stream().filter(lesson -> lesson.getChapterId() == x.getId())
					.collect(Collectors.toList());

			List<LessonsResponse> listLessonsResponses = listLessonsMap.stream().map(lesson -> {

				UserCourseProgress userCourseProgress = userCourseProgressMap.get(lesson.getId());
				return handleLessonResponse(lesson, userCourseProgress);

			}).collect(Collectors.toList());

			// tổng số bài học
			countLessons[0] += listLessons.size();

			return new ChapterResponse(x, listLessonsResponses,
					this.countLessonsIsStudiedInChapter(course.getId(), x.getId(), users.getId()));
		}).collect(Collectors.toList());
	}

	@GetMapping("/no-banner")
	public ResponseEntity<BaseResponse<List<CourseResponse>>> findAllNoBanner() throws Exception {

		BaseResponse<List<CourseResponse>> response = new BaseResponse<>();

		List<Course> list = courseService.findAll();

		list = list.stream().filter(x -> (x.getBanner() == null || x.getBanner().equals("")))
				.collect(Collectors.toList());
		response.setData(new CourseResponse().mapToList(list));

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@GetMapping("/{id}/list-class")
	public ResponseEntity<BaseResponse<List<ClassResponse>>> getClassById(@PathVariable("id") int id) throws Exception {

		BaseResponse<List<ClassResponse>> response = new BaseResponse();
		Course checkCourse = courseService.findOne(id);

		if (checkCourse == null) {
			response.setStatus(HttpStatus.BAD_REQUEST);
			response.setMessageError(StringErrorValue.COURSE_NOT_FOUND);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

		List<Class> listClass = classService.getAllByCourseId(id);
		response.setData(new ClassResponse().mapToList(listClass));

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@GetMapping("/{id}/list-chapter")
	public ResponseEntity<BaseResponse<List<ChapterResponse>>> getChapterByCourse(@PathVariable("id") int id)
			throws Exception {

		BaseResponse<List<ChapterResponse>> response = new BaseResponse();
		Course checkCourse = courseService.findOne(id);

		if (checkCourse == null) {
			response.setStatus(HttpStatus.BAD_REQUEST);
			response.setMessageError(StringErrorValue.COURSE_NOT_FOUND);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

		List<Chapter> listChapter = chapterService.findByCourseId(id);
		List<Course> courses = courseService.findAll();

		Map<Integer, Course> courseMap = new HashMap<>();
		for (Course course : courses) {
			courseMap.put(course.getId(), course);
		}

		List<ChapterResponse> listChapterResponse = listChapter.stream().map(chapter -> {
			Course course = courseMap.get(chapter.getCourseId());

			return new ChapterResponse(chapter, course);
		}).collect(Collectors.toList());

		response.setData(listChapterResponse);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

//	@GetMapping("/{id}/list-class-add-hour")
//	public ResponseEntity<BaseResponse<List<ClassResponse>>> getClassByIdAddHour(@PathVariable("id") int id)
//			throws Exception {
//
//		BaseResponse<List<ClassResponse>> response = new BaseResponse<>();
//		Course checkCourse = courseService.findOne(id);
//
//		if (checkCourse == null) {
//			response.setStatus(HttpStatus.BAD_REQUEST);
//			response.setMessageError(StringErrorValue.COURSE_NOT_FOUND);
//			return new ResponseEntity<>(response, HttpStatus.OK);
//		}
//
//		List<Class> listClass = classService.getAllByCourseId(id);
//		List<Class> listClassCheck = new ArrayList<>();
//
//		listClass.stream().map(x -> {
//			List<ClassWeekday> listClassWeekdays = new ArrayList<>();
//			try {
//				listClassWeekdays = classWeekdayService.getAllByClassId(x.getId());
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//
//			if (listClassWeekdays.isEmpty()) {
//				listClassCheck.add(x);
//			}
//
//			return 0;
//		}).collect(Collectors.toList());
//
//		response.setData(new ClassResponse().mapToList(listClassCheck));
//
//		return new ResponseEntity<>(response, HttpStatus.OK);
//	}

//	@GetMapping("/{id}/list-student")
//	public ResponseEntity<BaseResponse<List<UserCourseResponse>>> getListStudent(@PathVariable("id") int id)
//			throws Exception {
//
//		BaseResponse<List<UserCourseResponse>> response = new BaseResponse<>();
//		Pagination pagination = new Pagination(0, 20);
//		Course checkCourse = courseService.findOne(id);
//
//		if (checkCourse == null) {
//			response.setStatus(HttpStatus.BAD_REQUEST);
//			response.setMessageError(StringErrorValue.COURSE_NOT_FOUND);
//			return new ResponseEntity<>(response, HttpStatus.OK);
//		}
//
//		StoreProcedureListResult<UserCourse> listUserCourses = userCourseService.spGUserCourse(id, 0, -1, -1, -1,
//				pagination, 0);
//
//		List<UserCourseResponse> listData = listUserCourses.getResult().stream().map(x -> {
//			Users user = new Users();
//			try {
//				user = userService.findOne(x.getStudentId());
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//			return new UserCourseResponse(x, user);
//		}).collect(Collectors.toList());
//
//		response.setData(listData);
//
//		return new ResponseEntity<>(response, HttpStatus.OK);
//	}

	private LessonsResponse handleLessonResponse(Lessons lessons, UserCourseProgress userCourseProgress) {
		/*
		 * Nếu free thì không khóa và kiểm tra nếu completed thì trả về trạng thái đã
		 * học
		 * 
		 * Nếu userCourseProgress null => chưa học =>khóa
		 * 
		 */
		if (lessons.getIsFree() == 1) {
			return new LessonsResponse(lessons, 0,
					(userCourseProgress != null && userCourseProgress.getIsCompleted() == 1) ? 1 : 0);
		}
		if (userCourseProgress == null) {
			return new LessonsResponse(lessons, 1, 0);
		} else if (userCourseProgress.getIsCompleted() == 0) {
			return new LessonsResponse(lessons, 0, 0);
		}
		return new LessonsResponse(lessons, 0, 1);
	}

}
