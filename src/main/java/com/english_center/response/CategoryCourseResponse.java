package com.english_center.response;

import java.util.List;
import java.util.stream.Collectors;

import com.english_center.entity.CategoryCourse;

import lombok.Data;

@Data
public class CategoryCourseResponse {
	private int id;

	private String name;

	public CategoryCourseResponse() {
	}

	public CategoryCourseResponse(CategoryCourse entity) {
		super();
		this.id = entity.getId();
		this.name = entity.getName();
	}

	public List<CategoryCourseResponse> mapToList(List<CategoryCourse> entities) {
		return entities.stream().map(x -> new CategoryCourseResponse(x)).collect(Collectors.toList());
	}

}
