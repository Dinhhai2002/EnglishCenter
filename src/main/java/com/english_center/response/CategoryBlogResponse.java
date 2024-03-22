package com.english_center.response;

import java.util.List;
import java.util.stream.Collectors;

import com.english_center.entity.CategoryBlog;

import lombok.Data;

@Data
public class CategoryBlogResponse {
	private int id;

	private String name;

	private int status;

	public CategoryBlogResponse() {
	}

	public CategoryBlogResponse(CategoryBlog entity) {
		this.id = entity.getId();
		this.name = entity.getName();
		this.status = entity.getStatus();
	}

	public List<CategoryBlogResponse> mapToList(List<CategoryBlog> entities) {
		return entities.stream().map(x -> new CategoryBlogResponse(x)).collect(Collectors.toList());
	}

}
