package com.english_center.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.english_center.entity.Wards;
import com.english_center.response.BaseResponse;
import com.english_center.response.WardsResponse;
import com.english_center.service.WardsService;

@RestController
@RequestMapping("/api/v1/wards")
public class WardsController {
	@Autowired
	WardsService wardsService;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@GetMapping("/{id}/by-district")
	public ResponseEntity<BaseResponse> findByWardId(@PathVariable("id") int id) throws Exception {

		BaseResponse response = new BaseResponse();

		List<Wards> wards = wardsService.findByDistrictId(id);

		response.setData(new WardsResponse().mapToList(wards));

		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
