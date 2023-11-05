package com.english_center.model;

import java.util.List;

import com.google.api.services.youtube.model.PageInfo;

public class YouTubeVideoListResponse {
	private String kind;
	private String etag;
	private List<YouTubeVideo> items;
	private PageInfo pageInfo;

	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

	public String getEtag() {
		return etag;
	}

	public void setEtag(String etag) {
		this.etag = etag;
	}

	public List<YouTubeVideo> getItems() {
		return items;
	}

	public void setItems(List<YouTubeVideo> items) {
		this.items = items;
	}

	public PageInfo getPageInfo() {
		return pageInfo;
	}

	public void setPageInfo(PageInfo pageInfo) {
		this.pageInfo = pageInfo;
	}

}
