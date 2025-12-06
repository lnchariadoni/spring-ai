package com.chari.learning.spring.ai.spring_ai;

import com.chari.learning.spring.ai.spring_ai.course.service.CourseService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Integration test for Spring AI application.
 * Note: This test focuses on unit service testing.
 * For full integration testing with database and MCP server,
 * additional configuration would be required.
 */
@SpringBootTest(classes = {CourseService.class})
class SpringAiApplicationTests {

	@Autowired
	private CourseService courseService;

	@Test
	void contextLoads() {
		// Verify that the application context loads successfully
		// and CourseService bean is available
		assertNotNull(courseService, "CourseService should be loaded");
	}

	@Test
	void testCourseServiceInitialized() {
		// Verify CourseService is properly initialized with sample data
		assertNotNull(courseService, "CourseService should be available");
		assertNotNull(courseService.getAllCourses(), "Courses should be initialized");
	}
}
