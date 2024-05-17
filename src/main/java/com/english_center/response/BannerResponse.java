package com.english_center.response;

import java.util.List;
import java.util.stream.Collectors;

import com.english_center.entity.Banner;

import lombok.Data;

@Data
public class BannerResponse {
	private int id;

	private String url;

	private int status;

	public BannerResponse() {
	}

	public BannerResponse(Banner entity) {
		this.id = entity.getId();
		this.url = entity.getUrl();
		this.status = entity.getStatus();
	}

	public List<BannerResponse> mapToList(List<Banner> entities) {
		return entities.stream().map(x -> new BannerResponse(x)).collect(Collectors.toList());
	}
}
