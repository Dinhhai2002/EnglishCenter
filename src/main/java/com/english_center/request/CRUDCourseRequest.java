package com.english_center.request;

import java.math.BigDecimal;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class CRUDCourseRequest {
	@NotEmpty(message = "name không được phép để trống")
	@Length(max = 255, message = "Không được phép lớn hơn 255 kí tự")
	private String name;

	@NotEmpty(message = "description không được phép để trống")
	private String description;

	@Min(value = 0, message = "giá trị nhỏ nhất là 0")
	@JsonProperty("is_free")
	private int isFree;

	@Min(value = 0, message = "giá trị nhỏ nhất là 0")
	@Max(value = 100, message = "giá trị lớn nhất là 100")
	@JsonProperty("discount_percent")
	private int discountPercent;

	@Min(value = 0, message = "giá trị nhỏ nhất là 0")
	private BigDecimal price;

	

}
