package com.english_center.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.english_center.common.utils.HttpService;
import com.english_center.common.utils.Pagination;
import com.english_center.common.utils.StringErrorValue;
import com.english_center.common.utils.Utils;
import com.english_center.entity.Chapter;
import com.english_center.entity.Cities;
import com.english_center.entity.Course;
import com.english_center.entity.Exam;
import com.english_center.entity.Lessons;
import com.english_center.entity.Question;
import com.english_center.entity.UserRegister;
import com.english_center.entity.Users;
import com.english_center.model.StoreProcedureListResult;
import com.english_center.request.CRUDUserRequest;
import com.english_center.request.ConfirmOtpRequest;
import com.english_center.request.GoogleAccountRequest;
import com.english_center.request.JwtRequest;
import com.english_center.request.OTPRegisterUserRequest;
import com.english_center.request.OTPRequest;
import com.english_center.request.ResetPasswordRequest;
import com.english_center.response.BaseListDataResponse;
import com.english_center.response.BaseResponse;
import com.english_center.response.ChapterResponse;
import com.english_center.response.CityResponse;
import com.english_center.response.CourseResponse;
import com.english_center.response.DistrictResponse;
import com.english_center.response.ExamResponse;
import com.english_center.response.JwtResponse;
import com.english_center.response.LessonsResponse;
import com.english_center.response.UserResponse;
import com.english_center.response.WardsResponse;
import com.english_center.security.JwtTokenUtil;
import com.english_center.service.ChapterService;
import com.english_center.service.CityService;
import com.english_center.service.CourseService;
import com.english_center.service.DistrictService;
import com.english_center.service.ExamService;
import com.english_center.service.JwtUserDetailsService;
import com.english_center.service.LessonsService;
import com.english_center.service.QuestionService;
import com.english_center.service.SendEmail;
import com.english_center.service.UserRegisterService;
import com.english_center.service.WardsService;

