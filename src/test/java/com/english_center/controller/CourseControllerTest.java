package com.english_center.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.english_center.entity.Course;
import com.english_center.service.impl.CourseServiceImpl;

@WebMvcTest(CourseController.class)
@ExtendWith(MockitoExtension.class)
public class CourseControllerTest {
	@Autowired(required = true)
	private MockMvc mockMvc;

	@MockBean
	private CourseServiceImpl courseServiceImpl;

	private Course course;

	private Course savedCourse;

	private List<Course> listCourse;

	@BeforeEach
	public void setup() {
		course = Course.builder().id(1).name("khoa hoc tesst").description("hehe").categoryCourseId(1).build();

		savedCourse = Course.builder().id(2).name("khoa hoc tesst 2").description("hehe").categoryCourseId(1).build();

		listCourse = List.of(course, savedCourse);
	}

//	@Test
//	void findById_ReturnsSuccess() throws Exception {
//		when(this.courseServiceImpl.findAll()).thenReturn(listCourse);
//		this.mockMvc.perform(get("/api/v1/course")).andExpect(status().isOk())
//				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(jsonPath("$").isArray());
//	}
}
