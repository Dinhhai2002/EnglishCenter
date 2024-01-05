package com.english_center.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.english_center.common.utils.StringErrorValue;
import com.english_center.entity.Question;
import com.english_center.entity.ResultDetail;
import com.english_center.response.BaseResponse;
import com.english_center.response.ResultDetailResponse;

@RestController
@RequestMapping("/api/v1/result-detail")
public class ResultDetailController extends BaseController {
	@GetMapping("/{id}/detail")
	public ResponseEntity<BaseResponse<ResultDetailResponse>> findOne(@PathVariable("id") int id) throws Exception {

		BaseResponse<ResultDetailResponse> response = new BaseResponse<>();

		ResultDetail resultDetail = resultDetailService.findOne(id);

		if (resultDetail == null) {
			response.setStatus(HttpStatus.BAD_REQUEST);
			response.setMessageError(StringErrorValue.RESULTS_DETAIL_NOT_FOUND);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

		Question question = questionService.findOneBySortAndExamId(resultDetail.getSort(), resultDetail.getExamId());

		response.setData(new ResultDetailResponse(resultDetail, question));

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
