package com.english_center.response;

import java.util.List;
import java.util.stream.Collectors;

import com.english_center.entity.Comments;
import com.english_center.entity.ReplyComments;
import com.english_center.entity.Users;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ReplyCommentsResponse {
	private int id;

	private String content;

	private UserResponse user;

	@JsonProperty("user_id_reply_comments ")
	private int userIdReplyComments;

	@JsonProperty("comments_id ")
	private int commentsId;

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

	public int getUserIdReplyComments() {
		return userIdReplyComments;
	}

	public void setUserIdReplyComments(int userIdReplyComments) {
		this.userIdReplyComments = userIdReplyComments;
	}

	public int getCommentsId() {
		return commentsId;
	}

	public void setCommentsId(int commentsId) {
		this.commentsId = commentsId;
	}

	public UserResponse getUser() {
		return user;
	}

	public void setUser(UserResponse user) {
		this.user = user;
	}

	public ReplyCommentsResponse() {
	}

	public ReplyCommentsResponse(ReplyComments entity) {
		this.id = entity.getId();
		this.content = entity.getContent();
		this.userIdReplyComments = entity.getUserIdReplyComments();
		this.commentsId = entity.getCommentsId();
	}

	public ReplyCommentsResponse(ReplyComments entity, UserResponse user) {
		this.id = entity.getId();
		this.content = entity.getContent();
//		this.userIdReplyComments = entity.getUserIdReplyComments();
		this.user = user;
		this.commentsId = entity.getCommentsId();
	}

	public List<ReplyCommentsResponse> mapToList(List<ReplyComments> entities,UserResponse user) {
		return entities.stream().map(x ->
		new ReplyCommentsResponse(x,user)).collect(Collectors.toList());
	}

}
