package com.english_center.response;

import java.util.List;
import java.util.stream.Collectors;

import com.english_center.entity.Level;

import lombok.Data;

@Data
public class LevelResponse {
	private int id;

	private String name;

	private String code;

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
