package com.english_center.response;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CountStatisticalResponse {

	
	@JsonProperty("count_user")
	private int countUser;

	@JsonProperty("count_exam")
	private int countExam;
	
	@JsonProperty("count_course")
	private int countCourse;
	
	@JsonProperty("total_amount")
	private BigDecimal totalAmount;

	public int getCountUser() {
		return countUser;
	}

	public void setCountUser(int countUser) {
		this.countUser = countUser;
	}

	public int getCountExam() {
		return countExam;
	}

	public void setCountExam(int countExam) {
		this.countExam = countExam;
	}

	public int getCountCourse() {
		return countCourse;
	}

	public void setCountCourse(int countCourse) {
		this.countCourse = countCourse;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

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
