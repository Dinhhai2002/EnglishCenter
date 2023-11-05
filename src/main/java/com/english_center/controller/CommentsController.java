package com.english_center.controller;

import java.util.List;
import java.util.stream.Collectors;

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

import com.english_center.common.utils.StringErrorValue;
import com.english_center.entity.Comments;
import com.english_center.entity.Exam;
import com.english_center.entity.ReplyComments;
import com.english_center.entity.Users;
import com.english_center.request.CRUDCommentsRequest;
import com.english_center.response.BaseResponse;
import com.english_center.response.CommentsResponse;
import com.english_center.response.ReplyCommentsResponse;
import com.english_center.response.UserResponse;
import com.english_center.service.ExamService;

@RestController
@RequestMapping("/api/v1/comments")
public class CommentsController extends BaseController {

	@Autowired
	ExamService examService;

	@SuppressWarnings("rawtypes")
	@PostMapping("/create")
	public ResponseEntity<BaseResponse> create(@Valid @RequestBody CRUDCommentsRequest wrapper) throws Exception {

		BaseResponse response = new BaseResponse();
		Users user = this.getUser();

		if (user == null) {
			response.setStatus(HttpStatus.BAD_REQUEST);
			response.setMessageError(StringErrorValue.USER_NOT_FOUND);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

		Exam exam = examService.findOne(wrapper.getExamId());

		if (exam == null) {
			response.setStatus(HttpStatus.BAD_REQUEST);
			response.setMessageError(StringErrorValue.EXAM_NOT_FOUND);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

		Comments comments = new Comments();
		comments.setContent(wrapper.getContent());
		comments.setExamId(wrapper.getExamId());
		comments.setUserId(user.getId());

		commentsService.create(comments);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@SuppressWarnings("rawtypes")
	@PostMapping("/{id}/update")
	public ResponseEntity<BaseResponse> update(@PathVariable("id") int id,
			@Valid @RequestBody CRUDCommentsRequest wrapper) throws Exception {

		BaseResponse response = new BaseResponse();
		Comments comments = commentsService.findOne(id);
		if (comments == null) {
			response.setStatus(HttpStatus.BAD_REQUEST);
			response.setMessageError(StringErrorValue.COMMENTS_NOT_FOUND);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

		comments.setContent(wrapper.getContent());

		commentsService.update(comments);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@SuppressWarnings("rawtypes")
	@PostMapping("/{id}/delete")
	public ResponseEntity<BaseResponse> delete(@PathVariable("id") int id) throws Exception {

		BaseResponse response = new BaseResponse();
		Comments comments = commentsService.findOne(id);
		if (comments == null) {
			response.setStatus(HttpStatus.BAD_REQUEST);
			response.setMessageError(StringErrorValue.USER_NOT_FOUND);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

		List<ReplyComments> listReplyComments = replyCommentsService.findByCommentId(id);
		for (ReplyComments item : listReplyComments) {
			replyCommentsService.delete(item);
		}

		commentsService.delete(comments);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@GetMapping("/{id}")
	public ResponseEntity<BaseResponse<CommentsResponse>> findOne(@PathVariable("id") int id) throws Exception {

		BaseResponse<CommentsResponse> response = new BaseResponse();

		Comments comments = commentsService.findOne(id);

		if (comments == null) {
			response.setStatus(HttpStatus.BAD_REQUEST);
			response.setMessageError(StringErrorValue.COMMENTS_NOT_FOUND);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

		response.setData(new CommentsResponse(comments));

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@GetMapping("/get-by-exam")
	public ResponseEntity<BaseResponse> findByExam(@RequestParam(value = "exam_id", required = true) int examId)
			throws Exception {

		BaseResponse response = new BaseResponse();

		List<Comments> comments = commentsService.findByExamId(examId);

		/*
		 * Map trả về list comment lồng replyComments
		 */
		response.setData(comments.stream().map(x -> {
			UserResponse user = null;
			try {
				user = new UserResponse(userService.findOne(x.getUserId()));
			} catch (Exception e) {
				e.printStackTrace();
			}
			List<ReplyComments> replyComments = null;
			try {
				replyComments = replyCommentsService.findByCommentId(x.getId());
			} catch (Exception e) {
				e.printStackTrace();
			}
			List<ReplyCommentsResponse> replyCommentsResponse = replyComments.stream().map(y -> {

				try {
					return new ReplyCommentsResponse(y,
							new UserResponse(userService.findOne(y.getUserIdReplyComments())));
				} catch (Exception e) {
					e.printStackTrace();
				}
				return null;
			}).collect(Collectors.toList());

			return new CommentsResponse(x, replyCommentsResponse, user);
		}).collect(Collectors.toList()));

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
