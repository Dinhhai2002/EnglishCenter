package com.english_center.response;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import com.english_center.common.utils.Utils;
import com.english_center.entity.Course;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CourseResponse {
	private int id;

	private String name;

	private int lessons;

	private String description;

	private BigDecimal price;

	private String banner;

	@JsonProperty("count_chapter")
	private int countChapter;

	@JsonProperty("count_lessons")
	private int countLessons;

	@JsonProperty("is_free")
	private int isFree;

	@JsonProperty("discount_percent")
	private int discountPercent;

	private int status;

	private long duration;

	@JsonProperty("duration_format")
	private String durationFormat;

	@JsonProperty("type_user_using")
	private int typeUserUsing;

	@JsonProperty("lessons_present")
	private int lessonsPresent;

	private List<ChapterResponse> listChapterResponses;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getLessons() {
		return lessons;
	}

	public void setLessons(int lessons) {
		this.lessons = lessons;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getBanner() {
		return banner;
	}

	public void setBanner(String banner) {
		this.banner = banner;
	}

	public List<ChapterResponse> getListChapterResponses() {
		return listChapterResponses;
	}

	public void setListChapterResponses(List<ChapterResponse> listChapterResponses) {
		this.listChapterResponses = listChapterResponses;
	}

	public int getTypeUserUsing() {
		return typeUserUsing;
	}

	public void setTypeUserUsing(int typeUserUsing) {
		this.typeUserUsing = typeUserUsing;
	}

	public int getCountChapter() {
		return countChapter;
	}

	public void setCountChapter(int countChapter) {
		this.countChapter = countChapter;
	}

	public int getCountLessons() {
		return countLessons;
	}

	public void setCountLessons(int countLessons) {
		this.countLessons = countLessons;
	}

	public int getIsFree() {
		return isFree;
	}

	public void setIsFree(int isFree) {
		this.isFree = isFree;
	}

	public int getDiscountPercent() {
		return discountPercent;
	}

	public void setDiscountPercent(int discountPercent) {
		this.discountPercent = discountPercent;
	}

	public long getDuration() {
		return duration;
	}

	public void setDuration(long duration) {
		this.duration = duration;
	}

	public String getDurationFormat() {
		return durationFormat;
	}

	public void setDurationFormat(String durationFormat) {
		this.durationFormat = durationFormat;
	}

	public int getLessonsPresent() {
		return lessonsPresent;
	}

	public void setLessonsPresent(int lessonsPresent) {
		this.lessonsPresent = lessonsPresent;
	}

	public CourseResponse() {
	}

	public CourseResponse(Course entity) {
		this.id = entity.getId();
		this.name = entity.getName();
		this.lessons = entity.getLessons();
		this.description = entity.getDescription();
		this.price = entity.getPrice();
		this.banner = entity.getBanner();
		this.status = entity.getStatus();
		this.duration = entity.getDuration();
		this.isFree = entity.getIsFree();
		this.discountPercent = entity.getDiscountPercent();
	}

	public CourseResponse(Course entity, List<ChapterResponse> listChapter, int typeUserUsing, int countLessons) {
		this.id = entity.getId();
		this.name = entity.getName();
		this.lessons = entity.getLessons();
		this.description = entity.getDescription();
		this.price = entity.getPrice();
		this.banner = entity.getBanner();
		this.status = entity.getStatus();
		this.isFree = entity.getIsFree();
		this.discountPercent = entity.getDiscountPercent();
		this.listChapterResponses = listChapter;
		this.duration = entity.getDuration();
		this.durationFormat = Utils.formatMillisecondsToTime(entity.getDuration());
		this.typeUserUsing = typeUserUsing;
		this.countChapter = listChapter.size();
		this.countLessons = countLessons;
	}

	public CourseResponse(Course entity, List<ChapterResponse> listChapter, int typeUserUsing, int countLessons,
			int lessonsPresent) {
		this.id = entity.getId();
		this.name = entity.getName();
		this.lessons = entity.getLessons();
		this.description = entity.getDescription();
		this.price = entity.getPrice();
		this.banner = entity.getBanner();
		this.status = entity.getStatus();
		this.isFree = entity.getIsFree();
		this.discountPercent = entity.getDiscountPercent();
		this.listChapterResponses = listChapter;
		this.duration = entity.getDuration();
		this.durationFormat = Utils.formatMillisecondsToTime(entity.getDuration());
		this.typeUserUsing = typeUserUsing;
		this.countChapter = listChapter.size();
		this.countLessons = countLessons;
		this.lessonsPresent = lessonsPresent;
	}

	public CourseResponse(Course entity, int typeUserUsing) {
		this.id = entity.getId();
		this.name = entity.getName();
		this.lessons = entity.getLessons();
		this.description = entity.getDescription();
		this.price = entity.getPrice();
		this.banner = entity.getBanner();
		this.status = entity.getStatus();
		this.duration = entity.getDuration();
		this.isFree = entity.getIsFree();
		this.discountPercent = entity.getDiscountPercent();
		this.typeUserUsing = typeUserUsing;
	}

	public List<CourseResponse> mapToList(List<Course> entities) {
		return entities.stream().map(x -> new CourseResponse(x)).collect(Collectors.toList());
	}

}
