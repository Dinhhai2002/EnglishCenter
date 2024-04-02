package com.english_center.controller;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.english_center.common.enums.RoleEnum;
import com.english_center.common.enums.StatusEnum;
import com.english_center.common.utils.Pagination;
import com.english_center.common.utils.StringErrorValue;
import com.english_center.entity.Chapter;
import com.english_center.entity.Course;
import com.english_center.entity.Lessons;
import com.english_center.entity.Payment;
import com.english_center.entity.Point;
import com.english_center.entity.Promotion;
import com.english_center.entity.UserCourse;
import com.english_center.entity.UserCourseProgress;
import com.english_center.entity.Users;
import com.english_center.entity.VideoWatchHistory;
import com.english_center.model.StoreProcedureListResult;
import com.english_center.payment.vnpay.ConfigVnpay;
import com.english_center.request.PaymentRequest;
import com.english_center.response.BaseListDataResponse;
import com.english_center.response.BaseResponse;
import com.english_center.response.PaymentResponse;
import com.english_center.service.PointService;
import com.english_center.service.PromotionService;

@RestController()
@RequestMapping("/api/v1/payment")
public class PaymentController extends BaseController {

	@Autowired
	PointService pointService;

	@Autowired
	PromotionService promotionService;

	@GetMapping("")
	public ResponseEntity<BaseResponse<BaseListDataResponse<PaymentResponse>>> getAll(
			@RequestParam(name = "course_id", required = false, defaultValue = "-1") int courseId,
			@RequestParam(name = "user_id", required = false, defaultValue = "-1") int userId,
			@RequestParam(name = "is_pagination", required = false, defaultValue = "-1") int isPagination,
			@RequestParam(name = "page", required = false, defaultValue = "1") int page,
			@RequestParam(name = "limit", required = false, defaultValue = "10") int limit) throws Exception {
		BaseResponse<BaseListDataResponse<PaymentResponse>> response = new BaseResponse<>();
		Pagination pagination = new Pagination(page, limit);
		StoreProcedureListResult<Payment> listPayment = paymentService.spGPayment(courseId, userId, pagination,
				isPagination);

		List<Course> listCourse = courseService.findAll();
		List<Users> listUsers = userService.getAll();

		Map<Integer, Course> courseMap = new HashMap<>();
		for (Course course : listCourse) {
			courseMap.put(course.getId(), course);
		}

		Map<Integer, Users> userMap = new HashMap<>();
		for (Users user : listUsers) {
			userMap.put(user.getId(), user);
		}

		List<PaymentResponse> listPaymentResponses = listPayment.getResult().stream().map(x -> {
			Course course = courseMap.get(x.getCourseId());
			Users users = userMap.get(x.getStudentId());
			return new PaymentResponse(x, course, users);
		}).collect(Collectors.toList());

		BaseListDataResponse<PaymentResponse> listData = new BaseListDataResponse<>();

		listData.setList(listPaymentResponses);
		listData.setTotalRecord(listPayment.getTotalRecord());

		response.setData(listData);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@GetMapping("/{id}/course")
	public ResponseEntity<BaseResponse> getUrl(@PathVariable("id") int id, @RequestParam("amount") long amount)
			throws Exception {
		BaseResponse response = new BaseResponse();
		Users users = this.getUser();
		Course course = courseService.findOne(id);

		if (course == null) {
			response.setStatus(HttpStatus.BAD_REQUEST);
			response.setMessageError(StringErrorValue.COURSE_NOT_FOUND);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

		if (users.getRole() == RoleEnum.TEACHER.getValue() || users.getRole() == RoleEnum.ADMIN.getValue()) {
			response.setStatus(HttpStatus.BAD_REQUEST);
			response.setMessageError(StringErrorValue.USER_IS_NOT_ALLOW_REGISTER_COURSE);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

		String vnp_TxnRef = ConfigVnpay.getRandomNumber(8);
		String vnp_TmnCode = applicationProperties.getVnpTmnCode();

		String ReturnUrl = applicationProperties.getBaseUrlFe() + "/payment-success";

		Map<String, String> vnp_Params = new Hashtable<>();

		vnp_Params.put("vnp_Version", ConfigVnpay.vnp_Version);
		vnp_Params.put("vnp_Command", ConfigVnpay.vnp_Command);
		vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
		vnp_Params.put("vnp_Amount", String.valueOf(amount * 100));
		vnp_Params.put("vnp_CurrCode", "VND");
		vnp_Params.put("vnp_BankCode", "NCB");
		vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
		vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang:" + vnp_TxnRef);
		vnp_Params.put("vnp_OrderType", "other");
		vnp_Params.put("vnp_Locale", "vn");
		vnp_Params.put("vnp_ReturnUrl", ReturnUrl);
		vnp_Params.put("vnp_IpAddr", "13.160.92.202");

		Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		formatter.setTimeZone(TimeZone.getTimeZone("GMT+7"));
		String vnp_CreateDate = formatter.format(cld.getTime());
		vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

		cld.add(Calendar.MINUTE, 15);
		String vnp_ExpireDate = formatter.format(cld.getTime());
		vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

		List fieldNames = new ArrayList(vnp_Params.keySet());
		Collections.sort(fieldNames);
		StringBuilder hashData = new StringBuilder();
		StringBuilder query = new StringBuilder();
		Iterator itr = fieldNames.iterator();
		while (itr.hasNext()) {
			String fieldName = (String) itr.next();
			String fieldValue = (String) vnp_Params.get(fieldName);
			if ((fieldValue != null) && (fieldValue.length() > 0)) {
				// Build hash data
				hashData.append(fieldName);
				hashData.append('=');
				hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
				// Build query
				query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
				query.append('=');
				query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
				if (itr.hasNext()) {
					query.append('&');
					hashData.append('&');
				}
			}
		}
		String queryUrl = query.toString();
		String vnp_SecureHash = ConfigVnpay.hmacSHA512(applicationProperties.getVnpaySecretKey(), hashData.toString());
		queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
		String paymentUrl = applicationProperties.getVnpPayUrl() + "?" + queryUrl;

		response.setData(paymentUrl);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@SuppressWarnings("rawtypes")
	@PostMapping("/{id}/course")
	public ResponseEntity<BaseResponse> paymentCourse(@PathVariable("id") int id,
			@Valid @RequestBody PaymentRequest wrapper) throws Exception {
		BaseResponse response = new BaseResponse();
		Users users = this.getUser();
		Course course = courseService.findOne(id);

		if (course == null) {
			response.setStatus(HttpStatus.BAD_REQUEST);
			response.setMessageError(StringErrorValue.COURSE_NOT_FOUND);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

		UserCourse checkUserCourse = userCourseService
				.spGUserCourse(course.getId(), -1, users.getId(), 0, 0, new Pagination(0, 20), 0).getResult().stream()
				.findFirst().orElse(null);

		if (checkUserCourse != null) {
			response.setStatus(HttpStatus.BAD_REQUEST);
			response.setMessageError(StringErrorValue.COURSE_IS_REGISTED);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

		Promotion promotion = promotionService.findOne(wrapper.getPromotionId());

		if (promotion != null) {
			Point point = pointService.findOneByUserId(users.getId());
			if (point.getPoint() < promotion.getPoint()) {
				response.setStatus(HttpStatus.BAD_REQUEST);
				response.setMessageError(StringErrorValue.POINT_USER_NOT_PERMIT);
				return new ResponseEntity<>(response, HttpStatus.OK);
			}
			point.setPoint(point.getPoint() - promotion.getPoint());
			pointService.update(point);

		}

		Payment payment = new Payment();
		payment.setAmount(wrapper.getAmount());
		payment.setPaymentDate(new Date());
		payment.setCourseId(id);
		payment.setStudentId(users.getId());
		payment.setPaymentMethodId(0);
		payment.setStatus(StatusEnum.ACTIVE.getValue());

		paymentService.create(payment);

		UserCourse userCourse = new UserCourse();
		userCourse.setCourseId(id);
		userCourse.setIsExpired(0);
		userCourse.setType(0);
		userCourse.setIsJoinClass(1);
		userCourse.setStudentId(users.getId());

		userCourseService.create(userCourse);

		// sau khi đăng ký khóa học thì mở bài đầu tiên cho người dùng xem
		Chapter chapter = chapterDao.spGListChapter(course.getId(), "", 1, new Pagination(0, 20), 0, 1).getResult()
				.stream().findFirst().orElse(null);
		if (chapter != null) {
			Lessons lessons = lessonsDao
					.spGListLessons(course.getId(), chapter.getId(), "", 1, new Pagination(0, 20), 0, 1).getResult()
					.stream().findFirst().orElse(null);

			if (lessons != null) {
				UserCourseProgress userCourseProgress = new UserCourseProgress();
				userCourseProgress.setCourseId(lessons.getCourseId());
				userCourseProgress.setChapterId(lessons.getChapterId());
				userCourseProgress.setLessonsId(lessons.getId());
				userCourseProgress.setUserId(users.getId());

				userCourseProgressService.create(userCourseProgress);

				VideoWatchHistory videoWatchHistory = new VideoWatchHistory();
				videoWatchHistory.setUserId(users.getId());
				videoWatchHistory.setLessonsId(lessons.getId());

				videoWatchHistoryService.create(videoWatchHistory);
			}
		}

		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
