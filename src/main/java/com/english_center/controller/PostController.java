package com.english_center.controller;

import java.math.BigDecimal;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.english_center.common.utils.Pagination;
import com.english_center.common.utils.StringErrorValue;
import com.english_center.entity.CategoryBlog;
import com.english_center.entity.Image;
import com.english_center.entity.Posts;
import com.english_center.entity.Rating;
import com.english_center.entity.Users;
import com.english_center.model.PostModel;
import com.english_center.model.StoreProcedureListResult;
import com.english_center.request.CRUDPostRequest;
import com.english_center.response.BaseListDataResponse;
import com.english_center.response.BaseResponse;
import com.english_center.response.ImageResponse;
import com.english_center.response.PostResponse;
import com.english_center.service.PostService;
import com.english_center.service.RatingService;

@RestController
@RequestMapping("/api/v1/post")
public class PostController extends BaseController {
	@Autowired
	PostService postService;

	@Autowired
	RatingService ratingService;

	@GetMapping("")
	public ResponseEntity<BaseResponse<BaseListDataResponse<PostResponse>>> getAll(
			@RequestParam(name = "category_blog_id", required = false, defaultValue = "-1") int categoryBlogId,
			@RequestParam(name = "key_search", required = false, defaultValue = "") String keySearch,
			@RequestParam(name = "status", required = false, defaultValue = "-1") int status,
			@RequestParam(name = "page", required = false, defaultValue = "1") int page,
			@RequestParam(name = "limit", required = false, defaultValue = "10") int limit) throws Exception {
		BaseResponse<BaseListDataResponse<PostResponse>> response = new BaseResponse<>();
		Pagination pagination = new Pagination(page, limit);
		StoreProcedureListResult<PostModel> listPost = postService.spGPosts(categoryBlogId, keySearch, status,
				pagination);

		BaseListDataResponse<PostResponse> listData = new BaseListDataResponse<>();

		listData.setList(new PostResponse().mapToListModel(listPost.getResult()));
		listData.setTotalRecord(listPost.getTotalRecord());
		listData.setLimit(limit);

		response.setData(listData);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<BaseResponse<PostResponse>> findOne(@PathVariable("id") int id) throws Exception {

		BaseResponse<PostResponse> response = new BaseResponse<>();

		Posts post = postService.findOne(id);

		if (post == null) {
			response.setStatus(HttpStatus.BAD_REQUEST);
			response.setMessageError(StringErrorValue.POST_NOT_FOUND);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

		Users users = userService.findOne(post.getAuthorId());
		CategoryBlog categoryBlog = categoryBlogService.findOne(post.getCategoryBlogId());

		Rating rating = ratingService.findOneByUserAndPost(this.getUser().getId(), post.getId());

		response.setData(new PostResponse(post, users, categoryBlog, rating));

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping("/create")
	public ResponseEntity<BaseResponse<PostResponse>> create(@Valid @RequestBody CRUDPostRequest wrapper)
			throws Exception {

		BaseResponse<PostResponse> response = new BaseResponse<>();

		Posts post = new Posts();
		post.setTitle(wrapper.getTitle());
		post.setDescription(wrapper.getDescription());
		post.setContent(wrapper.getContent());
		post.setStatus(wrapper.getStatus());
		post.setCategoryBlogId(wrapper.getCategoryBlogId());
		post.setAuthorId(this.getUser().getId());
		post.setPoint(BigDecimal.ZERO);

		postService.create(post);
		response.setData(new PostResponse(post));
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping("/{id}/upload-banner")
	public ResponseEntity<BaseResponse<ImageResponse>> create(@RequestParam(name = "file") MultipartFile file,
			@PathVariable("id") int id) throws Exception {

		BaseResponse<ImageResponse> response = new BaseResponse<>();
		Users user = this.getUser();
		Posts post = postService.findOne(id);

		if (post == null) {
			response.setStatus(HttpStatus.BAD_REQUEST);
			response.setMessageError(StringErrorValue.POST_NOT_FOUND);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

		String fileName = iFirebaseImageService.save(file);

		String imageUrl = iFirebaseImageService.getImageUrl(fileName);

		Image image = new Image();
		image.setUserId(user.getId());
		image.setUrl(imageUrl);

		imageService.create(image);
		post.setBanner(imageUrl);

		postService.update(post);

		response.setData(new ImageResponse(image));

		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
