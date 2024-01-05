package com.english_center.response;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import com.english_center.entity.Result;
import com.english_center.entity.ResultDetail;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ResultResponse {
	private int id;

	@JsonProperty("total_point")
	private BigDecimal totalPoint;

	@JsonProperty("total_question_correct")
	private int totalQuestionCorrect;

	@JsonProperty("total_question_skip")
	private int totalQuestionSkip;

	@JsonProperty("exam_id")
	private int examId;

	@JsonProperty("name_exam")
	private String nameExam;

	@JsonProperty("user_id")
	private int userId;

	@JsonProperty("time_complete")
	private String timeComplete;

	@JsonProperty("created_at")
	private String createdAt;

	@JsonProperty("list_result_detail")
	private List<ResultDetailResponse> resultDetails;

	public ResultResponse() {

	}

	public ResultResponse(Result entity) {
		this.id = entity.getId();
		this.totalPoint = entity.getTotalPoint();
		this.totalQuestionCorrect = entity.getTotalQuestionCorrect();
		this.totalQuestionSkip = entity.getTotalQuestionSkip();
		this.examId = entity.getExamId();
		this.nameExam = entity.getNameExam();
		this.userId = entity.getUserId();
		this.timeComplete = entity.getTimeComplete();
		this.createdAt = entity.getDateFormatVN(entity.getCreatedAt());
	}

	public ResultResponse(Result entity, List<ResultDetail> resultDetails) {
		this.id = entity.getId();
		this.totalPoint = entity.getTotalPoint();
		this.totalQuestionCorrect = entity.getTotalQuestionCorrect();
		this.totalQuestionSkip = entity.getTotalQuestionSkip();
		this.examId = entity.getExamId();
		this.nameExam = entity.getNameExam();
		this.userId = entity.getUserId();
		this.timeComplete = entity.getTimeComplete();
		this.createdAt = entity.getDateFormatVN(entity.getCreatedAt());
		this.resultDetails = new ResultDetailResponse().mapToList(resultDetails);
	}

	public List<ResultResponse> mapToList(List<Result> entities) {
		return entities.stream().map(x -> new ResultResponse(x)).collect(Collectors.toList());
	}

}
