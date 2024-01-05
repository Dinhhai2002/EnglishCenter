package com.english_center.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
@Entity
@Table(name = "payment")
public class Payment extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "payment_date")
	private Date paymentDate;

	private BigDecimal amount;

	@Column(name = "payment_method_id")
	private int paymentMethodId;

	private int status;

	@Column(name = "student_id")
	private int studentId;

	@Column(name = "course_id")
	private int courseId;

}
