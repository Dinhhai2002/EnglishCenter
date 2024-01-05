package com.english_center.response;

import java.util.List;
import java.util.stream.Collectors;

import com.english_center.entity.Hour;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;


@Data
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

	public HourResponse() {
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
