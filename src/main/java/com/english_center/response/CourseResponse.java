package com.english_center.response;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import com.english_center.common.utils.Utils;
import com.english_center.entity.Course;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class CourseResponse {
	private int id;

	private String name;

	private int lessons;

	private String description;

	private String content;

	private BigDecimal price;

	private String banner;

	@JsonProperty("count_chapter")
	private int countChapter;

	@JsonProperty("count_lessons")
	private int countLessons;

	@JsonProperty("is_free")
	private int isFree;

	@JsonProperty("category_course_id")
	private int categoryCourseId;

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

	public CourseResponse() {
	}

	public CourseResponse(Course entity) {
		this.id = entity.getId();
		this.name = entity.getName();
		this.lessons = entity.getLessons();
		this.description = entity.getDescription();
		this.content = entity.getContent();
		this.price = entity.getPrice();
		this.banner = entity.getBanner();
		this.status = entity.getStatus();
		this.duration = entity.getDuration();
		this.isFree = entity.getIsFree();
		this.categoryCourseId = entity.getCategoryCourseId();
		this.discountPercent = entity.getDiscountPercent();
	}

	public CourseResponse(Course entity, List<ChapterResponse> listChapter, int typeUserUsing, int countLessons) {
		this.id = entity.getId();
		this.name = entity.getName();
		this.lessons = entity.getLessons();
		this.description = entity.getDescription();
		this.content = entity.getContent();
		this.price = entity.getPrice();
		this.banner = entity.getBanner();
		this.status = entity.getStatus();
		this.isFree = entity.getIsFree();
		this.categoryCourseId = entity.getCategoryCourseId();
		this.discountPercent = entity.getDiscountPercent();
		this.listChapterResponses = listChapter;
		this.duration = entity.getDuration();
		this.durationFormat = Utils.formatMillisecondsToTime(entity.getDuration());
		this.typeUserUsing = typeUserUsing;
		this.countChapter = listChapter.size();
		this.countLessons = entity.getLessons();
	}

	public CourseResponse(Course entity, int typeUserUsing, int countLessons, int lessonsPresent) {
		this.id = entity.getId();
		this.name = entity.getName();
		this.lessons = entity.getLessons();
		this.description = entity.getDescription();
		this.content = entity.getContent();
		this.price = entity.getPrice();
		this.banner = entity.getBanner();
		this.status = entity.getStatus();
		this.categoryCourseId = entity.getCategoryCourseId();
		this.isFree = entity.getIsFree();
		this.discountPercent = entity.getDiscountPercent();
		this.duration = entity.getDuration();
		this.durationFormat = Utils.formatMillisecondsToTime(entity.getDuration());
		this.typeUserUsing = typeUserUsing;
		this.countLessons = countLessons;
		this.lessonsPresent = lessonsPresent;
	}

	public CourseResponse(Course entity, List<ChapterResponse> listChapter, int typeUserUsing, int countLessons,
			int lessonsPresent) {
		this.id = entity.getId();
		this.name = entity.getName();
		this.lessons = entity.getLessons();
		this.description = entity.getDescription();
		this.content = entity.getContent();
		this.price = entity.getPrice();
		this.banner = entity.getBanner();
		this.status = entity.getStatus();
		this.isFree = entity.getIsFree();
		this.categoryCourseId = entity.getCategoryCourseId();
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
		this.content = entity.getContent();
		this.price = entity.getPrice();
		this.banner = entity.getBanner();
		this.status = entity.getStatus();
		this.duration = entity.getDuration();
		this.isFree = entity.getIsFree();
		this.categoryCourseId = entity.getCategoryCourseId();
		this.discountPercent = entity.getDiscountPercent();
		this.typeUserUsing = typeUserUsing;
	}

	public List<CourseResponse> mapToList(List<Course> entities) {
		return entities.stream().map(x -> new CourseResponse(x)).collect(Collectors.toList());
	}

}
