package com.english_center.response;

import java.util.List;
import java.util.stream.Collectors;

import com.english_center.entity.ReplyComments;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ReplyCommentsResponse {
	private int id;

	private String content;

	private UserResponse user;

	@JsonProperty("user_id_reply_comments ")
	private int userIdReplyComments;

	@JsonProperty("comments_id ")
	private int commentsId;

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

	public List<ReplyCommentsResponse> mapToList(List<ReplyComments> entities, UserResponse user) {
		return entities.stream().map(x -> new ReplyCommentsResponse(x, user)).collect(Collectors.toList());
	}

}
