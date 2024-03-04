package com.english_center.controller.admin;

import java.io.ByteArrayInputStream;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.english_center.common.utils.Pagination;
import com.english_center.common.utils.StringErrorValue;
import com.english_center.controller.BaseController;
import com.english_center.entity.Audio;
import com.english_center.entity.CategoryExam;
import com.english_center.entity.Exam;
import com.english_center.entity.Question;
import com.english_center.entity.TopicExam;
import com.english_center.model.StoreProcedureListResult;
import com.english_center.request.CRUDExamRequest;
import com.english_center.response.BaseResponse;
import com.english_center.response.ExamResponse;
import com.google.api.client.http.InputStreamContent;
import com.google.api.services.drive.model.File;

@RestController
@RequestMapping("/api/v1/admin/exam")
public class ExamAdminController extends BaseController {

	@GetMapping("/no-audio")
	@PreAuthorize("hasAnyAuthority('ADMIN')")
	public ResponseEntity<BaseResponse<List<ExamResponse>>> getAllNoAudio() throws Exception {
		BaseResponse<List<ExamResponse>> response = new BaseResponse<>();

		/*
		 * Lấy tất cả đề thi không phân trang
		 */
		StoreProcedureListResult<Exam> exams = examService.spGListExam(-1, -1, "", -1, new Pagination(0, 20), 0);
		List<Exam> listexamResult = exams.getResult().stream().filter(x -> (x.getAudioId() == 0))
				.collect(Collectors.toList());
		response.setData(new ExamResponse().mapToList(listexamResult));

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/no-question")
	@PreAuthorize("hasAnyAuthority('ADMIN')")
	public ResponseEntity<BaseResponse<List<ExamResponse>>> getAllNoQuestion() throws Exception {
		BaseResponse<List<ExamResponse>> response = new BaseResponse<>();
		/*
		 * Lấy tất cả đề thi không phân trang
		 */
		List<Exam> exams = examService.spGListExam(-1, -1, "", -1, new Pagination(0, 20), 0).getResult();
		List<Question> listQuestionAll = questionService.getAll();

		List<Exam> listexamResult = exams.stream().filter(x -> {
			List<Question> listQuestion = listQuestionAll.stream().filter(question -> question.getExamId() == x.getId())
					.collect(Collectors.toList());

			return listQuestion.isEmpty();
		}).collect(Collectors.toList());

		response.setData(new ExamResponse().mapToList(listexamResult));

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping("/{id}/change-status")
	@PreAuthorize("hasAnyAuthority('ADMIN')")
	public ResponseEntity<BaseResponse<ExamResponse>> changeStatus(@PathVariable("id") int id) throws Exception {
		BaseResponse<ExamResponse> response = new BaseResponse<>();
		Exam exam = examService.findOne(id);

		if (exam == null) {
			response.setStatus(HttpStatus.BAD_REQUEST);
			response.setMessageError(StringErrorValue.EXAM_NOT_FOUND);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

		if (exam.getStatus() == 0) {

			if (questionService.getListByExamId(exam.getId()).isEmpty()) {
				response.setStatus(HttpStatus.BAD_REQUEST);
				response.setMessageError(StringErrorValue.EXAM_IS_NOT_LIST_QUESTIONS);
				return new ResponseEntity<>(response, HttpStatus.OK);
			}

			if (exam.getAudioId() == 0) {
				response.setStatus(HttpStatus.BAD_REQUEST);
				response.setMessageError(StringErrorValue.EXAM_IS_NOT_AUDIO);
				return new ResponseEntity<>(response, HttpStatus.OK);
			}
		}

		exam.setStatus(exam.getStatus() == 1 ? 0 : 1);
		examService.update(exam);
		response.setData(new ExamResponse(exam));
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@PostMapping("/create")
	@PreAuthorize("hasAnyAuthority('ADMIN')")
	public ResponseEntity<BaseResponse> create(@Valid @RequestBody CRUDExamRequest wrapper) throws Exception {

		BaseResponse response = new BaseResponse();
		Exam checkExam = examService.findByName(wrapper.getName());

		if (checkExam != null) {
			response.setStatus(HttpStatus.BAD_REQUEST);
			response.setMessageError(StringErrorValue.NAME_EXAM_IS_EXIST);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

		CategoryExam categoryExam = categoryExamService.findOne(wrapper.getCategoryExamId());

		if (categoryExam == null) {
			response.setStatus(HttpStatus.BAD_REQUEST);
			response.setMessageError(StringErrorValue.CATEGORY_EXAM_NOT_FOUND);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

		TopicExam topicExam = topicExamService.findOne(wrapper.getTopicId());
		if (topicExam == null) {
			response.setStatus(HttpStatus.BAD_REQUEST);
			response.setMessageError(StringErrorValue.TOPIC_NOT_FOUND);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

		Exam exam = new Exam();

		exam.setName(wrapper.getName());
		exam.setDescription(wrapper.getDescription());
		exam.setCategoryExamId(wrapper.getCategoryExamId());
		exam.setCategoryExamName(categoryExam.getName());
		exam.setTopicId(wrapper.getTopicId());
		exam.setTopicName(topicExam.getName());
		exam.setTimeMinutes(wrapper.getTimeMinutes());
		exam.setTotalQuestion(wrapper.getTotalQuestion());

		examService.create(exam);

		response.setData(exam);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping("/{id}/update")
	@PreAuthorize("hasAnyAuthority('ADMIN')")
	public ResponseEntity<BaseResponse<ExamResponse>> update(@PathVariable("id") int id,
			@Valid @RequestBody CRUDExamRequest wrapper) throws Exception {

		BaseResponse<ExamResponse> response = new BaseResponse<>();
		Exam exam = examService.findOne(id);

		if (exam == null) {
			response.setStatus(HttpStatus.BAD_REQUEST);
			response.setMessageError(StringErrorValue.EXAM_NOT_FOUND);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

		// check name đã tồn tại hay chưa
		if (!exam.getName().equals(wrapper.getName()) && examService.findByName(wrapper.getName()) != null) {
			response.setStatus(HttpStatus.BAD_REQUEST);
			response.setMessageError(StringErrorValue.NAME_EXAM_IS_EXIST);
			return new ResponseEntity<>(response, HttpStatus.OK);

		}

		exam.setName(wrapper.getName());
		exam.setDescription(wrapper.getName());
		examService.update(exam);

		response.setData(new ExamResponse(exam));

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@SuppressWarnings("rawtypes")
	@PostMapping("/{id}/upload-audio")
	@PreAuthorize("hasAnyAuthority('ADMIN')")
	public ResponseEntity<BaseResponse> save(@PathVariable("id") int id,
			@RequestParam(name = "file") MultipartFile file) throws Exception {
		BaseResponse response = new BaseResponse();

		Exam exam = examService.findOne(id);
		if (exam == null) {
			response.setStatus(HttpStatus.BAD_REQUEST);
			response.setMessageError(StringErrorValue.EXAM_NOT_FOUND);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

		File fileMetadata = new File();
		fileMetadata.setParents(Collections.singletonList(applicationProperties.getFolderUpload()));
		String fileName = UUID.randomUUID().toString() + file.getOriginalFilename();
		fileMetadata.setName(fileName);
		File uploadFile = googleDrive.files()
				.create(fileMetadata,
						new InputStreamContent(file.getContentType(), new ByteArrayInputStream(file.getBytes())))
				.setFields("id, size, mimeType, webViewLink, videoMediaMetadata, webContentLink").execute();
		googleDrive.permissions().create(uploadFile.getId(), this.getPermission()).execute();

		Audio audio = new Audio();
		audio.setUrl(uploadFile.getId());
		audio.setExamId(id);
		audioService.create(audio);

		exam.setAudioId(audio.getId());
		exam.setUrlAudio(uploadFile.getId());

		examService.update(exam);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@SuppressWarnings({ "resource", "rawtypes" })
	@PostMapping("/{id}/upload-question")
	@PreAuthorize("hasAnyAuthority('ADMIN')")
	public ResponseEntity<BaseResponse> upload(@PathVariable("id") int id,
			@RequestParam(name = "file") MultipartFile file) throws Exception {
		BaseResponse response = new BaseResponse();
		Exam exam = examService.findOne(id);
		if (exam == null) {
			response.setStatus(HttpStatus.BAD_REQUEST);
			response.setMessageError(StringErrorValue.EXAM_NOT_FOUND);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

		Workbook workbook = new XSSFWorkbook(file.getInputStream());

		Sheet sheet = workbook.getSheetAt(0);

		for (int i = 1; i <= sheet.getLastRowNum(); i++) {
			Row row = sheet.getRow(i);
			String content = "";
			String paragraph = "";
			String urlImage = "";
			String answerA = "";
			String answerB = "";
			String answerC = "";
			String answerD = "";
			String result = "";
			int examDetailId = 0;
			int sort = 0;

			if (row.getCell(0) != null && row.getCell(0).getCellType() == CellType.STRING) {
				content = row.getCell(0).getStringCellValue();
			}
			if (row.getCell(1) != null && row.getCell(1).getCellType() == CellType.STRING) {
				paragraph = row.getCell(1).getStringCellValue();
			}
			if (row.getCell(2) != null && row.getCell(2).getCellType() == CellType.STRING) {
				urlImage = row.getCell(2).getStringCellValue();
			}
			if (row.getCell(3) != null && row.getCell(3).getCellType() == CellType.STRING) {
				answerA = row.getCell(3).getStringCellValue();
			}
			if (row.getCell(4) != null && row.getCell(4).getCellType() == CellType.STRING) {
				answerB = row.getCell(4).getStringCellValue();
			}
			if (row.getCell(5) != null && row.getCell(5).getCellType() == CellType.STRING) {
				answerC = row.getCell(5).getStringCellValue();
			}
			if (row.getCell(6) != null && row.getCell(6).getCellType() == CellType.STRING) {
				answerD = row.getCell(6).getStringCellValue();
			}
			if (row.getCell(7) != null && row.getCell(7).getCellType() == CellType.STRING) {
				result = row.getCell(7).getStringCellValue();
			}
			if (row.getCell(8) != null && row.getCell(8).getCellType() == CellType.NUMERIC) {
				examDetailId = (int) row.getCell(8).getNumericCellValue();
			}
			if (row.getCell(9) != null && row.getCell(9).getCellType() == CellType.NUMERIC) {
				sort = (int) row.getCell(9).getNumericCellValue();
			}

			Question question = new Question();
			question.setContent(content);
			question.setParagraph(paragraph);
			question.setUrlImage(urlImage);
			question.setAnswerA(answerA);
			question.setAnswerB(answerB);
			question.setAnswerC(answerC);
			question.setAnswerD(answerD);
			question.setResult(result);
			question.setExamDetailId(examDetailId);
			question.setSort(sort);
			question.setExamId(id);

			questionService.create(question);

		}

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
