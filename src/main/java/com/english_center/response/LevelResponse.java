package com.english_center.response;

import java.util.List;
import java.util.stream.Collectors;

import com.english_center.entity.Level;

public class LevelResponse {
	private int id;

	private String name;

	private String code;

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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public LevelResponse() {

	}

	public LevelResponse(Level entity) {
		this.id = entity.getId();
		this.name = entity.getName();
		this.code = entity.getCode();
	}
	
	public List<LevelResponse> mapToList(List<Level> entities) {
		return entities.stream().map(x -> new LevelResponse(x)).collect(Collectors.toList());
	}

}
