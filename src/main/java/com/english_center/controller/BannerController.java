package com.english_center.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.english_center.common.enums.StatusEnum;
import com.english_center.common.utils.Pagination;
import com.english_center.common.utils.StringErrorValue;
import com.english_center.entity.Banner;
import com.english_center.entity.Image;
import com.english_center.entity.Users;
import com.english_center.model.StoreProcedureListResult;
import com.english_center.response.BannerResponse;
import com.english_center.response.BaseListDataResponse;
import com.english_center.response.BaseResponse;
import com.english_center.service.BannerService;

@RestController
@RequestMapping("/api/v1/banner")
public class BannerController extends BaseController {
	@Autowired
	BannerService bannerService;

	@GetMapping("/{id}")
	public ResponseEntity<BaseResponse<BannerResponse>> findOne(@PathVariable("id") int id) throws Exception {

		BaseResponse<BannerResponse> response = new BaseResponse<>();

		Banner banner = bannerService.findOne(id);

		if (banner == null) {
			response.setStatus(HttpStatus.BAD_REQUEST);
			response.setMessageError(StringErrorValue.BANNER_NOT_FOUND);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
		response.setData(new BannerResponse(banner));

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("")
	public ResponseEntity<BaseResponse<BaseListDataResponse<BannerResponse>>> findAll(
			@RequestParam(name = "status", required = false, defaultValue = "-1") int status,
			@RequestParam(name = "page", required = false, defaultValue = "1") int page,
			@RequestParam(name = "limit", required = false, defaultValue = "10") int limit) throws Exception {

		BaseResponse<BaseListDataResponse<BannerResponse>> response = new BaseResponse<>();

		StoreProcedureListResult<Banner> banners = bannerService.getAll(status, new Pagination(page, limit));
		BaseListDataResponse<BannerResponse> listData = new BaseListDataResponse<>();

		listData.setList(new BannerResponse().mapToList(banners.getResult()));
		listData.setTotalRecord(banners.getTotalRecord());
		listData.setLimit(limit);

		response.setData(listData);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping("/create")
	public ResponseEntity<BaseResponse<BannerResponse>> create(@RequestParam(name = "file") MultipartFile file)
			throws Exception {

		BaseResponse<BannerResponse> response = new BaseResponse<>();
		Users user = this.getUser();

		String fileName = iFirebaseImageService.save(file);

		String imageUrl = iFirebaseImageService.getImageUrl(fileName);

		Image image = new Image();
		image.setUserId(user.getId());
		image.setUrl(imageUrl);

		imageService.create(image);

		Banner banner = new Banner();
		banner.setUrl(imageUrl);
		banner.setStatus(StatusEnum.ACTIVE.getValue());

		bannerService.create(banner);

		response.setData(new BannerResponse(banner));
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping("/{id}/update")
	public ResponseEntity<BaseResponse<BannerResponse>> create(@PathVariable("id") int id,
			@RequestParam(name = "file") MultipartFile file) throws Exception {

		BaseResponse<BannerResponse> response = new BaseResponse<>();
		Users user = this.getUser();

		Banner banner = bannerService.findOne(id);

		if (banner == null) {
			response.setStatus(HttpStatus.BAD_REQUEST);
			response.setMessageError(StringErrorValue.BANNER_NOT_FOUND);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

		String fileName = iFirebaseImageService.save(file);

		String imageUrl = iFirebaseImageService.getImageUrl(fileName);

		Image image = new Image();
		image.setUserId(user.getId());
		image.setUrl(imageUrl);

		imageService.create(image);

		banner.setUrl(imageUrl);

		bannerService.update(banner);

		response.setData(new BannerResponse(banner));
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping("/{id}/change-status")
	@PreAuthorize("hasAnyAuthority('ADMIN')")
	public ResponseEntity<BaseResponse<BannerResponse>> changeStatus(@PathVariable("id") int id) throws Exception {
		BaseResponse<BannerResponse> response = new BaseResponse<>();
		Banner banner = bannerService.findOne(id);

		if (banner == null) {
			response.setStatus(HttpStatus.BAD_REQUEST);
			response.setMessageError(StringErrorValue.BANNER_NOT_FOUND);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

		banner.setStatus(banner.getStatus() == 1 ? 0 : 1);

		bannerService.update(banner);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
