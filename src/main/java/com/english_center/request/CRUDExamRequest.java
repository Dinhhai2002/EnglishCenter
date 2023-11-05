package com.english_center.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CRUDExamRequest {
	@NotEmpty(message = "name không được phép để trống")
	@Length(max = 255, message = "Không được phép lớn hơn 255 kí tự")
	private String name;

	private String description;

	@Min(value = 1, message = "Giá trị nhỏ nhất là 1")
	@JsonProperty("category_exam_id")
	private int categoryExamId;

	@Min(value = 1, message = "Giá trị nhỏ nhất là 1")
	@JsonProperty("topic_id")
	private int topicId;

	@JsonProperty("time_minutes")
	private int timeMinutes;

	@JsonProperty("total_question")
	private int totalQuestion;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getCategoryExamId() {
		return categoryExamId;
	}

	public void setCategoryExamId(int categoryExamId) {
		this.categoryExamId = categoryExamId;
	}

	public int getTopicId() {
		return topicId;
	}

	public void setTopicId(int topicId) {
		this.topicId = topicId;
	}

	public int getTimeMinutes() {
		return timeMinutes;
	}

	public void setTimeMinutes(int timeMinutes) {
		this.timeMinutes = timeMinutes;
	}

	public int getTotalQuestion() {
		return totalQuestion;
	}

	public void setTotalQuestion(int totalQuestion) {
		this.totalQuestion = totalQuestion;
	}

}
