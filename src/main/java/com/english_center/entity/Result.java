package com.english_center.entity;

import java.math.BigDecimal;

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
@Table(name="result")
public class Result extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "total_point")
	private BigDecimal totalPoint ;
	
	@Column(name="total_question_correct")
	private int totalQuestionCorrect  ;
	
	@Column(name="total_question_skip")
	private int totalQuestionSkip  ;
	
	@Column(name="exam_id")
	private int examId  ;
	
	@Column(name="name_exam")
	private String nameExam  ;
	
	@Column(name="user_id")
	private int userId ;
	
	@Column(name="time_complete")
	private String timeComplete;

	
	

}
