package com.english_center.response;

import java.util.List;
import java.util.stream.Collectors;

import com.english_center.entity.Exam;
import com.english_center.entity.Question;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
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
		this.urlAudio = entity.getUrlAudio();
		this.status = entity.getStatus();
	}

	public ExamResponse(Exam entity, int totalUser, int isQuestion, int totalComment, List<Question> listQuestion) {
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
		this.urlAudio = entity.getUrlAudio();
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
		this.urlAudio = entity.getUrlAudio();
		this.questions = new QuestionResponse().mapToList(listQuestion);
	}

	public List<ExamResponse> mapToList(List<Exam> entities) {
		return entities.stream().map(x -> new ExamResponse(x)).collect(Collectors.toList());
	}

}
