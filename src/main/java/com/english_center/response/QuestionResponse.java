package com.english_center.response;

import java.util.List;
import java.util.stream.Collectors;

import com.english_center.entity.Question;
import com.fasterxml.jackson.annotation.JsonProperty;

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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getAnswerA() {
		return answerA;
	}

	public void setAnswerA(String answerA) {
		this.answerA = answerA;
	}

	public String getAnswerB() {
		return answerB;
	}

	public void setAnswerB(String answerB) {
		this.answerB = answerB;
	}

	public String getAnswerC() {
		return answerC;
	}

	public void setAnswerC(String answerC) {
		this.answerC = answerC;
	}

	public String getAnswerD() {
		return answerD;
	}

	public void setAnswerD(String answerD) {
		this.answerD = answerD;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public int getExamDetailId() {
		return examDetailId;
	}

	public void setExamDetailId(int examDetailId) {
		this.examDetailId = examDetailId;
	}

	public int getExamId() {
		return examId;
	}

	public void setExamId(int examId) {
		this.examId = examId;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public String getParagraph() {
		return paragraph;
	}

	public void setParagraph(String paragraph) {
		this.paragraph = paragraph;
	}

	public String getUrlImage() {
		return urlImage;
	}

	public void setUrlImage(String urlImage) {
		this.urlImage = urlImage;
	}

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