@RestController
@RequestMapping("/api/v1/authentication")
@CrossOrigin
public class JwtAuthenticationController extends BaseController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private JwtUserDetailsService userDetailsService;

	@Autowired
	private SendEmail sendEmail;

	@Autowired
	CityService cityService;

	@Autowired
	DistrictService districtService;

	@Autowired
	WardsService wardsService;

	@Autowired
	CourseService courseService;

	@Autowired
	QuestionService questionService;

	@Autowired
	ExamService examService;

	@Autowired
	ChapterService chapterService;

	@Autowired
	LessonsService lessonsService;

	@Autowired
	UserRegisterService userRegisterService;

	

	@Value("${password.account.google}")
	private String passwordAccountGoogle;

	@Value("${key.driver}")
	private String keyDriver;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PostMapping("/login")
	public ResponseEntity<BaseResponse> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest)
			throws Exception {
		BaseResponse response = new BaseResponse();
		authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
		Users user = userService.findUsersByUsersName(authenticationRequest.getUsername());
		UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

		if (user.getIsActive() == 0) {
			response.setStatus(HttpStatus.BAD_REQUEST);
			response.setMessageError(StringErrorValue.USER_IS_LOCKED);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

		String token = jwtTokenUtil.generateToken(userDetails);
		user.setAccessToken(token);
		user.setIsLogin(1);
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

		if (wrapper.getType() == 0) {

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

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@PostMapping("/login-google")
	public ResponseEntity<BaseResponse> loginGoogle(@Valid @RequestBody GoogleAccountRequest wrapper) throws Exception {

		BaseResponse response = new BaseResponse();
		Users registerUser = new Users();
		String token;
		Users users = userService.findUsersByEmail(wrapper.getEmail(), 1);

//		Nếu chưa có user thì tạo và gọi api login để set Token
		if (users == null) {
			registerUser.setEmail(wrapper.getEmail());
			registerUser.setUserName(wrapper.getEmail());
			registerUser.setAvatarUrl(wrapper.getImageUrl());
			registerUser.setIsGoogle(1);
			registerUser.setPassword(Utils.encodeBase64(passwordAccountGoogle));
			registerUser.setFullName(wrapper.getFullname());
			registerUser.setIsActive(1);
			userService.create(registerUser);

			token = HttpService.login(wrapper.getEmail(), passwordAccountGoogle);
			registerUser.setAccessToken(token);
			registerUser.setIsLogin(1);
			response.setData(new JwtResponse(token));
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

		token = HttpService.login(users.getUserName(), passwordAccountGoogle);
		registerUser.setIsLogin(1);
		response.setData(new JwtResponse(token));

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@GetMapping("/get-all-city")
	public ResponseEntity<BaseResponse<List<CityResponse>>> findAllCity() throws Exception {

		BaseResponse<List<CityResponse>> response = new BaseResponse();

		List<Cities> cities = cityService.getAll();

		response.setData(new CityResponse().mapToList(cities));

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@GetMapping("/{id}/get-district-by-city")
	public ResponseEntity<BaseResponse> findDistrictByCityId(@PathVariable("id") int id) throws Exception {

		BaseResponse response = new BaseResponse();

		response.setData(new DistrictResponse().mapToList(districtService.findByCityId(id)));

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@GetMapping("/{id}/get-ward-by-district")
	public ResponseEntity<BaseResponse> findWardByDistrictId(@PathVariable("id") int id) throws Exception {

		BaseResponse response = new BaseResponse();

		response.setData(new WardsResponse().mapToList(wardsService.findByDistrictId(id)));

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/list-course")
	public ResponseEntity<BaseResponse<BaseListDataResponse<CourseResponse>>> getAll(
			@RequestParam(name = "key_search", required = false, defaultValue = "") String keySearch,
			@RequestParam(name = "status", required = false, defaultValue = "-1") int status,
			@RequestParam(name = "is_pagination", required = false, defaultValue = "-1") int isPagination,
			@RequestParam(name = "page", required = false, defaultValue = "1") int page,
			@RequestParam(name = "limit", required = false, defaultValue = "10") int limit) throws Exception {
		BaseResponse<BaseListDataResponse<CourseResponse>> response = new BaseResponse<>();
		Pagination pagination = new Pagination(page, limit);
		StoreProcedureListResult<Course> listCourse = courseService.spGCourse(keySearch, status, 1, pagination);

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

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@GetMapping("/list-exam")
	public ResponseEntity<BaseResponse<BaseListDataResponse<ExamResponse>>> getAll(
			@RequestParam(name = "category_exam_id", required = false, defaultValue = "-1") int categoryExamId,
			@RequestParam(name = "topic_exam_id", required = false, defaultValue = "-1") int topicExamId,
			@RequestParam(name = "status", required = false, defaultValue = "-1") int status,
			@RequestParam(name = "key_search", required = false, defaultValue = "") String keySearch,
			@RequestParam(name = "page", required = false, defaultValue = "0") int page,
			@RequestParam(name = "limit", required = false, defaultValue = "20") int limit) throws Exception {

		BaseResponse<BaseListDataResponse<ExamResponse>> response = new BaseResponse();
		Pagination pagination = new Pagination(page, limit);

		StoreProcedureListResult<Exam> exams = examService.spGListExam(categoryExamId, topicExamId, keySearch, status,
				pagination, 1);

		List<ExamResponse> listExamResponses = exams.getResult().stream().map(x -> {
			int totaluser = 0;
			try {
				totaluser = this.countUserExam(x.getId());
			} catch (Exception e) {
				e.printStackTrace();
			}

			int isQuestion = 0;
			List<Question> listQuestion = new ArrayList<>();
			try {
				listQuestion = questionService.getListByExamId(x.getId());
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (!listQuestion.isEmpty()) {
				isQuestion = 1;
			}

			int countComments = 0;
			try {
				countComments = this.countComment(x.getId());
			} catch (Exception e) {
				e.printStackTrace();
			}
			return new ExamResponse(x, totaluser, isQuestion, countComments);
		}).collect(Collectors.toList());

		BaseListDataResponse<ExamResponse> listData = new BaseListDataResponse<>();
		listData.setList(listExamResponses);
		listData.setTotalRecord(exams.getTotalRecord());

		response.setData(listData);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@GetMapping("/exam/{id}/detail")
	public ResponseEntity<BaseResponse<ExamResponse>> findOne(@PathVariable("id") int id) throws Exception {

		BaseResponse<ExamResponse> response = new BaseResponse();

		Exam exam = examService.findOne(id);

		if (exam == null) {
			response.setStatus(HttpStatus.BAD_REQUEST);
			response.setMessageError(StringErrorValue.EXAM_NOT_FOUND);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

		List<Question> questions = questionService.getListByExamId(id);

		response.setData(new ExamResponse(exam, questions));

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

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@GetMapping("/course/{id}")
	public ResponseEntity<BaseResponse<CourseResponse>> findOneCourse(@PathVariable("id") int id) throws Exception {

		BaseResponse<CourseResponse> response = new BaseResponse();
		Course course = courseService.findOne(id);

		if (course == null) {
			response.setStatus(HttpStatus.BAD_REQUEST);
			response.setMessageError(StringErrorValue.COURSE_NOT_FOUND);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

		int[] countLessons = { 0 };
		List<Chapter> listChapter = chapterService.findByCourseId(id);

		List<ChapterResponse> list = listChapter.stream().map(x -> {
			List<Lessons> listLessons = new ArrayList<>();
			try {
				// danh sách bài học theo chương
				listLessons = lessonsService.spGListLessons(-1, x.getId(), "", 1, new Pagination(0, 20), 0).getResult();

			} catch (Exception e) {
				e.printStackTrace();
			}
//			List<LessonsResponse> lessonsResponses = listLessons.stream().map(lessons -> {
//
//				UserCourseProgress userCourseProgress = userCourseProgressService.findByLessonsAndUser(id, id)
//				
//				return new LessonsResponse(lessons, 1);
//			}).collect(Collectors.toList());

			countLessons[0] += listLessons.size();
			return new ChapterResponse(x, new LessonsResponse().mapToList(listLessons));
		}).collect(Collectors.toList());

		response.setData(new CourseResponse(course, list, 0, countLessons[0]));

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}
}