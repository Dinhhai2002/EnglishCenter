package com.english_center.response;

import java.util.List;
import java.util.stream.Collectors;

import com.english_center.entity.Cities;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CityResponse {
	private int id;

	@JsonProperty("country_id")
	private int countryId;

	private String name;

	private String code;

	private int status;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCountryId() {
		return countryId;
	}

	public void setCountryId(int countryId) {
		this.countryId = countryId;
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

	public CityResponse() {
		
	}

	public CityResponse(Cities entity) {
		this.id = entity.getId();
		this.countryId = entity.getCountryId();
		this.name = entity.getName();
		this.code = entity.getCode();
		this.status = entity.getStatus();
	}
	
	public List<CityResponse> mapToList(List<Cities> entities) {
		return entities.stream().map(x -> new CityResponse(x)).collect(Collectors.toList());
	}
	
	
}
