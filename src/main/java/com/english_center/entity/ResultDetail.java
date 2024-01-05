package com.english_center.entity;

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
@Table(name = "result_detail")
public class ResultDetail extends BaseEntity {
	/**
	* 
	*/
	private static final long serialVersionUID = -7584573948437902295L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "result_id")
	private int resultId;

	@Column(name = "answer_correct")
	private String answerCorrect;

	@Column(name = "answer_user_choose")
	private String answerUserChoose;

	@Column(name = "exam_id")
	private int examId;

	@Column(name = "is_correct")
	private int isCorrect;

	@Column(name = "sort")
	private int sort;

	@Column(name = "is_answer")
	private int isAnswer;

}
