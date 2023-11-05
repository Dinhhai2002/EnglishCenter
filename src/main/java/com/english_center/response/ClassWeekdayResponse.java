package com.english_center.response;

import com.english_center.entity.ClassWeekday;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ClassWeekdayResponse {
	private int id;

	@JsonProperty("class_id")
	private int classId;

	@JsonProperty("hour_id")
	private int hourId;

	@JsonProperty("from_hour")
	private String fromHour;

	@JsonProperty("to_hour")
	private String toHour;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getClassId() {
		return classId;
	}

	public void setClassId(int classId) {
		this.classId = classId;
	}

	public int getHourId() {
		return hourId;
	}

	public void setHourId(int hourId) {
		this.hourId = hourId;
	}

	public String getFromHour() {
		return fromHour;
	}

	public void setFromHour(String fromHour) {
		this.fromHour = fromHour;
	}

	public String getToHour() {
		return toHour;
	}

	public void setToHour(String toHour) {
		this.toHour = toHour;
	}

	public ClassWeekdayResponse() {
	}

	public ClassWeekdayResponse(ClassWeekday entity) {
		this.id = entity.getId();
		this.classId = entity.getClassId();
		this.hourId = entity.getHourId();
		this.fromHour = entity.getFromHour();
		this.toHour = entity.getToHour();
	}

}
