package com.english_center.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = false)
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "exam")
public class Exam extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String name;

	private String description;

	@Column(name = "category_exam_id")
	private int categoryExamId;

	@Column(name = "category_exam_name")
	private String categoryExamName;

	@Column(name = "topic_id")
	private int topicId;

	@Column(name = "topic_name")
	private String topicName;

	@Column(name = "time_minutes")
	private int timeMinutes;

	@Column(name = "total_question")
	private int totalQuestion;

	@Column(name = "audio_id")
	private int audioId;

	@Column(name = "url_audio")
	private String urlAudio;

	private int status;

}
