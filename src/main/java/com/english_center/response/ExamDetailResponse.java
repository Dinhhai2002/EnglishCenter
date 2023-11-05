package com.english_center.response;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import com.english_center.entity.ExamDetail;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ExamDetailResponse {
	private int id;

	private String name;

	@JsonProperty("total_question ")
	private int totalQuestion;

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

	public int getTotalQuestion() {
		return totalQuestion;
	}

	public void setTotalQuestion(int totalQuestion) {
		this.totalQuestion = totalQuestion;
	}

	public ExamDetailResponse() {
	}

	public ExamDetailResponse(ExamDetail entity) {

		this.id = entity.getId();
		this.name = entity.getName();
		this.totalQuestion = entity.getTotalQuestion();
	}

	public List<ExamDetailResponse> mapToList(List<ExamDetail> entities) {
		return entities.stream().map(x -> new ExamDetailResponse(x)).collect(Collectors.toList());
	}

}
