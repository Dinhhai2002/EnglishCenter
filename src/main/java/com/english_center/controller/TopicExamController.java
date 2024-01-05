package com.english_center.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.english_center.common.utils.StringErrorValue;
import com.english_center.entity.Exam;
import com.english_center.entity.TopicExam;
import com.english_center.request.CRUDTopicExamRequest;
import com.english_center.response.BaseResponse;
import com.english_center.response.ExamResponse;
import com.english_center.response.TopicExamReponse;

@RestController
@RequestMapping("/api/v1/topic-exam")
public class TopicExamController extends BaseController {
	

	@SuppressWarnings("rawtypes")
	@PostMapping("/create")
	public ResponseEntity<BaseResponse> create(@Valid @RequestBody CRUDTopicExamRequest wrapper) throws Exception {

		BaseResponse response = new BaseResponse();
		TopicExam topicExamCheck = topicExamService.findByName(wrapper.getName());

		if (topicExamCheck != null) {
			response.setStatus(HttpStatus.BAD_REQUEST);
			response.setMessageError(StringErrorValue.NAME_TOPIC_IS_EXIST);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

		TopicExam topicExam = new TopicExam();
		topicExam.setName(wrapper.getName());

		topicExamService.create(topicExam);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@GetMapping("")
	public ResponseEntity<BaseResponse<List<TopicExamReponse>>> getAll() throws Exception {

		BaseResponse<List<TopicExamReponse>> response = new BaseResponse();

		List<TopicExam> topicExams = topicExamService.getAll();

		response.setData(new TopicExamReponse().mapToList(topicExams));
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@GetMapping("/{id}/get-exam-by-topic")
	public ResponseEntity<BaseResponse<List<ExamResponse>>> getAllExamById(@PathVariable("id") int id)
			throws Exception {

		BaseResponse<List<ExamResponse>> response = new BaseResponse();

		List<Exam> exams = examService.findByTopicId(id);

		response.setData(new ExamResponse().mapToList(exams));

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

//	@SuppressWarnings({ "rawtypes", "unchecked" })
//	@GetMapping("/get-all-exam-by-all-topic")
//	public ResponseEntity<BaseResponse> getAllExamByAllTopic() throws Exception {
//
//		BaseResponse response = new BaseResponse();
//
//		List<Exam> exams = examService.spGListExam(-1, -1, "", -1, new Pagination(0, 20), 0).getResult();
//		List<TopicExam> topicExams = topicExamService.getAll();
//
//		List<TopicExamReponse> topicResponses = topicExams.stream().map(topicExam -> {
//			List<ExamResponse> filteredExams = exams.stream().filter(exam -> exam.getTopicId() == topicExam.getId())
//					.map(x -> {
//
//						// trả về tổng user của đề thi
//						int totaluser = 0;
//						try {
//							totaluser = this.countUserExam(x.getId());
//						} catch (Exception e) {
//							e.printStackTrace();
//						}
//
//						// kiểm tra đề thi đó đã có câu hỏi chưa
//						int isQuestion = 0;
//						List<Question> listQuestion = new ArrayList<>();
//						try {
//							listQuestion = questionService.getListByExamId(x.getId());
//						} catch (Exception e) {
//							e.printStackTrace();
//						}
//						if (!listQuestion.isEmpty()) {
//							isQuestion = 1;
//						}
//
//						// Tổng số lượng comments của đề thi
//						int countComments = 0;
//						try {
//							countComments = this.countComment(x.getId());
//						} catch (Exception e) {
//							e.printStackTrace();
//						}
//						return new ExamResponse(x, totaluser, isQuestion, countComments);
//					}).collect(Collectors.toList());
//
//			return new TopicExamReponse(topicExam, filteredExams);
//		}).collect(Collectors.toList());
//
//		response.setData(topicResponses);
//
//		return new ResponseEntity<>(response, HttpStatus.OK);
//	}
}
