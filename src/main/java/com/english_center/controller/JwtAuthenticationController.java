package com.english_center.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.english_center.common.enums.OtpEnum;
import com.english_center.common.utils.HttpService;
import com.english_center.common.utils.Pagination;
import com.english_center.common.utils.StringErrorValue;
import com.english_center.common.utils.Utils;
import com.english_center.entity.Banner;
import com.english_center.entity.CategoryBlog;
import com.english_center.entity.CategoryCourse;
import com.english_center.entity.CategoryExam;
import com.english_center.entity.Chapter;
import com.english_center.entity.Cities;
import com.english_center.entity.Comments;
import com.english_center.entity.Course;
import com.english_center.entity.Exam;
import com.english_center.entity.Lessons;
import com.english_center.entity.Posts;
import com.english_center.entity.Rating;
import com.english_center.entity.ReplyComments;
import com.english_center.entity.TopicExam;
import com.english_center.entity.UserRegister;
import com.english_center.entity.Users;
import com.english_center.model.PostModel;
import com.english_center.model.StoreProcedureListResult;
import com.english_center.request.CRUDUserRequest;
import com.english_center.request.ConfirmOtpRequest;
import com.english_center.request.GoogleAccountRequest;
import com.english_center.request.JwtRequest;
import com.english_center.request.OTPRegisterUserRequest;
import com.english_center.request.OTPRequest;
import com.english_center.request.ResetPasswordRequest;
import com.english_center.response.BannerResponse;
import com.english_center.response.BaseListDataResponse;
import com.english_center.response.BaseResponse;
import com.english_center.response.CategoryBlogResponse;
import com.english_center.response.CategoryCourseResponse;
import com.english_center.response.CategoryExamResponse;
import com.english_center.response.ChapterResponse;
import com.english_center.response.CityResponse;
import com.english_center.response.CommentsResponse;
import com.english_center.response.CourseResponse;
import com.english_center.response.DistrictResponse;
import com.english_center.response.ExamResponse;
import com.english_center.response.JwtResponse;
import com.english_center.response.LessonsResponse;
import com.english_center.response.PostResponse;
import com.english_center.response.ReplyCommentsResponse;
import com.english_center.response.TopicExamReponse;
import com.english_center.response.UserResponse;
import com.english_center.response.WardsResponse;
import com.english_center.service.BannerService;
import com.english_center.service.CategoryBlogService;
import com.english_center.service.PostService;
import com.english_center.service.RatingService;

@RestController
@RequestMapping("/api/v1/authentication")
@CrossOrigin
public class JwtAuthenticationController extends BaseController {

	@Autowired
	PostService postService;

	@Autowired
	CategoryBlogService categoryBlogService;

	@Autowired
	RatingService ratingService;
	
	@Autowired
	BannerService bannerService;

