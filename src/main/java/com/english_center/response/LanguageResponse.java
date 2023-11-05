//package com.english_center.response;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//import com.english_center.entity.Language;
//
//public class LanguageResponse {
//	private int id;
//
//	private String name;
//
//	public int getId() {
//		return id;
//	}
//
//	public void setId(int id) {
//		this.id = id;
//	}
//
//	public String getName() {
//		return name;
//	}
//
//	public void setName(String name) {
//		this.name = name;
//	}
//
//	public LanguageResponse() {
//	}
//
//	public LanguageResponse(Language entity) {
//		super();
//		this.id = entity.getId();
//		this.name = entity.getName();
//	}
//	
//	public List<LanguageResponse> mapToList(List<Language> entities) {
//		return entities.stream().map(x -> new LanguageResponse(x)).collect(Collectors.toList());
//	}
//
//
//}
