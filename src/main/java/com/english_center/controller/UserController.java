package com.english_center.controller;

import java.util.Base64;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.english_center.common.utils.Pagination;
import com.english_center.common.utils.StringErrorValue;
import com.english_center.entity.Image;
import com.english_center.entity.Users;
import com.english_center.model.StoreProcedureListResult;
import com.english_center.request.CRUDUserRequest;
import com.english_center.request.ChangePasswordRequest;
import com.english_center.response.BaseListDataResponse;
import com.english_center.response.BaseResponse;
import com.english_center.response.ImageResponse;
import com.english_center.response.UserResponse;

@RestController
@RequestMapping("/api/v1/users")
public class UserController extends BaseController {
	

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@GetMapping("")
	public ResponseEntity<BaseResponse<BaseListDataResponse<UserResponse>>> GetAllUser(
			@RequestParam(name = "key_search", required = false, defaultValue = "") String keySearch,
			@RequestParam(name = "status", required = false, defaultValue = "-1") int status,
			@RequestParam(name = "role", required = false, defaultValue = "-1") int role,
			@RequestParam(name = "page", required = false, defaultValue = "0") int page,
			@RequestParam(name = "limit", required = false, defaultValue = "20") int limit) throws Exception {

		BaseResponse<BaseListDataResponse<UserResponse>> response = new BaseResponse();

		Pagination pagination = new Pagination(page, limit);
		StoreProcedureListResult<Users> listUser = userService.spGUsers(keySearch, status, role, pagination);

		BaseListDataResponse<UserResponse> listData = new BaseListDataResponse<>();

		listData.setList(new UserResponse().mapToList(listUser.getResult()));
		listData.setTotalRecord(listUser.getTotalRecord());

		response.setData(listData);

		response.setData(listData);
		return new ResponseEntity<>(response, HttpStatus.OK);

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@PostMapping("/update")
	public ResponseEntity<BaseResponse<UserResponse>> update(@RequestBody CRUDUserRequest wrapper) throws Exception {

		BaseResponse<UserResponse> response = new BaseResponse();

		Users user = this.getUser();

//		if (!user.getUserName().equals(wrapper.getUserName())) {
//			Users findUsersByName = userService.findUsersByUsersName(wrapper.getUserName());
//			if (findUsersByName != null) {
//				response.setStatus(HttpStatus.BAD_REQUEST);
//				response.setMessageError(StringErrorValue.NAME_USER_IS_EXIST);
//
//				return new ResponseEntity<>(response, HttpStatus.OK);
//
//			}
//		}

		if (!user.getEmail().equals(wrapper.getEmail())) {
			System.out.println(wrapper.getEmail());
			Users findUsersByEmail = userService.findUsersByEmail(wrapper.getEmail(), 0);
			if (findUsersByEmail != null) {
				response.setStatus(HttpStatus.BAD_REQUEST);
				response.setMessageError(StringErrorValue.MAIL_USER_IS_EXIST);
				return new ResponseEntity<>(response, HttpStatus.OK);
			}
		}

		if (!user.getPhone().equals(wrapper.getPhone())) {
			Users findUsersByPhone = userService.findUsersByPhone(wrapper.getPhone());
			if (findUsersByPhone != null) {
				response.setStatus(HttpStatus.BAD_REQUEST);
				response.setMessageError(StringErrorValue.PHONE_USER_IS_EXIST);
				return new ResponseEntity<>(response, HttpStatus.OK);
			}
		}

//		khi đổi username thì gọi phương thức login để update lại accessToken
//		if (!user.getUserName().equals(wrapper.getUserName())) {
//			String password = new String(Base64.getDecoder().decode(user.getPassword()));
//			String token = HttpService.login(wrapper.getUserName(), password);
//			user.setAccessToken(token);
//		}

//		user.setUserName(wrapper.getUserName());
		user.setFullName(wrapper.getFullName());
		user.setEmail(wrapper.getEmail());
//		user.setGender(wrapper.getGender());
		user.setPhone(wrapper.getPhone());
//		user.setBirthday(this.formatDate(wrapper.getBirthday()));
//		user.setCityId(wrapper.getCityId());
//		user.setDistrictId(wrapper.getDistrictId());
//		user.setWardId(wrapper.getWardId());
		user.setFullAddress(wrapper.getFullAddress());

		userService.update(user);

		response.setData(new UserResponse(user));
		return new ResponseEntity<>(response, HttpStatus.OK);

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@GetMapping("/detail")
	public ResponseEntity<BaseResponse<UserResponse>> findOne() throws Exception {

		BaseResponse<UserResponse> response = new BaseResponse();
		Users user = this.getUser();

		response.setData(new UserResponse(user));
		return new ResponseEntity<>(response, HttpStatus.OK);

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@PostMapping("/change-password")
	public ResponseEntity<BaseResponse<UserResponse>> chagePassword(@Valid @RequestBody ChangePasswordRequest wrapper)
			throws Exception {

		BaseResponse<UserResponse> response = new BaseResponse();

		Users users = this.getUser();
		String password = new String(Base64.getDecoder().decode(users.getPassword()));

		if (!wrapper.getOldPassword().equals(password)) {
			response.setStatus(HttpStatus.BAD_REQUEST);
			response.setMessageError(StringErrorValue.PASSWORD_IS_NOT_CORRECT);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

		if (!wrapper.getNewPassword().equals(wrapper.getConfirmPassword())) {
			response.setStatus(HttpStatus.BAD_REQUEST);
			response.setMessageError(StringErrorValue.ERROR_CONFIRM_PASSWORD_AND_CONFIRM);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

//		users.setPassword(BCrypt.hashpw(wrapper.getNewPassword(), BCrypt.gensalt(12)));
		users.setPassword(Base64.getEncoder().encodeToString(wrapper.getNewPassword().getBytes()));
		userService.update(users);
		response.setData(new UserResponse(users));
		return new ResponseEntity<>(response, HttpStatus.OK);

	}

	@SuppressWarnings("rawtypes")
	@PostMapping("/upload-avatar")
	public ResponseEntity<BaseResponse<ImageResponse>> create(@RequestParam(name = "file") MultipartFile file)
			throws Exception {

		BaseResponse<ImageResponse> response = new BaseResponse();
		Users user = this.getUser();
		String name = iFirebaseImageService.generateFileName(file.getOriginalFilename());

		String fileName = iFirebaseImageService.save(file);

		String imageUrl = iFirebaseImageService.getImageUrl(fileName);

		Image image = new Image();
		image.setUserId(user.getId());
		image.setUrl(imageUrl);

		imageService.create(image);
		user.setAvatarId(image.getId());
		user.setAvatarUrl(image.getUrl());

		userService.update(user);

		response.setData(new ImageResponse(image));

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
