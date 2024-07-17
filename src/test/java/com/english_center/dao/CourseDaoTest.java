package com.english_center.dao;

import java.math.BigDecimal;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.english_center.dao.impl.CourseDaoImpl;
import com.english_center.entity.Course;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.HSQL)
public class CourseDaoTest {

	@Autowired
	private CourseDaoImpl courseDao;

//	@Test
//	public void CourseService_SpUCreateCourse_ReturnSavedCourse() throws Exception {
//		Course course = courseDao.spUCreateCourse("khoa hoc tesst 2", "Rất tuyệt vời!", BigDecimal.ONE, 1);
//		Assertions.assertThat(course).isNotNull();
////		Assertions.assertThat(course.getId()).isEqualTo(2);
//
//	}

}
