package com.english_center.request;

import java.util.List;

import javax.validation.constraints.Min;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CRUDClassWeekdayRequest {
	
	@Min(value = 1, message = "giá trị nhỏ nhất là 1")
	@JsonProperty("class_id")
	private int classId;

	@JsonProperty("list_hour_id")
	private List<Integer> listHourId;
	
	

	public int getClassId() {
		return classId;
	}

	public void setClassId(int classId) {
		this.classId = classId;
	}

	public List<Integer> getListHourId() {
		return listHourId;
	}

	public void setListHourId(List<Integer> listHourId) {
		this.listHourId = listHourId;
	}

}
