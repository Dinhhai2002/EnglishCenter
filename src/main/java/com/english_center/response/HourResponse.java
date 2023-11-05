package com.english_center.response;

import java.util.List;
import java.util.stream.Collectors;

import com.english_center.entity.Hour;
import com.fasterxml.jackson.annotation.JsonProperty;

public class HourResponse {
	private int id;

	@JsonProperty("weekday_id")
	private int weekdayId;

	@JsonProperty("time")
	private String time;

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

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public int getWeekdayId() {
		return weekdayId;
	}

	public void setWeekdayId(int weekdayId) {
		this.weekdayId = weekdayId;
	}

	public HourResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	public HourResponse(Hour entity) {
		this.id = entity.getId();
		this.fromHour = entity.getFromHour();
		this.toHour = entity.getToHour();
		this.time = entity.getTime();
		this.weekdayId = entity.getWeekdayId();
	}

	public List<HourResponse> mapToList(List<Hour> entities) {
		return entities.stream().map(x -> new HourResponse(x)).collect(Collectors.toList());
	}

}
