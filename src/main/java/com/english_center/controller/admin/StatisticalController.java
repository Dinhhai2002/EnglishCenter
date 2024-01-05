package com.english_center.controller.admin;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.english_center.controller.BaseController;
import com.english_center.response.BaseResponse;

@RestController
@RequestMapping("/api/v1/admin/statistical")
public class StatisticalController extends BaseController {

	@GetMapping("/amount")
	@PreAuthorize("hasAnyAuthority('ADMIN')")
	public ResponseEntity<BaseResponse<List<Object>>> amount(
			@RequestParam(name = "number_week", required = false, defaultValue = "") int numberWeek,
			@RequestParam(name = "from_date", required = false, defaultValue = "-1") String fromDate,
			@RequestParam(name = "to_date", required = false, defaultValue = "-1") String toDate,
			@RequestParam(name = "type", required = false, defaultValue = "1") int type) throws Exception {
		BaseResponse<List<Object>> response = new BaseResponse<>();

		List<Object> list = statisticalService.statisticalAmount(numberWeek, this.formatDate(fromDate),
				this.formatDate(toDate), type);

		response.setData(list);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/do-exam")
	@PreAuthorize("hasAnyAuthority('ADMIN')")
	public ResponseEntity<BaseResponse<List<Object>>> doExam(
			@RequestParam(name = "number_week", required = false, defaultValue = "") int numberWeek,
			@RequestParam(name = "from_date", required = false, defaultValue = "-1") String fromDate,
			@RequestParam(name = "to_date", required = false, defaultValue = "-1") String toDate,
			@RequestParam(name = "type", required = false, defaultValue = "1") int type) throws Exception {
		BaseResponse<List<Object>> response = new BaseResponse<>();

		List<Object> list = statisticalService.statisticalNumberUserDoExam(numberWeek, this.formatDate(fromDate),
				this.formatDate(toDate), type);

		response.setData(list);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
