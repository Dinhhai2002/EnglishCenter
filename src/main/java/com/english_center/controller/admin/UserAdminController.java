package com.english_center.controller.admin;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.english_center.common.utils.Pagination;
import com.english_center.common.utils.StringErrorValue;
import com.english_center.controller.BaseController;
import com.english_center.entity.Users;
import com.english_center.model.StoreProcedureListResult;
import com.english_center.response.BaseListDataResponse;
import com.english_center.response.BaseResponse;
import com.english_center.response.UserResponse;

@RestController
@RequestMapping("/api/v1/admin/user")
public class UserAdminController extends BaseController {

	@GetMapping("")
	@PreAuthorize("hasAnyAuthority('ADMIN')")
	public ResponseEntity<BaseResponse<BaseListDataResponse<UserResponse>>> getAll(
			@RequestParam(name = "key_search", required = false, defaultValue = "") String keySearch,
			@RequestParam(name = "status", required = false, defaultValue = "-1") int status,
			@RequestParam(name = "role", required = false, defaultValue = "-1") int role,
			@RequestParam(name = "page", required = false, defaultValue = "1") int page,
			@RequestParam(name = "limit", required = false, defaultValue = "10") int limit) throws Exception {
		BaseResponse<BaseListDataResponse<UserResponse>> response = new BaseResponse<>();
		Pagination pagination = new Pagination(page, limit);
		StoreProcedureListResult<Users> listUser = userService.spGUsers(keySearch, status, role, pagination);

		BaseListDataResponse<UserResponse> listData = new BaseListDataResponse<>();

		listData.setList(new UserResponse().mapToList(listUser.getResult()));
		listData.setTotalRecord(listUser.getTotalRecord());

		response.setData(listData);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping("/{id}/change-status")
	@PreAuthorize("hasAnyAuthority('ADMIN')")
	public ResponseEntity<BaseResponse<UserResponse>> changeStatus(@PathVariable("id") int id) throws Exception {
		BaseResponse<UserResponse> response = new BaseResponse<>();
		Users currentUser = this.getUser();
		Users user = userService.findOne(id);

		if (user == null) {
			response.setStatus(HttpStatus.BAD_REQUEST);
			response.setMessageError(StringErrorValue.USER_NOT_FOUND);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

		if (user.getId() == currentUser.getId()) {
			response.setStatus(HttpStatus.BAD_REQUEST);
			response.setMessageError(StringErrorValue.USER_NOT_LOCK);
			return new ResponseEntity<>(response, HttpStatus.OK);

		}	

		user.setIsActive(user.getIsActive() == 1 ? 0 : 1);

		userService.update(user);
		response.setData(new UserResponse(user));
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
