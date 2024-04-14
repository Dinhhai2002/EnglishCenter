package com.english_center.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.english_center.entity.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Entity
@Data
public class PostModel extends BaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private String title;
	
	private String description;

	private String content;

	@Column(name = "author_id")
	private int authorId;

	@Column(name = "author_name")
	private String authorName;
	
	@Column(name = "author_avatar")
	private String authorAvatar;

	@Column(name = "category_blog_id")
	private int categoryBlogId;

	@Column(name = "category_blog_name")
	private String categoryBlogName;

	private String banner;

	private int status;
	
	private BigDecimal point;

	@Column(name = "count_rating")
	private long countRating;
}
