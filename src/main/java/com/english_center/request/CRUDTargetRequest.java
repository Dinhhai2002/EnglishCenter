package com.english_center.request;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class CRUDTargetRequest {
	@JsonProperty("time_exam")
	@NotEmpty(message = "time_exam không được phép để trống")
	private String timeExam;

	@JsonProperty("point_target")
	@Min(value = 0, message = "giá trị nhỏ nhất là 0")
	@Max(value = 990, message = "giá trị lớn nhất là 990")
	private int pointTarget;

}
