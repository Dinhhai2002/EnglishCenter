package com.english_center.response;

import com.english_center.entity.Target;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TargetResponse {
	private int id;

	@JsonProperty("user_id")
	private int userId;
	
	@JsonProperty("time_exam")
	private String timeExam ;
	
	@JsonProperty("point_target")
	private int pointTarget ;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

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
