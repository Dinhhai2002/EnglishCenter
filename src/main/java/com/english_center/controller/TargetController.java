package com.english_center.controller;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.english_center.common.utils.StringErrorValue;
import com.english_center.common.utils.Utils;
import com.english_center.entity.Target;
import com.english_center.entity.Users;
import com.english_center.request.CRUDTargetRequest;
import com.english_center.response.BaseResponse;
import com.english_center.response.TargetResponse;
import com.english_center.service.TargetService;

@RestController
@RequestMapping("/api/v1/target")
public class TargetController extends BaseController {

	@Autowired
	TargetService targetService;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@GetMapping("/get-by-user")
	public ResponseEntity<BaseResponse> findByUserId() throws Exception {
		Users user = this.getUser();
		BaseResponse response = new BaseResponse();
		Target target = targetService.findByUserId(user.getId());
		if (target == null) {
			response.setStatus(HttpStatus.BAD_REQUEST);
			response.setMessageError(StringErrorValue.TARGET_NOT_FOUND);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
		response.setData(new TargetResponse(target));

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@PostMapping("/{id}/update")
	public ResponseEntity<BaseResponse> update(@PathVariable("id") int id,
			@Valid @RequestBody CRUDTargetRequest wrapper) throws Exception {
		BaseResponse response = new BaseResponse();

		Target target = targetService.findOne(id);

		if (target == null) {
			response.setStatus(HttpStatus.BAD_REQUEST);
			response.setMessageError(StringErrorValue.TARGET_NOT_FOUND);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

		target.setPointTarget(wrapper.getPointTarget());
		target.setTimeExam(Utils.convertStringToDate(wrapper.getTimeExam()));

		targetService.update(target);

		response.setData(new TargetResponse(target));

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@PostMapping("/create")
	public ResponseEntity<BaseResponse> create(@Valid @RequestBody CRUDTargetRequest wrapper) throws Exception {
		Users user = this.getUser();
		BaseResponse response = new BaseResponse();
		Date currentDate = new Date();

		long durationInMillis = Utils.convertStringToDate(wrapper.getTimeExam()).getTime() - currentDate.getTime();
		long daysBetween = TimeUnit.MILLISECONDS.toDays(durationInMillis);

		if (daysBetween < 0) {
			response.setStatus(HttpStatus.BAD_REQUEST);
			response.setMessageError(StringErrorValue.TIME_GREATER_CURRENT_DATE);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
		Target target = new Target();
		target.setUserId(user.getId());
		target.setPointTarget(wrapper.getPointTarget());
		target.setTimeExam(Utils.convertStringToDate(wrapper.getTimeExam()));

		targetService.create(target);

		response.setData(new TargetResponse(target));

		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