	@PostMapping("/login")
	public ResponseEntity<BaseResponse<JwtResponse>> createAuthenticationToken(@RequestBody JwtRequest wrapper)
			throws Exception {
		BaseResponse<JwtResponse> response = new BaseResponse<>();
		Users user = userService.findUsersByUsersNameAndPassword(wrapper.getUsername(),
				Utils.encodeBase64(wrapper.getPassword()));

		if (user == null) {
			response.setStatus(HttpStatus.BAD_REQUEST);
			response.setMessageError(StringErrorValue.LOGIN_FAIL);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

		UserDetails userDetails = userDetailsService.loadUserByUsername(wrapper.getUsername());

		if (user.getIsActive() == 0) {
			response.setStatus(HttpStatus.BAD_REQUEST);
			response.setMessageError(StringErrorValue.USER_IS_LOCKED);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

		String token = jwtTokenUtil.generateToken(userDetails);
		user.setAccessToken(token);
		userService.update(user);
		response.setData(new JwtResponse(token));
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping("/register")
	public ResponseEntity<BaseResponse<UserResponse>> spUCreateUser(@Valid @RequestBody CRUDUserRequest wrapper)
			throws Exception {
		BaseResponse<UserResponse> response = new BaseResponse<>();
//		BCrypt.hashpw(wrapper.getPassword(), BCrypt.gensalt(12))

		response.setData(new UserResponse(userService.spUCreateUsers(wrapper.getUserName(), wrapper.getFullName(),
				wrapper.getEmail(), wrapper.getPhone(), Utils.encodeBase64(wrapper.getPassword()), wrapper.getGender(),
				this.formatDate(wrapper.getBirthday()), wrapper.getWardId(), wrapper.getDistrictId(),
				wrapper.getCityId(), wrapper.getFullAddress())));

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@SuppressWarnings("rawtypes")
	@PostMapping("/reset-password")
	public ResponseEntity<BaseResponse> resetPassword(@Valid @RequestBody ResetPasswordRequest wrapper)
			throws Exception {

		BaseResponse response = new BaseResponse<>();

		Users user = userService.findUsersByUsersName(wrapper.getUserName());

		if (user == null) {
			response.setStatus(HttpStatus.BAD_REQUEST);
			response.setMessageError(StringErrorValue.USER_NOT_FOUND);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

		if (!wrapper.getNewPassword().equals(wrapper.getConfirmPassword())) {
			response.setStatus(HttpStatus.BAD_REQUEST);
			response.setMessageError(StringErrorValue.ERROR_CONFIRM_PASSWORD_AND_CONFIRM);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

		if (user.getIsConfirmOtp() == 0) {
			response.setStatus(HttpStatus.BAD_REQUEST);
			response.setMessageError(StringErrorValue.OTP_IS_NOT_CONFIRM);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

		user.setPassword(Utils.encodeBase64(wrapper.getNewPassword()));
		user.setIsConfirmOtp(0);

		userService.update(user);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@SuppressWarnings("rawtypes")
	@PostMapping("/otp-register")
	public ResponseEntity<BaseResponse> otpRegister(@Valid @RequestBody OTPRegisterUserRequest wrapper)
			throws Exception {

		BaseResponse<Object> response = new BaseResponse<>();

		if (userService.findUsersByUsersName(wrapper.getUserName()) != null) {
			response.setStatus(HttpStatus.BAD_REQUEST);
			response.setMessageError(StringErrorValue.NAME_USER_IS_EXIST);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

		if (userService.findUsersByEmail(wrapper.getEmail(), 0) != null) {
			response.setStatus(HttpStatus.BAD_REQUEST);
			response.setMessageError(StringErrorValue.MAIL_USER_IS_EXIST);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

		if (userService.findUsersByPhone(wrapper.getPhone()) != null) {
			response.setStatus(HttpStatus.BAD_REQUEST);
			response.setMessageError(StringErrorValue.PHONE_USER_IS_EXIST);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

		/*
		 * - lấy ra userRegister -> Nếu có thì kiểm tra mã otp còn hạn hay không + Nếu
		 * hết thì set status =0 + Ngược lại thì thông báo tài khoản này đăng có người
		 * khác xác thực
		 */
		UserRegister checkUserRegister = userRegisterService.findUsersRegisterByUsersNameAndEmail(wrapper.getUserName(),
				wrapper.getEmail());
		if (checkUserRegister != null) {

			if (this.caculateOtpExpired(checkUserRegister.getOtpCreatedAt()) > 0) {
				checkUserRegister.setStatus(0);
				userRegisterService.update(checkUserRegister);
			}

			else {
				response.setStatus(HttpStatus.BAD_REQUEST);
				response.setMessageError(StringErrorValue.USER_REGISTER_IS_AUTHENTICATING);
				return new ResponseEntity<>(response, HttpStatus.OK);
			}
		}

		Random rand = new Random();
		int otpvalue = rand.nextInt(1255650);
		sendEmail.sendSimpleEmail(wrapper.getEmail(), "Mã OTP",
				"Mã OTP là:" + otpvalue + ". Mã otp này có thời hạn là 3p");

		UserRegister userRegister = new UserRegister();
		userRegister.setUserName(wrapper.getUserName());
		userRegister.setEmail(wrapper.getEmail());
		userRegister.setOtp(otpvalue);
		userRegister.setOtpCreatedAt(new Date());
		userRegister.setStatus(1);

		userRegisterService.create(userRegister);

		response.setData(otpvalue);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@SuppressWarnings("rawtypes")
	@PostMapping("/otp")
	public ResponseEntity<BaseResponse> otpForgot(@Valid @RequestBody OTPRequest wrapper) throws Exception {

		BaseResponse<Object> response = new BaseResponse<>();
		Users user = userService.findUsersByUsersNameAndEmail(wrapper.getUserName(), wrapper.getEmail());

		if (user == null) {
			response.setStatus(HttpStatus.BAD_REQUEST);
			response.setMessageError(StringErrorValue.USER_NOT_FOUND);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

		if (user.getIsGoogle() == 1) {
			response.setStatus(HttpStatus.BAD_REQUEST);
			response.setMessageError(StringErrorValue.ACCOUNT_GOOLE_IS_NOT_PERMIT);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

		Random rand = new Random();
		int otpvalue = rand.nextInt(1255650);
		sendEmail.sendSimpleEmail(wrapper.getEmail(), "Mã OTP",
				"Mã OTP là:" + otpvalue + ". Mã otp này có thời hạn là 3p");

		user.setOtp(otpvalue);
		user.setOtpCreatedAt(new Date());

		userService.update(user);

		response.setData(otpvalue);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@SuppressWarnings("rawtypes")
	@PostMapping("/confirm-otp")
	public ResponseEntity<BaseResponse> confirmOtp(@Valid @RequestBody ConfirmOtpRequest wrapper) throws Exception {

		BaseResponse<Object> response = new BaseResponse<>();

		/*
		 * type = 0 => otp register || type = 1 => otp forgot password
		 */

		if (wrapper.getType() == OtpEnum.REGISTER.getValue()) {

			UserRegister userRegister = userRegisterService.findUsersRegisterByUsersNameAndEmail(wrapper.getUserName(),
					wrapper.getEmail());

			if (userRegister == null) {
				response.setStatus(HttpStatus.BAD_REQUEST);
				response.setMessageError(StringErrorValue.OTP_IS_NOT_USING);
				return new ResponseEntity<>(response, HttpStatus.OK);
			}

			if (userRegister.getOtp() != wrapper.getOtp()) {
				response.setStatus(HttpStatus.BAD_REQUEST);
				response.setMessageError(StringErrorValue.OTP_IS_NOT_CORRECT);
				return new ResponseEntity<>(response, HttpStatus.OK);
			}

			// xử lí thời gian mã OTP.Quy định mã otp có thời hạn trong 3 phút

			if (this.caculateOtpExpired(userRegister.getOtpCreatedAt()) > 0) {
				userRegister.setStatus(0);
				userRegisterService.update(userRegister);
				response.setStatus(HttpStatus.BAD_REQUEST);
				response.setMessageError(StringErrorValue.OTP_IS_EXPIRED);
				return new ResponseEntity<>(response, HttpStatus.OK);
			}

		} else {
			Users user = userService.findUsersByUsersNameAndEmail(wrapper.getUserName(), wrapper.getEmail());
			if (user == null) {
				response.setStatus(HttpStatus.BAD_REQUEST);
				response.setMessageError(StringErrorValue.USER_NOT_FOUND);
				return new ResponseEntity<>(response, HttpStatus.OK);
			}

			if (user.getOtp() != wrapper.getOtp()) {
				response.setStatus(HttpStatus.BAD_REQUEST);
				response.setMessageError(StringErrorValue.OTP_IS_NOT_CORRECT);
				return new ResponseEntity<>(response, HttpStatus.OK);
			}

			// xử lí thời gian mã OTP.Quy định mã otp có thời hạn trong 3 phút
			if (this.caculateOtpExpired(user.getOtpCreatedAt()) > 0) {
				response.setStatus(HttpStatus.BAD_REQUEST);
				response.setMessageError(StringErrorValue.OTP_IS_EXPIRED);
				return new ResponseEntity<>(response, HttpStatus.OK);
			}

			user.setIsConfirmOtp(1);
			userService.update(user);
		}

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping("/login-google")
	public ResponseEntity<BaseResponse<JwtResponse>> loginGoogle(@Valid @RequestBody GoogleAccountRequest wrapper)
			throws Exception {

		BaseResponse<JwtResponse> response = new BaseResponse<>();
		Users registerUser = new Users();
		String token;
		Users users = userService.findUsersByEmail(wrapper.getEmail(), 1);

//		Nếu chưa có user thì tạo và gọi api login để set Token
		if (users == null) {
			registerUser.setEmail(wrapper.getEmail());
			registerUser.setUserName(wrapper.getEmail());
			registerUser.setAvatarUrl(wrapper.getImageUrl());
			registerUser.setIsGoogle(1);
			registerUser.setPassword(Utils.encodeBase64(applicationProperties.getPasswordAccountGoogle()));
			registerUser.setFullName(wrapper.getFullname());
			registerUser.setIsActive(1);
			userService.create(registerUser);

			token = HttpService.login(wrapper.getEmail(), applicationProperties.getPasswordAccountGoogle(),
					applicationProperties.getBaseUrl());
			registerUser.setAccessToken(token);
			registerUser.setIsLogin(1);
			response.setData(new JwtResponse(token));
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
		token = HttpService.login(users.getUserName(), applicationProperties.getPasswordAccountGoogle(),
				applicationProperties.getBaseUrl());
		registerUser.setIsLogin(1);
		response.setData(new JwtResponse(token));

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/get-all-city")
	public ResponseEntity<BaseResponse<List<CityResponse>>> findAllCity() throws Exception {

		BaseResponse<List<CityResponse>> response = new BaseResponse<>();

		List<Cities> cities = cityService.getAll();

		response.setData(new CityResponse().mapToList(cities));

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/{id}/get-district-by-city")
	public ResponseEntity<BaseResponse<List<DistrictResponse>>> findDistrictByCityId(@PathVariable("id") int id)
			throws Exception {

		BaseResponse<List<DistrictResponse>> response = new BaseResponse<>();

		response.setData(new DistrictResponse().mapToList(districtService.findByCityId(id)));

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/{id}/get-ward-by-district")
	public ResponseEntity<BaseResponse<List<WardsResponse>>> findWardByDistrictId(@PathVariable("id") int id)
			throws Exception {

		BaseResponse<List<WardsResponse>> response = new BaseResponse<>();

		response.setData(new WardsResponse().mapToList(wardsService.findByDistrictId(id)));

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/list-course")
	public ResponseEntity<BaseResponse<BaseListDataResponse<CourseResponse>>> getAll(
			@RequestParam(name = "category_course_id", required = false, defaultValue = "-1") int categoryCourseId,
			@RequestParam(name = "key_search", required = false, defaultValue = "") String keySearch,
			@RequestParam(name = "status", required = false, defaultValue = "-1") int status,
			@RequestParam(name = "is_pagination", required = false, defaultValue = "-1") int isPagination,
			@RequestParam(name = "page", required = false, defaultValue = "1") int page,
			@RequestParam(name = "limit", required = false, defaultValue = "10") int limit) throws Exception {
		BaseResponse<BaseListDataResponse<CourseResponse>> response = new BaseResponse<>();
		Pagination pagination = new Pagination(page, limit);
		StoreProcedureListResult<Course> listCourse = courseService.spGCourse(categoryCourseId, keySearch, status, 1,
				pagination);

		BaseListDataResponse<CourseResponse> listData = new BaseListDataResponse<>();

//		xử lí trả về danh sách khóa học kèm theo user đó có đăng kí hay chưa
		List<CourseResponse> listCourseResponses = listCourse.getResult().stream().map(x -> {

			return new CourseResponse(x, 0);
		}).collect(Collectors.toList());

		listData.setList(listCourseResponses);
		listData.setTotalRecord(listCourse.getTotalRecord());

		response.setData(listData);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/list-exam")
	public ResponseEntity<BaseResponse<BaseListDataResponse<ExamResponse>>> getAll(
			@RequestParam(name = "category_exam_id", required = false, defaultValue = "-1") int categoryExamId,
			@RequestParam(name = "topic_exam_id", required = false, defaultValue = "-1") int topicExamId,
			@RequestParam(name = "status", required = false, defaultValue = "-1") int status,
			@RequestParam(name = "key_search", required = false, defaultValue = "") String keySearch,
			@RequestParam(name = "page", required = false, defaultValue = "0") int page,
			@RequestParam(name = "limit", required = false, defaultValue = "20") int limit) throws Exception {

		BaseResponse<BaseListDataResponse<ExamResponse>> response = new BaseResponse<>();
		Pagination pagination = new Pagination(page, limit);

		StoreProcedureListResult<Exam> exams = examService.spGListExam(categoryExamId, topicExamId, keySearch, status,
				pagination, 1);

		List<ExamResponse> listExamResponses = getExamResponses(exams.getResult());

		BaseListDataResponse<ExamResponse> listData = new BaseListDataResponse<>();
		listData.setList(listExamResponses);
		listData.setTotalRecord(exams.getTotalRecord());

		response.setData(listData);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("list-topic")
	public ResponseEntity<BaseResponse<List<TopicExamReponse>>> getAll() throws Exception {

		BaseResponse<List<TopicExamReponse>> response = new BaseResponse<>();

		List<TopicExam> topicExams = topicExamService.getAll();

		response.setData(new TopicExamReponse().mapToList(topicExams));
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("list-category-exam")
	public ResponseEntity<BaseResponse<List<CategoryExamResponse>>> getAllCategoryExam() throws Exception {
		StoreProcedureListResult<CategoryExam> listCategoryExam = categoryExamService.spGListCategoryExam("", 1,
				new Pagination(0, 100));
		BaseResponse<List<CategoryExamResponse>> response = new BaseResponse<>();

		response.setData(new CategoryExamResponse().mapToList(listCategoryExam.getResult()));

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/exam/{id}/detail")
	public ResponseEntity<BaseResponse<ExamResponse>> findOne(@PathVariable("id") int id) throws Exception {

		BaseResponse<ExamResponse> response = new BaseResponse<>();

		int countUser = this.countUserExam(id);
		int countComments = commentsService.countComments(id);

		response.setData(new ExamResponse(examService.findOne(id), countUser, 0, countComments, new ArrayList<>()));

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@GetMapping("/comments/get-by-exam")
	public ResponseEntity<BaseResponse> findByExam(@RequestParam(value = "exam_id", required = true) int examId)
			throws Exception {

		BaseResponse response = new BaseResponse();

		List<Comments> comments = commentsService.findByExamId(examId);

		List<Users> listUsers = userService.getAll();
		Map<Integer, Users> userMap = new HashMap<>();
		for (Users user : listUsers) {
			userMap.put(user.getId(), user);
		}

		List<ReplyComments> listReplyComments = replyCommentsService.getAll();

		/*
		 * Map trả về list comment lồng replyComments
		 */
		response.setData(comments.stream().map(x -> {

			Users usersComments = userMap.get(x.getUserId());

			List<ReplyComments> replyComments = listReplyComments.stream()
					.filter(itemReplyComment -> itemReplyComment.getCommentsId() == x.getId())
					.collect(Collectors.toList());

			List<ReplyCommentsResponse> replyCommentsResponse = replyComments.stream().map(y -> {

				Users usersReplyComments = userMap.get(y.getUserIdReplyComments());
				return new ReplyCommentsResponse(y, new UserResponse(usersReplyComments));
			}

			).collect(Collectors.toList());

			return new CommentsResponse(x, replyCommentsResponse, new UserResponse(usersComments));
		}).collect(Collectors.toList()));

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@GetMapping("/comments/count-by-exam")
	public ResponseEntity<BaseResponse> countCommentsExam(@RequestParam(value = "exam_id", required = true) int examId)
			throws Exception {

		BaseResponse response = new BaseResponse();

		response.setData(this.countComment(examId));

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@GetMapping("/exam/{id}/count-user")
	public ResponseEntity<BaseResponse> getResult(@PathVariable("id") int id) throws Exception {

		BaseResponse response = new BaseResponse();

		response.setData(this.countUserExam(id));

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/course/{id}")
	public ResponseEntity<BaseResponse<CourseResponse>> findOneCourse(@PathVariable("id") int id) throws Exception {

		BaseResponse<CourseResponse> response = new BaseResponse<>();
		Course course = courseService.findOne(id);

		if (course == null) {
			response.setStatus(HttpStatus.BAD_REQUEST);
			response.setMessageError(StringErrorValue.COURSE_NOT_FOUND);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

		int[] countLessons = { 0 };
		List<Chapter> listChapter = chapterService.findByCourseId(id);
		List<Lessons> listLessons = lessonsService.spGListLessons(-1, -1, "", 1, new Pagination(0, 20), 0).getResult();

		List<ChapterResponse> list = listChapter.stream().map(x -> {
			List<Lessons> listLessonsMap = listLessons.stream().filter(lesson -> lesson.getChapterId() == x.getId())
					.collect(Collectors.toList());

			countLessons[0] += listLessons.size();
			return new ChapterResponse(x, new LessonsResponse().mapToList(listLessonsMap));
		}).collect(Collectors.toList());

		response.setData(new CourseResponse(course, list, 0, countLessons[0]));

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/lessons/{id}/detail")
	public ResponseEntity<BaseResponse<LessonsResponse>> findOneLessons(@PathVariable("id") int id) throws Exception {

		BaseResponse<LessonsResponse> response = new BaseResponse<>();
		Lessons lessons = lessonsService.findOne(id);

		if (lessons == null) {
			response.setStatus(HttpStatus.BAD_REQUEST);
			response.setMessageError(StringErrorValue.LESSONS_NOT_FOUND);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

		response.setData(new LessonsResponse(lessons, courseService.findOne(lessons.getCourseId()),
				chapterService.findOne(lessons.getChapterId())));

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/post")
	public ResponseEntity<BaseResponse<BaseListDataResponse<PostResponse>>> getAll(
			@RequestParam(name = "category_blog_id", required = false, defaultValue = "-1") int categoryBlogId,
			@RequestParam(name = "user_id", required = false, defaultValue = "-1") int userId,
			@RequestParam(name = "key_search", required = false, defaultValue = "") String keySearch,
			@RequestParam(name = "status", required = false, defaultValue = "-1") int status,
			@RequestParam(name = "page", required = false, defaultValue = "1") int page,
			@RequestParam(name = "limit", required = false, defaultValue = "10") int limit) throws Exception {
		BaseResponse<BaseListDataResponse<PostResponse>> response = new BaseResponse<>();
		Pagination pagination = new Pagination(page, limit);
		StoreProcedureListResult<PostModel> listPost = postService.spGPosts(userId, categoryBlogId, keySearch, status,
				pagination);

		BaseListDataResponse<PostResponse> listData = new BaseListDataResponse<>();

		listData.setList(new PostResponse().mapToListModel(listPost.getResult()));
		listData.setTotalRecord(listPost.getTotalRecord());
		listData.setLimit(limit);

		response.setData(listData);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/post/{id}")
	public ResponseEntity<BaseResponse<PostResponse>> findOne(@PathVariable("id") int id,
			@RequestParam(name = "is_authorize", required = false, defaultValue = "0") int isAuthorize)
			throws Exception {

		BaseResponse<PostResponse> response = new BaseResponse<>();

		Posts post = postService.findOne(id);

		if (post == null) {
			response.setStatus(HttpStatus.BAD_REQUEST);
			response.setMessageError(StringErrorValue.POST_NOT_FOUND);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

		Users users = userService.findOne(post.getAuthorId());
		CategoryBlog categoryBlog = categoryBlogService.findOne(post.getCategoryBlogId());

		Rating rating = null;
		if (isAuthorize == 1) {
			rating = ratingService.findOneByUserAndPost(this.getUser().getId(), post.getId());
		}

		response.setData(new PostResponse(post, users, categoryBlog, rating));

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/category-blog")
	public ResponseEntity<BaseResponse<List<CategoryBlogResponse>>> findAllCategoryBlog(
			@RequestParam(name = "status", required = false, defaultValue = "-1") int status) throws Exception {

		BaseResponse<List<CategoryBlogResponse>> response = new BaseResponse<>();

		List<CategoryBlog> categoryBlogs = categoryBlogService.getAll(status);

		response.setData(new CategoryBlogResponse().mapToList(categoryBlogs));

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/category-course")
	public ResponseEntity<BaseResponse<BaseListDataResponse<CategoryCourseResponse>>> findAll(
			@RequestParam(name = "key_search", required = false, defaultValue = "") String keySearch,
			@RequestParam(name = "status", required = false, defaultValue = "-1") int status,
			@RequestParam(name = "page", required = false, defaultValue = "1") int page,
			@RequestParam(name = "limit", required = false, defaultValue = "10") int limit) throws Exception {
		BaseResponse<BaseListDataResponse<CategoryCourseResponse>> response = new BaseResponse<>();
		Pagination pagination = new Pagination(page, limit);
		StoreProcedureListResult<CategoryCourse> categoryCourses = categoryCourseService
				.spGListCategoryCourse(keySearch, status, pagination);

		BaseListDataResponse<CategoryCourseResponse> listData = new BaseListDataResponse<>();

		listData.setList(new CategoryCourseResponse().mapToList(categoryCourses.getResult()));
		listData.setTotalRecord(categoryCourses.getTotalRecord());

		response.setData(listData);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	
	@GetMapping("/banner")
	public ResponseEntity<BaseResponse<BaseListDataResponse<BannerResponse>>> findAll(
			@RequestParam(name = "status", required = false, defaultValue = "-1") int status,
			@RequestParam(name = "page", required = false, defaultValue = "1") int page,
			@RequestParam(name = "limit", required = false, defaultValue = "10") int limit) throws Exception {

		BaseResponse<BaseListDataResponse<BannerResponse>> response = new BaseResponse<>();

		StoreProcedureListResult<Banner> banners = bannerService.getAll(status, new Pagination(page, limit));
		BaseListDataResponse<BannerResponse> listData = new BaseListDataResponse<>();

		listData.setList(new BannerResponse().mapToList(banners.getResult()));
		listData.setTotalRecord(banners.getTotalRecord());
		listData.setLimit(limit);

		response.setData(listData);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}