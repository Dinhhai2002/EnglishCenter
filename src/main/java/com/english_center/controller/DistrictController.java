package com.english_center.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.english_center.entity.Districts;
import com.english_center.response.BaseResponse;
import com.english_center.service.DistrictService;

@RestController
@RequestMapping("/api/v1/district")
public class DistrictController extends BaseController {
	@Autowired
	DistrictService districtService;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@GetMapping("/{id}/by-city")
	public ResponseEntity<BaseResponse> findByWardId(@PathVariable("id") int id) throws Exception {

		BaseResponse response = new BaseResponse();

		List<Districts> districts = districtService.findByCityId(id);

		response.setData(districts);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
