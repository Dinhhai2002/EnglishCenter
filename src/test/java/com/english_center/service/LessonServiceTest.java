package com.english_center.service;

import static org.mockito.ArgumentMatchers.any;
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
import com.english_center.dao.impl.LessonsDaoImpl;
import com.english_center.entity.Lessons;
import com.english_center.model.StoreProcedureListResult;
import com.english_center.service.impl.LessonsServiceImpl;

@ExtendWith(MockitoExtension.class)
public class LessonServiceTest {
	@Mock
	private LessonsDaoImpl lessonsDaoImpl;

	@InjectMocks
	private LessonsServiceImpl lessonsServiceImpl;

	private Lessons lessons;

	private Lessons savedLessons;

	private List<Lessons> listLessons;

	private StoreProcedureListResult<Lessons> lessonsStore;

	@BeforeEach
	public void setup() {
		lessons = Lessons.builder().id(1).name("bai hoc tesst").courseId(1).chapterId(2).build();

		savedLessons = Lessons.builder().id(2).name("bai hoc tesst").courseId(1).chapterId(2).build();

		listLessons = List.of(lessons, savedLessons);

		lessonsStore = new StoreProcedureListResult<Lessons>(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(),
				listLessons.size(), listLessons);

	}

	@Test
	void LessonsService_FindOne_ReturnLessons() throws Exception {
		when(lessonsDaoImpl.findOne(anyInt())).thenReturn(lessons);
		Lessons lessons = this.lessonsServiceImpl.findOne(1);
		Assertions.assertThat(lessons).isNotNull();
		Assertions.assertThat(lessons.getId()).isEqualTo(1);

	}

	@Test
	void LessonsService_FindByChapterId_ReturnListLessons() throws Exception {
		when(lessonsDaoImpl.findByChapterId(anyInt())).thenReturn(listLessons);
		List<Lessons> listLessonsTest = this.lessonsServiceImpl.findByChapterId(1);
		Assertions.assertThat(listLessonsTest).isNotNull();
		Assertions.assertThat(listLessonsTest.size()).isEqualTo(2);

	}

	@Test
	void LessonsService_SpGListLessons_ReturnListLessons() throws Exception {
		when(lessonsDaoImpl.spGListLessons(anyInt(), anyInt(), anyString(), anyInt(), any(), anyInt(), anyInt()))
				.thenReturn(lessonsStore);
		StoreProcedureListResult<Lessons> listLessonsTest = lessonsServiceImpl.spGListLessons(1, 1, "", 1,
				new Pagination(1, 20), 1);
		Assertions.assertThat(listLessonsTest.getResult()).isNotNull();
		Assertions.assertThat(listLessonsTest.getResult()).hasSizeGreaterThan(1);

	}
}
