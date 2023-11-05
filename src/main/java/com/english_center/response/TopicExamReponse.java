package com.english_center.response;

import java.util.List;
import java.util.stream.Collectors;

import com.english_center.entity.TopicExam;

public class TopicExamReponse {
	private int id;

	private String name;

	private List<ExamResponse> listExam;

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

	public List<ExamResponse> getExamResponses() {
		return listExam;
	}

	public void setExamResponses(List<ExamResponse> listExam) {
		this.listExam = listExam;
	}

	public TopicExamReponse() {
	}

	public TopicExamReponse(TopicExam entity) {
		this.id = entity.getId();
		this.name = entity.getName();
	}

	public TopicExamReponse(TopicExam entity, List<ExamResponse> listExam) {
		this.id = entity.getId();
		this.name = entity.getName();
		this.listExam = listExam;
	}

	public List<TopicExamReponse> mapToList(List<TopicExam> entities) {
		return entities.stream().map(x -> new TopicExamReponse(x)).collect(Collectors.toList());
	}

	public List<TopicExamReponse> mapToListExam(List<TopicExam> entities, List<ExamResponse> listExam) {
		return entities.stream().map(x -> new TopicExamReponse(x, listExam)).collect(Collectors.toList());
	}

}
