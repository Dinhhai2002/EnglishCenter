package com.english_center.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.english_center.common.utils.Utils;
import com.english_center.response.BaseResponse;
import com.english_center.service.CityService;

@RestController
@RequestMapping("/api/v1/city")
public class CityController extends BaseController {
	@Autowired
	CityService cityService;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@GetMapping("")
	public ResponseEntity<BaseResponse> findAll() throws IOException {

		BaseResponse response = new BaseResponse();
		String video = "https://www.youtube.com/watch?v=H2x5Y65SO9w";
		response.setData(Utils.extractVideoId(video));
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
