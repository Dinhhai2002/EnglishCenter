package com.english_center.response;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import com.english_center.entity.CategoryBlog;
import com.english_center.entity.Posts;
import com.english_center.entity.Rating;
import com.english_center.entity.Users;
import com.english_center.model.PostModel;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class PostResponse {
	private int id;

	private String title;

	private String description;

	private String content;

	@JsonProperty("author_id")
	private int authorId;

	@JsonProperty("author_name")
	private String authorName;

	@JsonProperty("author_avatar")
	private String authorAvatar;

	@JsonProperty("category_blog_id")
	private int categoryBlogId;

	@JsonProperty("category_blog_name")
	private String categoryBlogName;

	private String banner;

	private int status;

	@JsonProperty("is_rating")
	private int isRating;

	private Rating rating;

	@JsonProperty("point_avg")
	private BigDecimal pointAvg;

	@JsonProperty("count_rating")
	private long countRating;

	@JsonProperty("created_at")
	private String createdAt;

	public PostResponse() {
	}

	public PostResponse(Posts entity) {
		this.id = entity.getId();
		this.title = entity.getTitle();
		this.description = entity.getDescription();
		this.content = entity.getContent();
		this.authorId = entity.getAuthorId();
		this.categoryBlogId = entity.getCategoryBlogId();
		this.banner = entity.getBanner();
		this.status = entity.getStatus();
	}

	public PostResponse(PostModel entity) {
		this.id = entity.getId();
		this.title = entity.getTitle();
		this.description = entity.getDescription();
		this.content = entity.getContent();
		this.authorId = entity.getAuthorId();
		this.authorName = entity.getAuthorName();
		this.authorAvatar = entity.getAuthorAvatar();
		this.categoryBlogId = entity.getCategoryBlogId();
		this.categoryBlogName = entity.getCategoryBlogName();
		this.banner = entity.getBanner();
		this.createdAt = entity.getDateFormatVN(entity.getCreatedAt());
		this.status = entity.getStatus();
		this.pointAvg = entity.getPoint();
		this.countRating = entity.getCountRating();
	}

	public PostResponse(Posts entity, Users users, CategoryBlog categoryBlog, Rating rating) {
		this.id = entity.getId();
		this.title = entity.getTitle();
		this.description = entity.getDescription();
		this.content = entity.getContent();
		this.authorId = entity.getAuthorId();
		this.authorName = users.getUserName();
		this.authorAvatar = users.getAvatarUrl();
		this.categoryBlogId = categoryBlog.getId();
		this.categoryBlogName = categoryBlog.getName();
		this.banner = entity.getBanner();
		this.createdAt = entity.getDateFormatVN(entity.getCreatedAt());
		this.status = entity.getStatus();
		this.isRating = rating == null ? 0 : 1;
		this.rating = rating;
		this.pointAvg = entity.getPoint();
		this.countRating = entity.getCountRating();
	}

	public List<PostResponse> mapToList(List<Posts> entities) {
		return entities.stream().map(x -> new PostResponse(x)).collect(Collectors.toList());
	}

	public List<PostResponse> mapToListModel(List<PostModel> entities) {
		return entities.stream().map(x -> new PostResponse(x)).collect(Collectors.toList());
	}

}
