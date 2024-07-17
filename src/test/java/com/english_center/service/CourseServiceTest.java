package com.english_center.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.english_center.dao.impl.CourseDaoImpl;
import com.english_center.entity.Course;
import com.english_center.service.impl.CourseServiceImpl;

@ExtendWith(MockitoExtension.class)
public class CourseServiceTest {
	@Mock
	private CourseDaoImpl courseDao;

	@InjectMocks
	private CourseServiceImpl courseService;

	private Course course;

	private Course savedCourse;

	private List<Course> listCourse;

	@BeforeEach
	public void setup() {
		course = Course.builder().id(1).name("khoa hoc tesst").description("hehe").categoryCourseId(1).build();

		savedCourse = Course.builder().id(2).name("khoa hoc tesst 2").description("hehe").categoryCourseId(1).build();

		listCourse = List.of(course, savedCourse);
	}

	@Test
	public void CourseService_SpUCreateCourse_ReturnSavedCourse() throws Exception {
		when(this.courseDao.spUCreateCourse(anyString(), anyString(), any(), anyInt())).thenReturn(savedCourse);
		Course savedCourses = this.courseService.spUCreateCourse("khoa hoc tesst 2", "Rất tuyệt vời!", BigDecimal.ONE,
				1);
		Assertions.assertThat(savedCourses).isNotNull();
		Assertions.assertThat(savedCourses.getId()).isEqualTo(2);

	}

	@Test
	public void CourseService_FindOne_ReturnsCourse() throws Exception {
		when(courseDao.findOne(anyInt())).thenReturn(course);
		Course courseWithId = courseService.findOne(2);

		Assertions.assertThat(courseWithId).isNotNull();
		Assertions.assertThat(courseWithId.getId()).isEqualTo(1);

	}

	@Test
	public void CourseService_FindByName_ReturnCourse() throws Exception {
		when(courseDao.findByName(anyString())).thenReturn(course);

		Course courseTest = courseService.findByName(course.getName());
		Assertions.assertThat(courseTest).isNotNull();
		Assertions.assertThat(courseTest.getId()).isEqualTo(1);

	}

	@Test
	public void CourseService_SpUUpdateCourse_ReturnCourse() throws Exception {
		when(courseDao.spUUpdateCourse(anyInt(), anyString(), anyInt(), anyString(), any(), anyInt(), any()))
				.thenReturn(course);

		Course courseTest = courseService.spUUpdateCourse(course.getId(), course.getName(), course.getLessons(),
				course.getDescription(), BigDecimal.TEN, course.getIsFree(), BigDecimal.TEN);
		Assertions.assertThat(courseTest).isNotNull();
		Assertions.assertThat(courseTest.getId()).isEqualTo(1);

	}

	@Test
	public void CourseService_FindAll_ReturnListCourse() throws Exception {
		when(courseDao.findAll()).thenReturn(listCourse);

		List<Course> courseTest = courseService.findAll();
		Assertions.assertThat(courseTest.size()).isGreaterThan(1);

	}
}
