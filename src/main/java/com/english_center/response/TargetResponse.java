package com.english_center.response;

import com.english_center.entity.Target;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class TargetResponse {
	private int id;

	@JsonProperty("user_id")
	private int userId;

	@JsonProperty("time_exam")
	private String timeExam;

	@JsonProperty("point_target")
	private int pointTarget;

	public TargetResponse() {

	}

	public TargetResponse(Target entity) {
		super();
		this.id = entity.getId();
		this.userId = entity.getUserId();
		this.timeExam = entity.getDateFormatVN(entity.getTimeExam());
		this.pointTarget = entity.getPointTarget();
	}

}
