/**
 * 
 */
package com.english_center.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.WebRequest;

import com.english_center.common.enums.VideoTypeEnum;
import com.english_center.entity.Chapter;
import com.english_center.entity.Comments;
import com.english_center.entity.Lessons;
import com.english_center.entity.ReplyComments;
import com.english_center.entity.Result;
import com.english_center.entity.Users;
import com.english_center.model.YouTubeVideo;
import com.english_center.model.YouTubeVideoListResponse;
import com.english_center.response.BaseResponse;
import com.english_center.security.JwtTokenUtil;
import com.english_center.service.ClassStudentService;
import com.english_center.service.CommentsService;
import com.english_center.service.CourseService;
import com.english_center.service.IFirebaseImageService;
import com.english_center.service.ImageService;
import com.english_center.service.ReplyCommentsService;
import com.english_center.service.ResultService;
import com.english_center.service.UserCourseService;
import com.english_center.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.Permission;

@RestController
public class BaseController {

	@Autowired
	UserService userService;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	ClassStudentService classStudentService;

	@Autowired
	UserCourseService userCourseService;

	@Autowired
	private CourseService courseService;

	@Autowired
	ImageService imageService;

	@Autowired
	ResultService resultService;

	@Autowired
	IFirebaseImageService iFirebaseImageService;

	@Autowired
	CommentsService commentsService;

	@Autowired
	ReplyCommentsService replyCommentsService;

	@Autowired
	Drive googleDrive;

	@Autowired
	RestTemplate restTemplate;

	@Value("${key.youtube}")
	private String keyYoutube;

	// time 3 phút
	public static final long TIME_OTP_EXPIRED = 1000 * 60 * 3;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public final ResponseEntity<BaseResponse> handleUserNotFoundException(MethodArgumentNotValidException ex,
			WebRequest request) {

		BaseResponse response = new BaseResponse();
		response.setStatus(HttpStatus.BAD_REQUEST);
		response.setMessageError("Dữ liệu không hợp lệ");

		List<String> errors = ex.getBindingResult().getFieldErrors().stream()
				.map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList());

		response.setData(errors);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@ExceptionHandler(MissingServletRequestParameterException.class)
	public ResponseEntity<BaseResponse> handleMissingParams(MissingServletRequestParameterException ex) {
		// Actual exception handling
		BaseResponse response = new BaseResponse();
		response.setStatus(HttpStatus.BAD_REQUEST);
		response.setMessageError(String.format("%s is required!", ex.getParameterName()));
		response.setData(null);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	public String formatDate(String inputDate) throws ParseException {
		SimpleDateFormat inputDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date inputDate1 = inputDateFormat.parse(inputDate);

		SimpleDateFormat outputDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		return outputDateFormat.format(inputDate1);

	}

	public int countUserExam(int examId) throws Exception {
		List<Result> results = resultService.findByExamId(examId);

		List<Integer> listId = new ArrayList<>();
		results.stream().forEach(x -> {
			listId.add(x.getUserId());
		});

		Set<Integer> uniqueUserIds = new HashSet<>();

		listId.stream().forEach(uniqueUserIds::add);

		return uniqueUserIds.size();
	}

	public int countComment(int examId) throws Exception {

		List<Comments> comments = commentsService.findByExamId(examId);
		int count = 0;

		for (Comments x : comments) {
			List<ReplyComments> replyComments = new ArrayList<>();
			try {
				replyComments = replyCommentsService.findByCommentId(x.getId());
			} catch (Exception e) {
				e.printStackTrace();
			}
			count += replyComments.size();
		}

		count += comments.size();
		return count;
	}

	@SuppressWarnings("unused")
	public Users accessToken(String encodeString) throws Exception {

		byte[] decodedBytes = Base64.getMimeDecoder().decode(encodeString);

		String decodedMime = new String(decodedBytes);

		ObjectMapper mapper = new ObjectMapper();
		try {
			Users map = mapper.readValue(decodedMime, Users.class);

			Users user = userService.findUsersByUsersName(map.getUserName());

			if (user.getIsLogin() == 0 && user.getAccessToken() == "")
				throw new Exception("Tài khoản chưa đăng nhập");
			if (user != null)
				return user;
			else
				throw new Exception("Thất bại");
		} catch (Exception e) {
			throw new Exception("Thất bại");
		}

	}

	public long caculateOtpExpired(Date otpDate) {
		Date currentDate = new Date();
		return currentDate.getTime() - (otpDate.getTime() + TIME_OTP_EXPIRED);

	}

	public HttpServletRequest getRequest() {
		return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
	}

	public String getRequestHeaderAccessToken() {
		String authorizeHeader = this.getRequest().getHeader("Authorization");
		return authorizeHeader.replace("Bearer ", "");
	}

	@SuppressWarnings("unused")
	public Users getUser() throws Exception {

		String username = null;
		String jwtToken = null;

		jwtToken = this.getRequestHeaderAccessToken();
		username = jwtTokenUtil.getUsernameFromToken(jwtToken);

		Users user = userService.findUsersByUsersName(username);

		if (user.getIsLogin() == 0 && user.getAccessToken() == "")
			throw new Exception("Tài khoản chưa đăng nhập");
		if (user != null)
			return user;
		else
			throw new Exception("Thất bại");

	}

	// hàm permission cho upload driver
	public Permission getPermission() {
		Permission permission = new Permission();
		permission.setType("anyone");
		permission.setRole("reader");
		return permission;
	}

	public long calculateTimeVideoDriver(String idVideo) throws IOException {

		File file = googleDrive.files().get(idVideo)
				.setFields("id, name,mimeType, size, webViewLink, thumbnailLink, shared, videoMediaMetadata").execute();
		return file.getVideoMediaMetadata().getDurationMillis();
	}

	public long calculateTimeVideoYoutube(String idVideo) throws IOException {

		YouTubeVideoListResponse youtube = restTemplate.getForObject(
				String.format("https://www.googleapis.com/youtube/v3/videos?part=contentDetails&id=%s&key=%s", idVideo,
						keyYoutube),
				YouTubeVideoListResponse.class);
		String time = youtube.getItems().stream().findFirst().orElse(new YouTubeVideo()).getContentDetails()
				.getDuration();
		Duration duration = Duration.parse(time);
		return duration.toMillis();
	}

	public long calculateTimeVideo(String idVideo, int type) throws IOException {
		if (type == VideoTypeEnum.YOUTUBE.getValue()) {
			return calculateTimeVideoYoutube(idVideo);
		}

		return calculateTimeVideoDriver(idVideo);
	}

	public long calculateTimeListVideo(List<Lessons> listLessons) throws IOException {
		long totalTime = 0;
		for (Lessons item : listLessons) {
			if (item.getVideoType() == VideoTypeEnum.YOUTUBE.getValue()) {
				totalTime += calculateTimeVideoYoutube(item.getIdVideo());
			} else {
				totalTime += calculateTimeVideoDriver(item.getIdVideo());
			}
		}
		return totalTime;
	}

	public int caculateSortLessons(List<Lessons> listLessons) {
		if (listLessons.isEmpty()) {
			return 0;
		}
		return listLessons.get(listLessons.size() - 1).getSort();
	}

	public int caculateSortChapter(List<Chapter> listChapter) {
		if (listChapter.isEmpty()) {
			return 0;
		}
		return listChapter.get(listChapter.size() - 1).getSort();
	}

}
