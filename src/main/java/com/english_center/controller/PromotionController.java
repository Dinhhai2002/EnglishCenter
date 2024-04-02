package com.english_center.controller;

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
import java.util.List;

import com.english_center.common.enums.StatusEnum;
import com.english_center.common.utils.StringErrorValue;
import com.english_center.entity.Promotion;
import com.english_center.request.CRUDPromotionRequest;
import com.english_center.response.BaseResponse;
import com.english_center.response.PromotionResponse;
import com.english_center.service.PromotionService;

@RestController
@RequestMapping("/api/v1/promotion")
public class PromotionController extends BaseController {

	@Autowired
	PromotionService promotionService;

	@GetMapping("/{id}")
	public ResponseEntity<BaseResponse<PromotionResponse>> findOne(@PathVariable("id") int id) throws Exception {

		BaseResponse<PromotionResponse> response = new BaseResponse<>();

		Promotion promotion = promotionService.findOne(id);

		if (promotion == null) {
			response.setStatus(HttpStatus.BAD_REQUEST);
			response.setMessageError(StringErrorValue.PROMOTION_NOT_FOUND);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

		response.setData(new PromotionResponse(promotion));

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("")
	public ResponseEntity<BaseResponse<List<PromotionResponse>>> findAll() throws Exception {

		BaseResponse<List<PromotionResponse>> response = new BaseResponse<>();

		List<Promotion> promotions = promotionService.findAll();

		response.setData(new PromotionResponse().mapToList(promotions));

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping("/create")
	public ResponseEntity<BaseResponse<PromotionResponse>> create(@Valid @RequestBody CRUDPromotionRequest wrapper)
			throws Exception {

		BaseResponse<PromotionResponse> response = new BaseResponse<>();

		Promotion promotion = new Promotion();
		promotion.setPoint(wrapper.getPoint());
		promotion.setPromotionType(wrapper.getPromotionType());
		promotion.setPromotionValue(wrapper.getPromotionValue());
		promotion.setDescription(wrapper.getDescription());
		promotion.setStatus(StatusEnum.ACTIVE.getValue());

		promotionService.create(promotion);
		response.setData(new PromotionResponse(promotion));
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
