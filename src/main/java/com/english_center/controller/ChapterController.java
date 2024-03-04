package com.english_center.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import com.english_center.entity.Chapter;
import com.english_center.entity.Course;
import com.english_center.entity.Lessons;
import com.english_center.model.StoreProcedureListResult;
import com.english_center.request.CRUDChapterRequest;
import com.english_center.response.BaseListDataResponse;
import com.english_center.response.BaseResponse;
import com.english_center.response.ChapterResponse;
import com.english_center.response.LessonsResponse;

@RestController
@RequestMapping("/api/v1/chapter")
public class ChapterController extends BaseController {

	@SuppressWarnings("rawtypes")
	@PostMapping("/create")
	@PreAuthorize("hasAnyAuthority('ADMIN')")
	public ResponseEntity<BaseResponse> create(@Valid @RequestBody CRUDChapterRequest wrapper) throws Exception {

		BaseResponse response = new BaseResponse();

		Course checkCourse = courseService.findOne(wrapper.getCourseId());

		if (checkCourse == null) {
			response.setStatus(HttpStatus.BAD_REQUEST);
			response.setMessageError(StringErrorValue.COURSE_NOT_FOUND);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

		if (chapterService.findByNameAndCourse(wrapper.getName(), wrapper.getCourseId()) != null) {
			response.setStatus(HttpStatus.BAD_REQUEST);
			response.setMessageError(StringErrorValue.nameChapterIsExist(wrapper.getName(), checkCourse.getName()));
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
		// lấy số sort của chương học cuối thuộc khóa học
		int sort = caculateSortChapter(
				chapterService.spGListChapter(checkCourse.getId(), "", 1, new Pagination(0, 20), 0).getResult());

		Chapter chapter = new Chapter();
		chapter.setName(wrapper.getName());
		chapter.setCourseId(wrapper.getCourseId());
		chapter.setSort(sort + 1);

		chapterService.create(chapter);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@PostMapping("/{id}/update")
	@PreAuthorize("hasAnyAuthority('ADMIN')")
	public ResponseEntity<BaseResponse> update(@PathVariable("id") int id,
			@Valid @RequestBody CRUDChapterRequest wrapper) throws Exception {

		BaseResponse response = new BaseResponse();

		Chapter chapter = chapterService.findOne(id);

		if (chapter == null) {
			response.setStatus(HttpStatus.BAD_REQUEST);
			response.setMessageError(StringErrorValue.CHAPTER_NOT_FOUND);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

		Course checkCourse = courseService.findOne(wrapper.getCourseId());

		if (checkCourse == null) {
			response.setStatus(HttpStatus.BAD_REQUEST);
			response.setMessageError(StringErrorValue.COURSE_NOT_FOUND);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

		if (!chapter.getName().equals(wrapper.getName())
				&& chapterService.findByNameAndCourse(wrapper.getName(), wrapper.getCourseId()) != null) {
			response.setStatus(HttpStatus.BAD_REQUEST);
			response.setMessageError(StringErrorValue.nameChapterIsExist(wrapper.getName(), checkCourse.getName()));
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

		chapter.setName(wrapper.getName());

		chapterService.update(chapter);

		response.setData(new ChapterResponse(chapter));

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("")
	public ResponseEntity<BaseResponse<BaseListDataResponse<ChapterResponse>>> getAll(
			@RequestParam(name = "course_id", required = false, defaultValue = "-1") int courseId,
			@RequestParam(name = "key_search", required = false, defaultValue = "") String keySearch,
			@RequestParam(name = "status", required = false, defaultValue = "-1") int status,
			@RequestParam(name = "is_pagination", required = false, defaultValue = "-1") int isPagination,
			@RequestParam(name = "page", required = false, defaultValue = "1") int page,
			@RequestParam(name = "limit", required = false, defaultValue = "10") int limit) throws Exception {
		BaseResponse<BaseListDataResponse<ChapterResponse>> response = new BaseResponse<>();
		Pagination pagination = new Pagination(page, limit);
		StoreProcedureListResult<Chapter> listChapter = chapterService.spGListChapter(courseId, keySearch, status,
				pagination, isPagination);

		BaseListDataResponse<ChapterResponse> listData = new BaseListDataResponse<>();
		List<Course> courses = courseService.findAll();

		Map<Integer, Course> courseMap = new HashMap<>();
		for (Course course : courses) {
			courseMap.put(course.getId(), course);
		}

		List<ChapterResponse> listChapterResponse = listChapter.getResult().stream().map(chapter -> {
			Course course = courseMap.get(chapter.getCourseId());

			return new ChapterResponse(chapter, course);
		}).collect(Collectors.toList());

		listData.setList(listChapterResponse);
		listData.setTotalRecord(listChapter.getTotalRecord());

		response.setData(listData);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@GetMapping("/{id}/detail")
	public ResponseEntity<BaseResponse> findOne(@PathVariable("id") int id) throws Exception {
		BaseResponse response = new BaseResponse();

		Chapter chapter = chapterService.findOne(id);

		if (chapter == null) {
			response.setStatus(HttpStatus.BAD_REQUEST);
			response.setMessageError(StringErrorValue.CHAPTER_NOT_FOUND);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

		response.setData(new ChapterResponse(chapter, courseService.findOne(chapter.getCourseId())));

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping("/{id}/change-status")
	@PreAuthorize("hasAnyAuthority('ADMIN')")
	public ResponseEntity<BaseResponse<List<LessonsResponse>>> changeStatus(@PathVariable("id") int id)
			throws Exception {
		BaseResponse<List<LessonsResponse>> response = new BaseResponse<>();
		Chapter chapter = chapterService.findOne(id);

		if (chapter == null) {
			response.setStatus(HttpStatus.BAD_REQUEST);
			response.setMessageError(StringErrorValue.CHAPTER_NOT_FOUND);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

		if (chapter.getStatus() == 1) {
			List<Lessons> lessons = lessonsService.findByChapterId(id).stream().filter(x -> (x.getStatus() == 1))
					.collect(Collectors.toList());
			// lấy danh sách bài học đang hoạt động

			if (!lessons.isEmpty()) {
				response.setData(new LessonsResponse().mapToList(lessons));
				response.setStatus(HttpStatus.BAD_REQUEST);
				response.setMessageError(StringErrorValue.CHAPTER_IS_LESSONS_ACTIVE);
				return new ResponseEntity<>(response, HttpStatus.OK);
			}
		}

		chapter.setStatus(chapter.getStatus() == 1 ? 0 : 1);

		chapterService.update(chapter);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
