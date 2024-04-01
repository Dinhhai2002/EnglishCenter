package com.english_center.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;

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

import com.english_center.common.utils.StringErrorValue;
import com.english_center.entity.Point;
import com.english_center.entity.Posts;
import com.english_center.entity.Rating;
import com.english_center.entity.Users;
import com.english_center.request.CRUDRatingRequest;
import com.english_center.response.BaseResponse;
import com.english_center.response.RatingResponse;
import com.english_center.service.PointService;
import com.english_center.service.PostService;
import com.english_center.service.RatingService;

@RestController
@RequestMapping("/api/v1/rating")
public class RatingController extends BaseController {
	@Autowired
	RatingService ratingService;

	@Autowired
	PostService postService;

	@Autowired
	PointService pointService;

	@GetMapping("/{id}")
	public ResponseEntity<BaseResponse<RatingResponse>> findOne(@PathVariable("id") int id) throws Exception {

		BaseResponse<RatingResponse> response = new BaseResponse<>();

		Rating rating = ratingService.findOne(id);

		if (rating == null) {
			response.setStatus(HttpStatus.BAD_REQUEST);
			response.setMessageError(StringErrorValue.RATING_NOT_FOUND);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
		response.setData(new RatingResponse(rating));

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@SuppressWarnings("rawtypes")
	@PostMapping("/create")
	public ResponseEntity<BaseResponse> create(@Valid @RequestBody CRUDRatingRequest wrapper) throws Exception {

		BaseResponse response = new BaseResponse<>();

		Users users = this.getUser();

		Posts posts = postService.findOne(wrapper.getPostId());

		if (posts == null) {
			response.setStatus(HttpStatus.BAD_REQUEST);
			response.setMessageError(StringErrorValue.POST_NOT_FOUND);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

		Rating rating = new Rating();
		rating.setPostId(wrapper.getPostId());
		rating.setPoint(wrapper.getPoint());
		rating.setUserId(users.getId());
		ratingService.create(rating);

		long count = posts.getCountRating();
		BigDecimal pointAVG = this.caculatePointAvg(posts.getPoint(), count, wrapper.getPoint());

		posts.setPoint(pointAVG.divide(new BigDecimal(count + 1), 2, RoundingMode.HALF_UP));
		posts.setCountRating(count + 1);
		postService.update(posts);

		Point point = pointService.findOneByUserId(users.getId());

		if (point == null) {
			point = new Point();
			point.setUserId(users.getId());
			point.setPoint(wrapper.getPoint());
			pointService.create(point);
		} else {
			point.setPoint(point.getPoint() + wrapper.getPoint());
			pointService.update(point);
		}

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@SuppressWarnings("rawtypes")
	@PostMapping("/{id}/update")
	public ResponseEntity<BaseResponse> update(@PathVariable("id") int id,
			@Valid @RequestBody CRUDRatingRequest wrapper) throws Exception {

		BaseResponse response = new BaseResponse<>();

		Users users = this.getUser();

		Posts posts = postService.findOne(wrapper.getPostId());

		if (posts == null) {
			response.setStatus(HttpStatus.BAD_REQUEST);
			response.setMessageError(StringErrorValue.POST_NOT_FOUND);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

		Rating rating = ratingService.findOne(id);
		if (rating == null) {
			response.setStatus(HttpStatus.BAD_REQUEST);
			response.setMessageError(StringErrorValue.RATING_NOT_FOUND);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

		int pointAfterUpdate = wrapper.getPoint() - rating.getPoint();

		/*
		 * pointAvgEnd = ((pointAVGCurrent * countRating) - pointCurrent + pointRequest)
		 * / countRating
		 */
		BigDecimal pointAVG = this.caculatePointAvg(posts.getPoint(), posts.getCountRating(), pointAfterUpdate);

		posts.setPoint(pointAVG.divide(new BigDecimal(posts.getCountRating()), 2, RoundingMode.HALF_UP));
		postService.update(posts);

		rating.setPoint(wrapper.getPoint());
		ratingService.update(rating);

		Point point = pointService.findOneByUserId(users.getId());

		point.setPoint(point.getPoint() + pointAfterUpdate);
		pointService.update(point);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	public BigDecimal caculatePointAvg(BigDecimal pointAvgCurrent, long countRating, int pointAfterUpdate) {
		return (pointAvgCurrent.multiply(new BigDecimal(countRating))).add(new BigDecimal(pointAfterUpdate));
	}

}
