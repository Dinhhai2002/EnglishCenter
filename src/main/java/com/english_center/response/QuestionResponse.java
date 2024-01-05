package com.english_center.response;

import java.util.List;
import java.util.stream.Collectors;

import com.english_center.entity.Question;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class QuestionResponse {
	private int id;

	private String content;

	private String paragraph;

	@JsonProperty("url_image")
	private String urlImage;

	@JsonProperty("answer_a")
	private String answerA;

	@JsonProperty("answer_b")
	private String answerB;

	@JsonProperty("answer_c")
	private String answerC;

	@JsonProperty("answer_d")
	private String answerD;

	private String result;

	@JsonProperty("exam_detail_id")
	private int examDetailId;

	@JsonProperty("exam_id")
	private int examId;

	private int sort;

	public QuestionResponse() {

	}

	public QuestionResponse(Question entity) {
		this.id = entity.getId();
		this.content = entity.getContent();
		this.paragraph = entity.getParagraph();
		this.urlImage = entity.getUrlImage();
		this.answerA = entity.getAnswerA();
		this.answerB = entity.getAnswerB();
		this.answerC = entity.getAnswerC();
		this.answerD = entity.getAnswerD();
		this.result = entity.getResult();
		this.examDetailId = entity.getExamDetailId();
		this.examId = entity.getExamId();
		this.sort = entity.getSort();
	}

	public List<QuestionResponse> mapToList(List<Question> entities) {
		return entities.stream().map(x -> new QuestionResponse(x)).collect(Collectors.toList());
	}

}
