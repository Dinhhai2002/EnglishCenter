package com.english_center.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CRUDTargetRequest {
	@JsonProperty("time_exam")
	@NotEmpty(message = "time_exam không được phép để trống")
	private String timeExam;

	@JsonProperty("point_target")
	@Min(value = 0, message = "giá trị nhỏ nhất là 0")
	private int pointTarget;

	public String getTimeExam() {
		return timeExam;
	}

	public void setTimeExam(String timeExam) {
		this.timeExam = timeExam;
	}

	public int getPointTarget() {
		return pointTarget;
	}

	public void setPointTarget(int pointTarget) {
		this.pointTarget = pointTarget;
	}

}
