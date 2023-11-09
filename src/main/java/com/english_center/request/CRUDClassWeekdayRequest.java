package com.english_center.request;

import java.util.List;

import javax.validation.constraints.Min;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class CRUDClassWeekdayRequest {
	
	@Min(value = 1, message = "giá trị nhỏ nhất là 1")
	@JsonProperty("class_id")
	private int classId;

	@JsonProperty("list_hour_id")
	private List<Integer> listHourId;
	
}
