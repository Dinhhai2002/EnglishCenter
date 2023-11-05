package com.english_center.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public BigDecimal getTotalPoint() {
		return totalPoint;
	}

	public void setTotalPoint(BigDecimal totalPoint) {
		this.totalPoint = totalPoint;
	}

	public int getTotalQuestionCorrect() {
		return totalQuestionCorrect;
	}

	public void setTotalQuestionCorrect(int totalQuestionCorrect) {
		this.totalQuestionCorrect = totalQuestionCorrect;
	}
	
	
	public int getTotalQuestionSkip() {
		return totalQuestionSkip;
	}

	public void setTotalQuestionSkip(int totalQuestionSkip) {
		this.totalQuestionSkip = totalQuestionSkip;
	}

	public int getExamId() {
		return examId;
	}

	public void setExamId(int examId) {
		this.examId = examId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getTimeComplete() {
		return timeComplete;
	}

	public void setTimeComplete(String timeComplete) {
		this.timeComplete = timeComplete;
	}

	public String getNameExam() {
		return nameExam;
	}

	public void setNameExam(String nameExam) {
		this.nameExam = nameExam;
	}
	
	

}
