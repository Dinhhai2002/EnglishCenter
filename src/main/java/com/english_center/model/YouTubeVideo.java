package com.english_center.model;

public class YouTubeVideo {
	private String kind;
    private String etag;
    private String id;
    private ContentDetails contentDetails;
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
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public ContentDetails getContentDetails() {
		return contentDetails;
	}
	public void setContentDetails(ContentDetails contentDetails) {
		this.contentDetails = contentDetails;
	}
    
    
}
