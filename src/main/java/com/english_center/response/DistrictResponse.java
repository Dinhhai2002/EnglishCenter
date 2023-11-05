package com.english_center.response;

import java.util.List;
import java.util.stream.Collectors;

import com.english_center.entity.Districts;
import com.fasterxml.jackson.annotation.JsonProperty;

public class DistrictResponse {
	private int id;

	@JsonProperty("city_id")
	private int cityId;

	private String name;

	private String code;

	private int status;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCityId() {
		return cityId;
	}

	public void setCityId(int cityId) {
		this.cityId = cityId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public DistrictResponse() {
		
	}

	public DistrictResponse(Districts entity) {
		this.id = entity.getId();
		this.cityId = entity.getCityId();
		this.name = entity.getName();
		this.code = entity.getCode();
		this.status = entity.getStatus();
	}
	
	public List<DistrictResponse> mapToList(List<Districts> entities) {
		return entities.stream().map(x -> new DistrictResponse(x)).collect(Collectors.toList());
	}
	
	
}
