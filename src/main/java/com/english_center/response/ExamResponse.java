package com.english_center.response;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import com.english_center.entity.Exam;
import com.english_center.entity.Question;
import com.english_center.entity.TopicExam;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ExamResponse {
	private int id;

	private String name;

	private String description;

	@JsonProperty("category_exam_id")
	private int categoryExamId;

	@JsonProperty("category_exam_name")
	private String categoryExamName;

	@JsonProperty("topic_id")
	private int topicId;

	@JsonProperty("topic_name")
	private String topicName;

	@JsonProperty("time_minutes")
	private int timeMinutes;

	@JsonProperty("total_question")
	private int totalQuestion;

	@JsonProperty("audio_id")
	private int audioId;
	
	@JsonProperty("url_audio")
	private String urlAudio;

	private int status;

	@JsonProperty("is_question")
	private int isQuestion;

	@JsonProperty("questions")
	private List<QuestionResponse> questions;

	@JsonProperty("total_user")
	private int totalUser;

	@JsonProperty("total_comments")
	private int totalComments;

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

	public List<QuestionResponse> getQuestions() {
		return questions;
	}

	public void setQuestions(List<QuestionResponse> questions) {
		this.questions = questions;
	}

	public int getTotalUser() {
		return totalUser;
	}

	public void setTotalUser(int totalUser) {
		this.totalUser = totalUser;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getIsQuestion() {
		return isQuestion;
	}

	public void setIsQuestion(int isQuestion) {
		this.isQuestion = isQuestion;
	}

	public int getTotalComments() {
		return totalComments;
	}

	public void setTotalComments(int totalComments) {
		this.totalComments = totalComments;
	}
	
	

	public String getUrlAudio() {
		return urlAudio;
	}

	public void setUrlAudio(String urlAudio) {
		this.urlAudio = urlAudio;
	}

	public ExamResponse() {
	}

	public ExamResponse(Exam entity) {
		super();
		this.id = entity.getId();
		this.name = entity.getName();
		this.description = entity.getDescription();
		this.categoryExamId = entity.getCategoryExamId();
		this.categoryExamName = entity.getCategoryExamName();
		this.topicId = entity.getTopicId();
		this.topicName = entity.getTopicName();
		this.timeMinutes = entity.getTimeMinutes();
		this.totalQuestion = entity.getTotalQuestion();
		this.audioId = entity.getAudioId();
		this.status = entity.getStatus();
	}

	public ExamResponse(Exam entity, int totalUser, int isQuestion, int totalComment,List<Question> listQuestion) {
		super();
		this.id = entity.getId();
		this.name = entity.getName();
		this.description = entity.getDescription();
		this.categoryExamId = entity.getCategoryExamId();
		this.categoryExamName = entity.getCategoryExamName();
		this.topicId = entity.getTopicId();
		this.topicName = entity.getTopicName();
		this.timeMinutes = entity.getTimeMinutes();
		this.totalQuestion = entity.getTotalQuestion();
		this.audioId = entity.getAudioId();
		this.totalUser = totalUser;
		this.status = entity.getStatus();
		this.isQuestion = isQuestion;
		this.totalComments = totalComment;
		this.questions = new QuestionResponse().mapToList(listQuestion);
	}
	
	

	
	public ExamResponse(Exam entity, List<Question> listQuestion) {
		super();
		this.id = entity.getId();
		this.name = entity.getName();
		this.description = entity.getDescription();
		this.categoryExamId = entity.getCategoryExamId();
		this.categoryExamName = entity.getCategoryExamName();
		this.topicId = entity.getTopicId();
		this.topicName = entity.getTopicName();
		this.timeMinutes = entity.getTimeMinutes();
		this.totalQuestion = entity.getTotalQuestion();
		this.audioId = entity.getAudioId();
		this.urlAudio=entity.getUrlAudio();
		this.questions = new QuestionResponse().mapToList(listQuestion);
	}

	public List<ExamResponse> mapToList(List<Exam> entities) {
		return entities.stream().map(x -> new ExamResponse(x)).collect(Collectors.toList());
	}

}
