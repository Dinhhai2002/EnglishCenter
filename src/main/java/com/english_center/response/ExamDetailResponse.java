package com.english_center.response;

import java.util.List;
import java.util.stream.Collectors;

import com.english_center.entity.ExamDetail;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ExamDetailResponse {
	private int id;

	private String name;

	@JsonProperty("total_question ")
	private int totalQuestion;

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
