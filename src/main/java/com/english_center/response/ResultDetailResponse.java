package com.english_center.response;

import java.util.List;
import java.util.stream.Collectors;

import com.english_center.entity.Question;
import com.english_center.entity.ResultDetail;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ResultDetailResponse {
	private int id;

	@JsonProperty("result_id")
	private int resultId;

	@JsonProperty("answer_correct")
	private String answerCorrect;

	@JsonProperty("answer_user_choose")
	private String answerUserChoose;

	@JsonProperty("exam_id")
	private int examId;

	@JsonProperty("is_correct")
	private int isCorrect;

	@JsonProperty("sort")
	private int sort;

	@JsonProperty("is_answer")
	private int isAnswer;

	@JsonProperty("answer_a")
	private String answerA;

	@JsonProperty("answer_b")
	private String answerB;

	@JsonProperty("answer_c")
	private String answerC;

	@JsonProperty("answer_d")
	private String answerD;

	@JsonProperty("exam_detail_id")
	private int examDetailId;

	private String content;

	private String paragraph;

	@JsonProperty("url_image")
	private String urlImage;

	public ResultDetailResponse() {
	}

	public ResultDetailResponse(ResultDetail entity) {
		this.id = entity.getId();
		this.resultId = entity.getResultId();
		this.answerCorrect = entity.getAnswerCorrect();
		this.answerUserChoose = entity.getAnswerUserChoose();
		this.examId = entity.getExamId();
		this.isCorrect = entity.getIsCorrect();
		this.sort = entity.getSort();
		this.isAnswer = entity.getIsAnswer();

	}

	public ResultDetailResponse(ResultDetail entity, Question question) {
		this.id = entity.getId();
		this.resultId = entity.getResultId();
		this.answerCorrect = entity.getAnswerCorrect();
		this.answerUserChoose = entity.getAnswerUserChoose();
		this.examId = entity.getExamId();
		this.isCorrect = entity.getIsCorrect();
		this.sort = entity.getSort();
		this.isAnswer = entity.getIsAnswer();
		this.answerA = question.getAnswerA();
		this.answerB = question.getAnswerB();
		this.answerC = question.getAnswerC();
		this.answerD = question.getAnswerD();
		this.examDetailId = question.getExamDetailId();
		this.content = question.getContent();
		this.paragraph = question.getParagraph();
		this.urlImage = question.getUrlImage();

	}

	public List<ResultDetailResponse> mapToList(List<ResultDetail> entities) {
		return entities.stream().map(x -> new ResultDetailResponse(x)).collect(Collectors.toList());
	}

}
