package com.english_center.response;

import java.util.List;
import java.util.stream.Collectors;

import com.english_center.entity.Weekday;

public class WeekdayReponse {
	private int id;

	private String name;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

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
