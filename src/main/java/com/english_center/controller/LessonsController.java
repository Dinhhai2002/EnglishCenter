package com.english_center.controller;

import java.io.ByteArrayInputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
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

import com.english_center.common.enums.RoleEnum;
import com.english_center.common.enums.StatusEnum;
import com.english_center.common.enums.VideoTypeEnum;
import com.english_center.common.utils.Pagination;
import com.english_center.common.utils.StringErrorValue;
import com.english_center.common.utils.Utils;
import com.english_center.entity.Chapter;
import com.english_center.entity.Course;
import com.english_center.entity.Lessons;
import com.english_center.entity.UserCourseProgress;
import com.english_center.entity.Users;
import com.english_center.entity.VideoWatchHistory;
import com.english_center.model.StoreProcedureListResult;
import com.english_center.request.CRUDLessonsRequest;
import com.english_center.request.UpdateTimeViewVideoRequest;
import com.english_center.response.BaseListDataResponse;
import com.english_center.response.BaseResponse;
import com.english_center.response.LessonsResponse;
import com.english_center.response.VideoWatchHistoryResponse;
import com.english_center.service.ClassService;
import com.google.api.client.http.InputStreamContent;
import com.google.api.services.drive.model.File;

@RestController
@RequestMapping("/api/v1/lessons")
public class LessonsController extends BaseController {

	@Autowired
	ClassService classService;

