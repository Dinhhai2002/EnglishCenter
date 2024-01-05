package com.english_center.response;

import com.english_center.entity.ClassWeekday;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
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
