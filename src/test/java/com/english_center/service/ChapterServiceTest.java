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
import com.english_center.dao.impl.ChapterDaoImpl;
import com.english_center.entity.Chapter;
import com.english_center.model.StoreProcedureListResult;
import com.english_center.service.impl.ChapterServiceImpl;

@ExtendWith(MockitoExtension.class)
public class ChapterServiceTest {

	@Mock
	private ChapterDaoImpl chapterDaoImpl;

	@InjectMocks
	private ChapterServiceImpl chapterServiceImpl;

	private Chapter chapter;

	private Chapter savedChapter;

	private List<Chapter> listChapter;

	private StoreProcedureListResult<Chapter> chapterStore;

	@BeforeEach
	public void setup() {
		chapter = Chapter.builder().id(1).name("Chuong hoc tesst").courseId(1).build();

		savedChapter = Chapter.builder().id(2).name("Chuong hoc tesst 2").courseId(1).build();

		listChapter = List.of(chapter, savedChapter);

		chapterStore = new StoreProcedureListResult<Chapter>(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(),
				listChapter.size(), listChapter);
	}

	@Test
	void ChapterService_findOne_ReturnChapter() throws Exception {
		when(chapterDaoImpl.findOne(anyInt())).thenReturn(chapter);
		Chapter chapterTest = this.chapterServiceImpl.findOne(1);
		Assertions.assertThat(chapterTest).isNotNull();
		Assertions.assertThat(chapterTest.getId()).isEqualTo(1);

	}

	@Test
	void ChapterService_FindByCourseId_ReturnListChapter() throws Exception {
		when(chapterDaoImpl.findByCourseId(anyInt())).thenReturn(listChapter);
		List<Chapter> listChapterTest = this.chapterServiceImpl.findByCourseId(2);
		Assertions.assertThat(listChapterTest).isNotNull();
		Assertions.assertThat(listChapterTest.size()).isEqualTo(2);

	}

	@Test
	void ChapterService_FindByNameAndCourse_ReturnChapter() throws Exception {
		when(chapterDaoImpl.findByNameAndCourse(anyString(), anyInt())).thenReturn(chapter);
		Chapter chapterTest = this.chapterServiceImpl.findByNameAndCourse("Chuong hoc tesst", 2);
		Assertions.assertThat(chapterTest).isNotNull();
		Assertions.assertThat(chapterTest.getId()).isEqualTo(1);

	}

	@Test
	void ChapterService_SpGListChapter_ReturnListChapter() throws Exception {
		when(chapterDaoImpl.spGListChapter(anyInt(), anyString(), anyInt(), any(), anyInt(), anyInt()))
				.thenReturn(chapterStore);
		StoreProcedureListResult<Chapter> listChapterTest = chapterServiceImpl.spGListChapter(1, "", 1,
				new Pagination(1, 20), 1);
		Assertions.assertThat(listChapterTest.getResult()).isNotNull();
		Assertions.assertThat(listChapterTest.getResult()).hasSizeGreaterThan(1);

	}

}
