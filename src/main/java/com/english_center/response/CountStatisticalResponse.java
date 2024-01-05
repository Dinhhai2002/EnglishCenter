package com.english_center.response;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class CountStatisticalResponse {

	@JsonProperty("count_user")
	private int countUser;

	@JsonProperty("count_exam")
	private int countExam;

	@JsonProperty("count_course")
	private int countCourse;

	@JsonProperty("total_amount")
	private BigDecimal totalAmount;

	public CountStatisticalResponse() {
	}

	public CountStatisticalResponse(int countUser, int countExam, int countCourse, BigDecimal totalAmount) {
		super();
		this.countUser = countUser;
		this.countExam = countExam;
		this.countCourse = countCourse;
		this.totalAmount = totalAmount;
	}

}
