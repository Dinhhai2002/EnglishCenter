package com.english_center.response;

import java.util.List;
import java.util.stream.Collectors;

import com.english_center.entity.CategoryExam;

import lombok.Data;

@Data
public class CategoryExamResponse {
	private int id;

	private String name;

	private String description;

	private int status;

	public CategoryExamResponse() {

	}

	public CategoryExamResponse(CategoryExam entity) {
		this.id = entity.getId();
		this.name = entity.getName();
		this.description = entity.getDescription();
		this.status = entity.getStatus();
	}

	public List<CategoryExamResponse> mapToList(List<CategoryExam> entities) {
		return entities.stream().map(x -> new CategoryExamResponse(x)).collect(Collectors.toList());
	}

}
