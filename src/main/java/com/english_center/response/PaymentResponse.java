package com.english_center.response;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import com.english_center.entity.Course;
import com.english_center.entity.Payment;
import com.english_center.entity.Users;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class PaymentResponse {
	private int id;

	@JsonProperty("payment_date")
	private String paymentDate;

	private BigDecimal amount;

	@JsonProperty("payment_method_id")
	private int paymentMethodId;

	private int status;

	@JsonProperty("student_id")
	private int studentId;

	@JsonProperty("student_name")
	private String studentName;

	@JsonProperty("course_id")
	private int courseId;

	@JsonProperty("course_name")
	private String courseName;

	public PaymentResponse() {
	}

	public PaymentResponse(Payment entity) {
		this.id = entity.getId();
		this.paymentDate = entity.getDatetimeFormatVN(entity.getPaymentDate());
		this.amount = entity.getAmount();
		this.paymentMethodId = entity.getPaymentMethodId();
		this.status = entity.getStatus();
		this.studentId = entity.getStudentId();
		this.courseId = entity.getCourseId();
	}

	public PaymentResponse(Payment entity, Course course, Users users) {
		this.id = entity.getId();
		this.paymentDate = entity.getDatetimeFormatVN(entity.getPaymentDate());
		this.amount = entity.getAmount();
		this.paymentMethodId = entity.getPaymentMethodId();
		this.status = entity.getStatus();
		this.studentId = entity.getStudentId();
		this.studentName = users.getFullName();
		this.courseId = entity.getCourseId();
		this.courseName = course != null ? course.getName() : "";
	}

	public List<PaymentResponse> mapToList(List<Payment> entities) {
		return entities.stream().map(x -> new PaymentResponse(x)).collect(Collectors.toList());
	}

}
