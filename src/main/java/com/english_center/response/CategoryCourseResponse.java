package com.english_center.response;

import java.util.List;
import java.util.stream.Collectors;

import com.english_center.entity.CategoryCourse;
import com.english_center.entity.Course;

public class CategoryCourseResponse {
	private int id;

	private String name;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

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
