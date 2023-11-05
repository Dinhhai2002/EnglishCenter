package com.english_center.request;

import java.math.BigDecimal;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CRUDClassRequest {
	
	@NotEmpty(message = "name không được phép để trống")
	@Length(max = 255, message = "Không được phép lớn hơn 255 kí tự")
	private String name;
	
	@NotEmpty(message = "startDate không được phép để trống")
	@JsonProperty("start_date")
	private String startDate;
	
	@NotEmpty(message = "endDate không được phép để trống")
	@JsonProperty("end_date")
	private String endDate;
	
	
//	@Min(value = 1, message = "giá trị nhỏ nhất là 1")
//	@JsonProperty("teacher_id")
//	private int teacherId;
	
	@Min(value = 1, message = "giá trị nhỏ nhất là 1")
	@JsonProperty("course_id")
	private int courseId;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}



//	public int getTeacherId() {
//		return teacherId;
//	}
//
//	public void setTeacherId(int teacherId) {
//		this.teacherId = teacherId;
//	}

	public int getCourseId() {
		return courseId;
	}

	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}
	
	
}
