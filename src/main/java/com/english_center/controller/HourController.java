package com.english_center.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.english_center.common.utils.Pagination;
import com.english_center.entity.Hour;
import com.english_center.model.StoreProcedureListResult;
import com.english_center.response.BaseListDataResponse;
import com.english_center.response.BaseResponse;
import com.english_center.response.HourResponse;
import com.english_center.service.HourService;

@RestController
@RequestMapping("/api/v1/hour")
public class HourController {
	@Autowired
	HourService hourService;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@GetMapping("")
	public ResponseEntity<BaseResponse<BaseListDataResponse<HourResponse>>> getAll(
			@RequestParam(name = "weekday_id", required = false, defaultValue = "") int weekdayId,
			@RequestParam(name = "page", required = false, defaultValue = "1") int page,
			@RequestParam(name = "limit", required = false, defaultValue = "10") int limit) throws Exception {

		BaseResponse<BaseListDataResponse<HourResponse>> response = new BaseResponse();
		Pagination pagination = new Pagination(page, limit);
		StoreProcedureListResult<Hour> data = hourService.getAllHavePagination(weekdayId, pagination);

		BaseListDataResponse<HourResponse> listDataResponse = new BaseListDataResponse<>();
		listDataResponse.setList(new HourResponse().mapToList(data.getResult()));
		listDataResponse.setTotalRecord(data.getTotalRecord());

		response.setData(listDataResponse);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
