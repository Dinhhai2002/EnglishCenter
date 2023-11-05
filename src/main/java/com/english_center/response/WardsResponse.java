package com.english_center.response;

import java.util.List;
import java.util.stream.Collectors;

import com.english_center.entity.Wards;
import com.fasterxml.jackson.annotation.JsonProperty;

public class WardsResponse {
	private int id;

	@JsonProperty("district_id")
	private int districtId;

	private String name;

	private String code;

	private int status;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getDistrictId() {
		return districtId;
	}

	public void setDistrictId(int districtId) {
		this.districtId = districtId;
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

	public WardsResponse() {

	}

	public WardsResponse(Wards entity) {
		this.id = entity.getId();
		this.districtId = entity.getDistrictId();
		this.name = entity.getName();
		this.code = entity.getCode();
		this.status = entity.getStatus();
	}
	
	public List<WardsResponse> mapToList(List<Wards> entities) {
		return entities.stream().map(x -> new WardsResponse(x)).collect(Collectors.toList());
	}

}