	@GetMapping("")
	public ResponseEntity<BaseResponse<BaseListDataResponse<LessonsResponse>>> getAll(
			@RequestParam(name = "course_id", required = false, defaultValue = "-1") int courseId,
			@RequestParam(name = "chapter_id", required = false, defaultValue = "-1") int chapterId,
			@RequestParam(name = "key_search", required = false, defaultValue = "") String keySearch,
			@RequestParam(name = "status", required = false, defaultValue = "-1") int status,
			@RequestParam(name = "is_pagination", required = false, defaultValue = "1") int isPagination,
			@RequestParam(name = "page", required = false, defaultValue = "1") int page,
			@RequestParam(name = "limit", required = false, defaultValue = "10") int limit) throws Exception {
		BaseResponse<BaseListDataResponse<LessonsResponse>> response = new BaseResponse<>();
		Pagination pagination = new Pagination(page, limit);
		StoreProcedureListResult<Lessons> listLessons = lessonsService.spGListLessons(courseId, chapterId, keySearch,
				status, pagination, isPagination);

		BaseListDataResponse<LessonsResponse> listData = new BaseListDataResponse<>();
		List<Course> listCourse = courseService.findAll();
		List<Chapter> listChapter = chapterService.spGListChapter(-1, "", -1, new Pagination(0, 20), 0).getResult();

		Map<Integer, Course> courseMap = new HashMap<>();
		for (Course course : listCourse) {
			courseMap.put(course.getId(), course);
		}

		Map<Integer, Chapter> chapterMap = new HashMap<>();
		for (Chapter chapter : listChapter) {
			chapterMap.put(chapter.getId(), chapter);
		}

		List<LessonsResponse> listLessonsResponse = listLessons.getResult().stream().map(x -> {
			Course course = courseMap.get(x.getCourseId());
			Chapter chapter = chapterMap.get(x.getChapterId());
			return new LessonsResponse(x, course, chapter);
		}).collect(Collectors.toList());

		listData.setList(listLessonsResponse);
		listData.setTotalRecord(listLessons.getTotalRecord());

		response.setData(listData);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@SuppressWarnings("rawtypes")
	@PostMapping("/create")
	@PreAuthorize("hasAnyAuthority('ADMIN')")
	public ResponseEntity<BaseResponse> create(@Valid @RequestBody CRUDLessonsRequest wrapper) throws Exception {

		BaseResponse response = new BaseResponse();

		Course checkCourse = courseService.findOne(wrapper.getCourseId());

		if (checkCourse == null) {
			response.setStatus(HttpStatus.BAD_REQUEST);
			response.setMessageError(StringErrorValue.COURSE_NOT_FOUND);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

		Chapter chapter = chapterService.findOne(wrapper.getChapterId());

		if (chapter == null) {
			response.setStatus(HttpStatus.BAD_REQUEST);
			response.setMessageError(StringErrorValue.CHAPTER_NOT_FOUND);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

		List<Lessons> listLessons = lessonsService
				.spGListLessons(checkCourse.getId(), chapter.getId(), wrapper.getName(), 1, new Pagination(0, 20), 0)
				.getResult();
		if (!listLessons.isEmpty()) {
			response.setStatus(HttpStatus.BAD_REQUEST);
			response.setMessageError(
					StringErrorValue.nameLessonsIsExist(wrapper.getName(), chapter.getName(), checkCourse.getName()));
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

		// lấy số sort của bài học cuối thuộc chương học của khóa học
		int sort = caculateSortLessons(lessonsService
				.spGListLessons(checkCourse.getId(), chapter.getId(), "", 1, new Pagination(0, 20), 0).getResult());

		Lessons lessons = new Lessons();

		lessons.setName(wrapper.getName());
		lessons.setDescription(wrapper.getDescription());
		lessons.setContent(wrapper.getContent());
		lessons.setCourseId(wrapper.getCourseId());
		lessons.setChapterId(wrapper.getChapterId());
		lessons.setSort(sort + 1);

		lessons.setStatus(0);
		lessons.setVideoType(wrapper.getVideoType());
		lessons.setIdVideo("");

		/*
		 * videoType = 0 => url youtube || videoType = 1 => url driver Nếu type = 0 thì
		 * set luôn urlVideo
		 * 
		 */
		if (wrapper.getVideoType() == VideoTypeEnum.YOUTUBE.getValue()) {
			String idVideo = Utils.extractVideoId(wrapper.getUrlVideo());
			lessons.setIdVideo(idVideo);
			lessons.setStatus(1);
			lessons.setDuration(calculateTimeVideoYoutube(idVideo));

			// update duration course
			checkCourse.setDuration(checkCourse.getDuration() + calculateTimeVideoYoutube(idVideo));
		}

		lessonsService.create(lessons);

		// set lại số lượng bài học của khóa học khi thêm thành công
		checkCourse.setLessons(checkCourse.getLessons() + 1);
		courseService.update(checkCourse);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@PostMapping("/{id}/update")
	@PreAuthorize("hasAnyAuthority('ADMIN')")
	public ResponseEntity<BaseResponse> update(@PathVariable("id") int id,
			@Valid @RequestBody CRUDLessonsRequest wrapper) throws Exception {

		BaseResponse response = new BaseResponse();
		Lessons lessons = lessonsService.findOne(id);

		if (lessons == null) {
			response.setStatus(HttpStatus.BAD_REQUEST);
			response.setMessageError(StringErrorValue.LESSONS_NOT_FOUND);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

		Course checkCourse = courseService.findOne(wrapper.getCourseId());

		if (checkCourse == null) {
			response.setStatus(HttpStatus.BAD_REQUEST);
			response.setMessageError(StringErrorValue.COURSE_NOT_FOUND);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

		Chapter chapter = chapterService.findOne(wrapper.getChapterId());

		if (chapter == null) {
			response.setStatus(HttpStatus.BAD_REQUEST);
			response.setMessageError(StringErrorValue.CHAPTER_NOT_FOUND);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

		// kiểm tra tên thay đổi đã tồn tại hay chưa
		if (!lessons.getName().equals(wrapper.getName()) && !lessonsService
				.spGListLessons(checkCourse.getId(), chapter.getId(), wrapper.getName(), 1, new Pagination(0, 20), 0)
				.getResult().isEmpty()) {
			response.setStatus(HttpStatus.BAD_REQUEST);
			response.setMessageError(
					StringErrorValue.nameLessonsIsExist(wrapper.getName(), chapter.getName(), checkCourse.getName()));
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

		lessons.setName(wrapper.getName());
		lessons.setDescription(wrapper.getDescription());
		lessons.setContent(wrapper.getContent());
		lessons.setIsFree(wrapper.getIsFree());

		String newIdVideo = Utils.extractVideoId(wrapper.getUrlVideo());

		if (!lessons.getIdVideo().equals(newIdVideo) && lessons.getVideoType() == VideoTypeEnum.YOUTUBE.getValue()) {
			/*
			 * newDuration = currentDuration - durationLessonCurrent + durationLessonsNew
			 */
			checkCourse.setDuration(checkCourse.getDuration() - calculateTimeVideoYoutube(lessons.getIdVideo())
					+ calculateTimeVideoYoutube(newIdVideo));

			lessons.setIdVideo(newIdVideo);

			courseService.update(checkCourse);

		}

		lessonsService.update(lessons);

		response.setData(new LessonsResponse(lessons, checkCourse, chapter));

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@GetMapping("/{id}/detail")
	public ResponseEntity<BaseResponse<LessonsResponse>> findOne(@PathVariable("id") int id) throws Exception {

		BaseResponse<LessonsResponse> response = new BaseResponse();
		Users users = this.getUser();
		Lessons lessons = lessonsService.findOne(id);

		if (lessons == null) {
			response.setStatus(HttpStatus.BAD_REQUEST);
			response.setMessageError(StringErrorValue.LESSONS_NOT_FOUND);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

		/*
		 * Chặn người dùng cố truy cập những bài học đã tắt
		 */
		if (lessons.getStatus() == 0 && users.getRole() != RoleEnum.ADMIN.getValue()) {
			response.setStatus(HttpStatus.BAD_REQUEST);
			response.setMessageError(StringErrorValue.LESSONS_NOT_FOUND);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

//		UserCourse userCourse = userCourseService
//				.spGUserCourse(lessons.getCourseId(), -1, users.getId(), 0, 0, new Pagination(0, 20), 0).getResult()
//				.stream().findFirst().orElse(null);

		/*
		 * Kiểm tra nếu người dùng chưa đăng kí khóa học mà cố truy cập vào bài học đó
		 */
//		if (userCourse == null && users.getRole() != RoleEnum.ADMIN.getValue() && lessons.getIsFree() == 0) {
//			response.setStatus(HttpStatus.BAD_REQUEST);
//			response.setMessageError(StringErrorValue.LESSONS_NOT_FOUND);
//			return new ResponseEntity<>(response, HttpStatus.OK);
//		}

		response.setData(new LessonsResponse(lessons, courseService.findOne(lessons.getCourseId()),
				chapterService.findOne(lessons.getChapterId())));

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping("/{id}/change-status")
	@PreAuthorize("hasAnyAuthority('ADMIN')")
	public ResponseEntity<BaseResponse<LessonsResponse>> changeStatus(@PathVariable("id") int id) throws Exception {
		BaseResponse<LessonsResponse> response = new BaseResponse<>();
		Lessons lessons = lessonsService.findOne(id);

		if (lessons == null) {
			response.setStatus(HttpStatus.BAD_REQUEST);
			response.setMessageError(StringErrorValue.LESSONS_NOT_FOUND);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
		Course course = courseService.findOne(lessons.getCourseId());

		if (course == null) {
			response.setStatus(HttpStatus.BAD_REQUEST);
			response.setMessageError(StringErrorValue.COURSE_NOT_FOUND);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

		if (lessons.getStatus() == StatusEnum.NOT_ACTIVE.getValue()) {
			/*
			 * kiểm tra nếu là bài học thuộc video driver mà chưa up video thì không hoạt
			 * động
			 */

			if (lessons.getVideoType() == VideoTypeEnum.DRIVER.getValue() && lessons.getIdVideo().equals("")) {
				response.setStatus(HttpStatus.BAD_REQUEST);
				response.setMessageError(StringErrorValue.LESSONS_IS_NOT_VIDEO);
				return new ResponseEntity<>(response, HttpStatus.OK);
			}

			/*
			 * Kiểm tra nếu bài học có tên thuộc khóa học và chương học trùng lặp hoạt động
			 * thì không cho bật trạng thái
			 */

			List<Lessons> listLessons = lessonsService.spGListLessons(lessons.getCourseId(), lessons.getChapterId(),
					lessons.getName(), 1, new Pagination(0, 20), 0).getResult();
			if (!listLessons.isEmpty()) {
				response.setStatus(HttpStatus.BAD_REQUEST);
				response.setMessageError(StringErrorValue.lessonsIsActive(lessons.getName()));
				return new ResponseEntity<>(response, HttpStatus.OK);
			}

			/*
			 * bật trạng thái thì cộng duration của course hiện tại và video bật lên
			 */
			course.setDuration(course.getDuration() + calculateTimeVideo(lessons.getIdVideo(), lessons.getVideoType()));
			courseService.update(course);

		} else {
			/*
			 * tắt thì trừ time video đi
			 */
			course.setDuration(course.getDuration() - calculateTimeVideo(lessons.getIdVideo(), lessons.getVideoType()));
			course.setLessons(course.getLessons() - 1);
			courseService.update(course);
		}

		lessons.setStatus(lessons.getStatus() == 1 ? 0 : 1);

		lessonsService.update(lessons);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@PostMapping("/{id}/upload-driver")
	@PreAuthorize("hasAnyAuthority('ADMIN')")
	public ResponseEntity<BaseResponse> uploadDriver(@PathVariable("id") int id, MultipartFile file) throws Exception {

		BaseResponse response = new BaseResponse();

		Lessons lessons = lessonsService.findOne(id);

		if (lessons == null) {
			response.setStatus(HttpStatus.BAD_REQUEST);
			response.setMessageError(StringErrorValue.LESSONS_NOT_FOUND);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

		Course course = courseService.findOne(lessons.getCourseId());

		if (course == null) {
			response.setStatus(HttpStatus.BAD_REQUEST);
			response.setMessageError(StringErrorValue.COURSE_NOT_FOUND);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

		File fileMetadata = new File();
		fileMetadata.setParents(Collections.singletonList(applicationProperties.getFolderUpload()));
		String fileName = UUID.randomUUID().toString() + file.getOriginalFilename();
		fileMetadata.setName(fileName);
		File uploadFile = googleDrive.files()
				.create(fileMetadata,
						new InputStreamContent(file.getContentType(), new ByteArrayInputStream(file.getBytes())))
				.setFields("id, size, mimeType, webViewLink, videoMediaMetadata, webContentLink").execute();
		googleDrive.permissions().create(uploadFile.getId(), getPermission()).execute();

		// update lessons
		lessons.setIdVideo(uploadFile.getId());
		lessons.setVideoType(1);

		lessonsService.update(lessons);

		// update duration
		course.setDuration(course.getDuration() + calculateTimeVideoDriver(uploadFile.getId()));
		courseService.update(course);

		response.setData(lessons);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@PostMapping("/{id}/video-watch/update")
	public ResponseEntity<BaseResponse> updateTimeVideoReally(@PathVariable("id") int id,
			@Valid @RequestBody UpdateTimeViewVideoRequest wrapper) throws Exception {

		BaseResponse response = new BaseResponse();
		Users users = this.getUser();
		Lessons lessons = lessonsService.findOne(id);

		if (lessons == null) {
			response.setStatus(HttpStatus.BAD_REQUEST);
			response.setMessageError(StringErrorValue.LESSONS_NOT_FOUND);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

		VideoWatchHistory videoWatchHistory = videoWatchHistoryService.findByLessonsAndUser(lessons.getId(),
				users.getId());

		if (videoWatchHistory == null) {
			response.setStatus(HttpStatus.BAD_REQUEST);
			response.setMessageError(StringErrorValue.VIDEO_WATCH_HISTORY_NOT_FOUND);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

		/*
		 * check nếu đã xem xong và đã chuyển sang bài khác thì không cần update thêm
		 * thời gian xem nữa
		 */
		if (videoWatchHistory.getWatchTime() > lessons.getDuration() + 500000 && videoWatchHistory.getIsDone() == 1) {
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

		videoWatchHistory.setWatchTime(videoWatchHistory.getWatchTime() + wrapper.getDuration());

		// check nếu time vượt qua 80% thì được next bài tiếp theo
		if (videoWatchHistory.getIsDone() == 0 && Boolean.TRUE
				.equals(Utils.isAccessNextVideo(videoWatchHistory.getWatchTime(), lessons.getDuration()))) {
			UserCourseProgress userCourseProgress = userCourseProgressService.findByLessonsAndUser(lessons.getId(),
					users.getId());

			userCourseProgress.setIsCompleted(1);

			userCourseProgressService.update(userCourseProgress);

			Lessons nextLessons = lessonsDao.spGListLessons(lessons.getCourseId(), lessons.getChapterId(), "", 1,
					new Pagination(0, 20), 0, lessons.getSort() + 1).getResult().stream().findFirst().orElse(null);
			UserCourseProgress nextUserCourseProgress = new UserCourseProgress();
			VideoWatchHistory nextVideoWatchHistory = new VideoWatchHistory();

			/**
			 * Trường hơp không có bài học tiếp theo trong chương -> lấy ra bài học đầu tiên
			 * của chương tiếp theo. Ngược lại thì lấy bài học kế tiếp
			 * 
			 **/
			if (nextLessons == null) {
				Chapter nextChapter = chapterDao
						.spGListChapter(lessons.getCourseId(), "", 1, new Pagination(0, 20), 0,
								chapterService.findOne(lessons.getChapterId()).getSort() + 1)
						.getResult().stream().findFirst().orElse(null);
				if (nextChapter != null) {
					// lấy bài đầu tiên của chương tiếp theo
					nextLessons = lessonsDao.spGListLessons(lessons.getCourseId(), nextChapter.getId(), "", 1,
							new Pagination(0, 20), 0, 1).getResult().stream().findFirst().orElse(null);
					if (nextLessons != null) {

						nextUserCourseProgress.setCourseId(lessons.getCourseId());
						nextUserCourseProgress.setChapterId(nextChapter.getId());
						nextUserCourseProgress.setLessonsId(nextLessons.getId());
						nextUserCourseProgress.setUserId(users.getId());

						userCourseProgressService.create(nextUserCourseProgress);

						nextVideoWatchHistory.setUserId(users.getId());
						nextVideoWatchHistory.setLessonsId(nextLessons.getId());

						videoWatchHistoryService.create(nextVideoWatchHistory);
					}

				}
			} else {
				nextUserCourseProgress.setCourseId(nextLessons.getCourseId());
				nextUserCourseProgress.setChapterId(nextLessons.getChapterId());
				nextUserCourseProgress.setLessonsId(nextLessons.getId());
				nextUserCourseProgress.setUserId(users.getId());

				userCourseProgressService.create(nextUserCourseProgress);

				nextVideoWatchHistory.setUserId(users.getId());
				nextVideoWatchHistory.setLessonsId(nextLessons.getId());

				videoWatchHistoryService.create(nextVideoWatchHistory);
			}
			videoWatchHistory.setIsDone(1);
			response.setData(new VideoWatchHistoryResponse(videoWatchHistory));

		}
		videoWatchHistoryService.update(videoWatchHistory);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
