package com.english_center.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
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

	
}
