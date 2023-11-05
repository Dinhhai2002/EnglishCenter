package com.english_center.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "reply_comments")
public class ReplyComments extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String content;

	@Column(name = "user_id_reply_comments")
	private int userIdReplyComments;

	@Column(name = "comments_id")
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

}
