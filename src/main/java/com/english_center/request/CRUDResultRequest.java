package com.english_center.request;

import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CRUDResultRequest {

	@JsonProperty("exam_id")
	@Min(value = 1, message = "giá trị nhỏ nhất là 1")
	private int examId;

	@JsonProperty("list_answer")
	private List<AnswerForResultRequest> listAnswer;

	@JsonProperty("time_complete")
	@NotEmpty(message = "thời gian hoàn thành không được phép để trống")
	private String timeComplete;

	@JsonProperty("total_question_skip")
	@Min(value = 0, message = "giá trị nhỏ nhất là 0")
	private int totalQuestionSkip;

	public int getExamId() {
		return examId;
	}

	public void setExamId(int examId) {
		this.examId = examId;
	}

	public List<AnswerForResultRequest> getListAnswer() {
		return listAnswer;
	}

	public void setListAnswer(List<AnswerForResultRequest> listAnswer) {
		this.listAnswer = listAnswer;
	}

	public String getTimeComplete() {
		return timeComplete;
	}

	public void setTimeComplete(String timeComplete) {
		this.timeComplete = timeComplete;
	}

	public int getTotalQuestionSkip() {
		return totalQuestionSkip;
	}

	public void setTotalQuestionSkip(int totalQuestionSkip) {
		this.totalQuestionSkip = totalQuestionSkip;
	}

}
