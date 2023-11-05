package com.english_center.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.english_center.entity.Weekday;
import com.english_center.response.BaseResponse;
import com.english_center.response.WeekdayReponse;
import com.english_center.service.WeekdayService;

@RestController
@RequestMapping("/api/v1/weekday")
public class WeekdayController {
	@Autowired
	WeekdayService weekdayService;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@GetMapping("")
	public ResponseEntity<BaseResponse<List<WeekdayReponse>>> getAll() throws Exception {

		BaseResponse<List<WeekdayReponse>> response = new BaseResponse();

		List<Weekday> listWeekdays = weekdayService.getAll();

		response.setData(new WeekdayReponse().mapToList(listWeekdays));

		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
