package com.english_center.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.english_center.common.enums.StatusEnum;
import com.english_center.common.utils.Pagination;
import com.english_center.common.utils.StringErrorValue;
import com.english_center.entity.Promotion;
import com.english_center.model.StoreProcedureListResult;
import com.english_center.request.CRUDPromotionRequest;
import com.english_center.response.BaseListDataResponse;
import com.english_center.response.BaseResponse;
import com.english_center.response.PromotionResponse;
import com.english_center.service.PromotionService;

@RestController
@RequestMapping("/api/v1/promotion")
public class PromotionController extends BaseController {

	@Autowired
	PromotionService promotionService;

	public static final int MAX_NUMBER_PROMOTION = 5;

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
	public ResponseEntity<BaseResponse<BaseListDataResponse<PromotionResponse>>> findAll(
			@RequestParam(name = "key_search", required = false, defaultValue = "") String keySearch,
			@RequestParam(name = "status", required = false, defaultValue = "-1") int status,
			@RequestParam(name = "page", required = false, defaultValue = "1") int page,
			@RequestParam(name = "limit", required = false, defaultValue = "10") int limit) throws Exception {
		BaseResponse<BaseListDataResponse<PromotionResponse>> response = new BaseResponse<>();

		StoreProcedureListResult<Promotion> promotions = promotionService.spGPromotion(keySearch, status,
				new Pagination(page, limit));

		BaseListDataResponse<PromotionResponse> listData = new BaseListDataResponse<>();

		listData.setList(new PromotionResponse().mapToList(promotions.getResult()));
		listData.setTotalRecord(promotions.getTotalRecord());

		response.setData(listData);

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

	@SuppressWarnings("rawtypes")
	@PostMapping("/{id}/update")
	public ResponseEntity<BaseResponse> update(@Valid @RequestBody CRUDPromotionRequest wrapper,
			@PathVariable("id") int id) throws Exception {

		BaseResponse response = new BaseResponse();

		Promotion promotion = promotionService.findOne(id);

		if (promotion == null) {
			response.setStatus(HttpStatus.BAD_REQUEST);
			response.setMessageError(StringErrorValue.PROMOTION_NOT_FOUND);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
		promotion.setDescription(wrapper.getDescription());
		promotion.setPoint(wrapper.getPoint());
		promotion.setPromotionType(wrapper.getPromotionType());
		promotion.setPromotionValue(wrapper.getPromotionValue());

		promotionService.update(promotion);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping("/{id}/change-status")
	@PreAuthorize("hasAnyAuthority('ADMIN')")
	public ResponseEntity<BaseResponse<List<PromotionResponse>>> changeStatus(@PathVariable("id") int id)
			throws Exception {
		BaseResponse<List<PromotionResponse>> response = new BaseResponse<>();

		Promotion promotion = promotionService.findOne(id);

		if (promotion == null) {
			response.setStatus(HttpStatus.BAD_REQUEST);
			response.setMessageError(StringErrorValue.PROMOTION_NOT_FOUND);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

		StoreProcedureListResult<Promotion> promotions = promotionService.spGPromotion("", StatusEnum.ACTIVE.getValue(),
				new Pagination(0, 1000));
		System.out.println(promotions.getResult().size());
		if (promotion.getStatus() == StatusEnum.NOT_ACTIVE.getValue()
				&& promotions.getResult().size() >= MAX_NUMBER_PROMOTION) {
			response.setStatus(HttpStatus.BAD_REQUEST);
			response.setMessageError(StringErrorValue.NUMBER_PROMOTION_NOT_PERMIT);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

		promotion.setStatus(promotion.getStatus() == 1 ? 0 : 1);

		promotionService.update(promotion);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
