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
@Table(name = "question")
public class Question extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String content;

	private String paragraph;

	@Column(name = "url_image")
	private String urlImage;

	@Column(name = "answer_a")
	private String answerA;

	@Column(name = "answer_b")
	private String answerB;

	@Column(name = "answer_c")
	private String answerC;

	@Column(name = "answer_d")
	private String answerD;

	private String result;

	@Column(name = "exam_detail_id")
	private int examDetailId;

	@Column(name = "exam_id")
	private int examId;

	private int sort;

}
