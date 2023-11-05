package com.english_center.model;

import java.util.Map;

public class ContentDetails {
	private String duration;
    private String dimension;
    private String definition;
    private boolean caption;
    private boolean licensedContent;
    private Map<String, String> contentRating;
    private String projection;
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public String getDimension() {
		return dimension;
	}
	public void setDimension(String dimension) {
		this.dimension = dimension;
	}
	public String getDefinition() {
		return definition;
	}
	public void setDefinition(String definition) {
		this.definition = definition;
	}
	public boolean isCaption() {
		return caption;
	}
	public void setCaption(boolean caption) {
		this.caption = caption;
	}
	public boolean isLicensedContent() {
		return licensedContent;
	}
	public void setLicensedContent(boolean licensedContent) {
		this.licensedContent = licensedContent;
	}
	public Map<String, String> getContentRating() {
		return contentRating;
	}
	public void setContentRating(Map<String, String> contentRating) {
		this.contentRating = contentRating;
	}
	public String getProjection() {
		return projection;
	}
	public void setProjection(String projection) {
		this.projection = projection;
	}
    
    
}
