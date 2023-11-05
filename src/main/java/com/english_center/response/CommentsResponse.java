package com.english_center.response;

import java.util.List;
import java.util.stream.Collectors;

import com.english_center.entity.Comments;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CommentsResponse {

	private int id;

	private String content;

	private UserResponse user;

	@JsonProperty("exam_id")
	private int examId;

	private List<ReplyCommentsResponse> replies;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public UserResponse getUser() {
		return user;
	}

	public void setUser(UserResponse user) {
		this.user = user;
	}

	public int getExamId() {
		return examId;
	}

	public void setExamId(int examId) {
		this.examId = examId;
	}

	public List<ReplyCommentsResponse> getReplies() {
		return replies;
	}

	public void setReplies(List<ReplyCommentsResponse> replies) {
		this.replies = replies;
	}

	public CommentsResponse() {
	}

	public CommentsResponse(Comments entity) {
		super();
		this.id = entity.getId();
		this.content = entity.getContent();
		this.examId = entity.getExamId();
	}

	public CommentsResponse(Comments entity, List<ReplyCommentsResponse> replyComments, UserResponse user) {
		super();
		this.id = entity.getId();
		this.content = entity.getContent();
		this.user = user;
		this.examId = entity.getExamId();
		this.replies = replyComments;
	}

	public List<CommentsResponse> mapToList(List<Comments> entities, List<ReplyCommentsResponse> replyComments,
			UserResponse user) {
		return entities.stream().map(x -> new CommentsResponse(x, replyComments, user)).collect(Collectors.toList());
	}

}
