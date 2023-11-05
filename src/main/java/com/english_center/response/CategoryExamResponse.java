package com.english_center.response;

import java.util.List;
import java.util.stream.Collectors;

import com.english_center.entity.CategoryExam;

public class CategoryExamResponse {
	private int id;

	private String name;
	
	private String description;
	
	private int status;

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

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
