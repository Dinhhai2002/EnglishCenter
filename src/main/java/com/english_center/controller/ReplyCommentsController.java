package com.english_center.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.english_center.common.utils.StringErrorValue;
import com.english_center.entity.Comments;
import com.english_center.entity.ReplyComments;
import com.english_center.entity.Users;
import com.english_center.request.CRUDReplyCommentsRequest;
import com.english_center.response.BaseResponse;
import com.english_center.response.ReplyCommentsResponse;

@RestController
@RequestMapping("/api/v1/reply-comments")
public class ReplyCommentsController extends BaseController {

	@SuppressWarnings("rawtypes")
	@PostMapping("/create")
	public ResponseEntity<BaseResponse> create(@Valid @RequestBody CRUDReplyCommentsRequest wrapper) throws Exception {

		BaseResponse response = new BaseResponse();

		Users userReplyComments = this.getUser();

		if (userReplyComments == null) {
			response.setStatus(HttpStatus.BAD_REQUEST);
			response.setMessageError(StringErrorValue.USER_NOT_FOUND);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

		Comments comments = commentsService.findOne(wrapper.getCommentsId());

		if (comments == null) {
			response.setStatus(HttpStatus.BAD_REQUEST);
			response.setMessageError(StringErrorValue.COMMENTS_NOT_FOUND);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

		ReplyComments replyComments = new ReplyComments();
		replyComments.setContent(wrapper.getContent());
		replyComments.setUserIdReplyComments(userReplyComments.getId());
		replyComments.setCommentsId(wrapper.getCommentsId());

		replyCommentsService.create(replyComments);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@SuppressWarnings("rawtypes")
	@PostMapping("/{id}/update")
	public ResponseEntity<BaseResponse> update(@PathVariable("id") int id,
			@Valid @RequestBody CRUDReplyCommentsRequest wrapper) throws Exception {

		BaseResponse response = new BaseResponse();
		ReplyComments replyComments = replyCommentsService.findOne(id);
		if (replyComments == null) {
			response.setStatus(HttpStatus.BAD_REQUEST);
			response.setMessageError(StringErrorValue.REPLYCOMMENTS_NOT_FOUND);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

		replyComments.setContent(wrapper.getContent());

		replyCommentsService.update(replyComments);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@SuppressWarnings("rawtypes")
	@PostMapping("/{id}/delete")
	public ResponseEntity<BaseResponse> update(@PathVariable("id") int id) throws Exception {

		BaseResponse response = new BaseResponse();
		ReplyComments replyComments = replyCommentsService.findOne(id);
		if (replyComments == null) {
			response.setStatus(HttpStatus.BAD_REQUEST);
			response.setMessageError(StringErrorValue.REPLYCOMMENTS_NOT_FOUND);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

		replyCommentsService.delete(replyComments);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<BaseResponse<ReplyCommentsResponse>> findOne(@PathVariable("id") int id) throws Exception {

		BaseResponse<ReplyCommentsResponse> response = new BaseResponse<>();

		ReplyComments replyComments = replyCommentsService.findOne(id);

		if (replyComments == null) {
			response.setStatus(HttpStatus.BAD_REQUEST);
			response.setMessageError(StringErrorValue.REPLYCOMMENTS_NOT_FOUND);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

		response.setData(new ReplyCommentsResponse(replyComments));

		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
