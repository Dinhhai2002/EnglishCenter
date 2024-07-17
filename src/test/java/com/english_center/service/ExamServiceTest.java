package com.english_center.service;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import com.english_center.common.utils.Pagination;
import com.english_center.dao.impl.ExamDaoImpl;
import com.english_center.entity.Exam;
import com.english_center.model.StoreProcedureListResult;
import com.english_center.service.impl.ExamServiceImpl;

@ExtendWith(MockitoExtension.class)
public class ExamServiceTest {
	@Mock
	private ExamDaoImpl examDaoImpl;

	@InjectMocks
	private ExamServiceImpl examServiceImpl;

	private Exam exam;

	private Exam saveExam;

	private List<Exam> listExam;

	private StoreProcedureListResult<Exam> examStore;

	@BeforeEach
	public void setup() {
		exam = Exam.builder().id(1).name("ETS 2023 01").description("test").build();

		saveExam = Exam.builder().id(2).name("ETS 2023 02").description("test").build();

		listExam = List.of(exam, saveExam);

		examStore = new StoreProcedureListResult<Exam>(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(),
				listExam.size(), listExam);
	}

	@Test
	void ExamService_findOne_ReturnExam() throws Exception {
		when(examDaoImpl.findOne(anyInt())).thenReturn(exam);
		Exam examTest = this.examServiceImpl.findOne(1);
		Assertions.assertThat(examTest).isNotNull();
		Assertions.assertThat(examTest.getId()).isEqualTo(1);

	}

	@Test
	void ExamService_findByName_ReturnExam() throws Exception {
		when(examDaoImpl.findByName(anyString())).thenReturn(exam);
		Exam examTest = this.examServiceImpl.findByName("ETS 2023 02");
		Assertions.assertThat(examTest).isNotNull();
		Assertions.assertThat(examTest.getId()).isEqualTo(1);

	}

	@Test
	void ExamService_findByTopicId_ReturnListExam() throws Exception {
		when(examDaoImpl.findByTopicId(anyInt())).thenReturn(listExam);
		List<Exam> listExamTest = this.examServiceImpl.findByTopicId(2);
		Assertions.assertThat(listExamTest).isNotNull();
		Assertions.assertThat(listExamTest.size()).isGreaterThan(1);

	}

	@Test
	void ExamService_findByCategory_ReturnListExam() throws Exception {
		when(examDaoImpl.findByCategory(anyInt())).thenReturn(listExam);
		List<Exam> listExamTest = this.examServiceImpl.findByCategory(2);
		Assertions.assertThat(listExamTest).isNotNull();
		Assertions.assertThat(listExamTest.size()).isGreaterThan(1);

	}

	@Test
	void ExamService_SpGListExam_ReturnListExam() throws Exception {
		when(examDaoImpl.spGListExam(anyInt(), anyInt(), anyString(), anyInt(), anyInt(), anyInt(), anyInt()))
				.thenReturn(examStore);
		StoreProcedureListResult<Exam> listExamTest = examServiceImpl.spGListExam(1, 1, "", 1, new Pagination(1, 20),
				1);
		Assertions.assertThat(listExamTest.getResult()).isNotNull();
		Assertions.assertThat(listExamTest.getResult()).hasSizeGreaterThan(1);

	}

}
