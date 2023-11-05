package com.english_center.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getCategoryExamId() {
		return categoryExamId;
	}

	public void setCategoryExamId(int categoryExamId) {
		this.categoryExamId = categoryExamId;
	}
	

	public String getCategoryExamName() {
		return categoryExamName;
	}

	public void setCategoryExamName(String categoryExamName) {
		this.categoryExamName = categoryExamName;
	}

	public String getTopicName() {
		return topicName;
	}

	public void setTopicName(String topicName) {
		this.topicName = topicName;
	}

	public int getTopicId() {
		return topicId;
	}

	public void setTopicId(int topicId) {
		this.topicId = topicId;
	}

	public int getTimeMinutes() {
		return timeMinutes;
	}

	public void setTimeMinutes(int timeMinutes) {
		this.timeMinutes = timeMinutes;
	}

	public int getTotalQuestion() {
		return totalQuestion;
	}

	public void setTotalQuestion(int totalQuestion) {
		this.totalQuestion = totalQuestion;
	}

	public int getAudioId() {
		return audioId;
	}

	public void setAudioId(int audioId) {
		this.audioId = audioId;
	}

	public String getUrlAudio() {
		return urlAudio;
	}

	public void setUrlAudio(String urlAudio) {
		this.urlAudio = urlAudio;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
	
	
	
}
