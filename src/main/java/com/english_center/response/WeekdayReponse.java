package com.english_center.response;

import java.util.List;
import java.util.stream.Collectors;

import com.english_center.entity.Weekday;

import lombok.Data;

@Data
public class WeekdayReponse {
	private int id;

	private String name;

	public WeekdayReponse() {
	}

	public WeekdayReponse(Weekday weekday) {
		this.id = weekday.getId();
		this.name = weekday.getName();
	}

	public List<WeekdayReponse> mapToList(List<Weekday> entities) {
		return entities.stream().map(x -> new WeekdayReponse(x)).collect(Collectors.toList());
	}

}
