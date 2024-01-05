package com.english_center.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.english_center.response.BaseResponse;

@RestController
@RequestMapping("/api/v1/audio")
public class AudioController extends BaseController {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@GetMapping("/{id}")
	public ResponseEntity<BaseResponse> findOne(@PathVariable("id") int id) throws Exception {
		BaseResponse response = new BaseResponse();

		response.setData(audioService.findOne(id));

		return new ResponseEntity<>(response, HttpStatus.OK);
	}


}
