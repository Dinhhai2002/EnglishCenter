package com.english_center.response;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import com.english_center.entity.Result;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ResultResponse {
	private int id;

	@JsonProperty("total_point")
	private BigDecimal totalPoint ;
	
	@JsonProperty("total_question_correct")
	private int totalQuestionCorrect  ;
	
	@JsonProperty("total_question_skip")
	private int totalQuestionSkip  ;
	
	@JsonProperty("exam_id")
	private int examId  ;
	
	@JsonProperty("name_exam")
	private String nameExam  ;
	
	@JsonProperty("user_id")
	private int userId ;
	
	@JsonProperty("time_complete")
	private String timeComplete;
	
	@JsonProperty("created_at")
	private String createdAt;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public BigDecimal getTotalPoint() {
		return totalPoint;
	}

	public void setTotalPoint(BigDecimal totalPoint) {
		this.totalPoint = totalPoint;
	}

	public int getTotalQuestionCorrect() {
		return totalQuestionCorrect;
	}

	public void setTotalQuestionCorrect(int totalQuestionCorrect) {
		this.totalQuestionCorrect = totalQuestionCorrect;
	}

	public int getExamId() {
		return examId;
	}

	public void setExamId(int examId) {
		this.examId = examId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
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

	public String getNameExam() {
		return nameExam;
	}

	public void setNameExam(String nameExam) {
		this.nameExam = nameExam;
	}
	
	

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public ResultResponse() {
		
	}

	public ResultResponse(Result entity) {
		this.id = entity.getId();
		this.totalPoint = entity.getTotalPoint();
		this.totalQuestionCorrect = entity.getTotalQuestionCorrect();
		this.totalQuestionSkip=entity.getTotalQuestionSkip();
		this.examId = entity.getExamId();
		this.nameExam=entity.getNameExam();
		this.userId = entity.getUserId();
		this.timeComplete = entity.getTimeComplete();
		this.createdAt=entity.getDateFormatVN(entity.getCreatedAt());
	}
	
	public List<ResultResponse> mapToList(List<Result> entities) {
		return entities.stream().map(x -> new ResultResponse(x)).collect(Collectors.toList());
	}
	
	
	
	
	
	
}
