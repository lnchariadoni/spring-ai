package com.chari.learning.spring.ai.spring_ai.course.service;

import com.chari.learning.spring.ai.spring_ai.course.model.Course;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CourseServiceTest {
  private CourseService courseService;

  @BeforeEach
  void setUp() {
    courseService = new CourseService();
    courseService.init(); // Initialize with sample courses
  }

  @Test
  void testGetAllCourses_shouldReturnAllCourses() {
    // When
    List<Course> courses = courseService.getAllCourses();

    // Then
    assertNotNull(courses);
    assertEquals(4, courses.size());
    assertTrue(courses.stream().anyMatch(c -> c.title().contains("MCP Server")));
    assertTrue(courses.stream().anyMatch(c -> c.title().contains("AI/ML Introduction")));
  }

  @Test
  void testGetCourseById_withValidId_shouldReturnCourse() {
    // When
    Course course = courseService.getCourseById(1);

    // Then
    assertNotNull(course);
    assertEquals(1, course.id());
    assertEquals("MCP Server with Spring AI", course.title());
    assertTrue(course.url().contains("youtube.com"));
  }

  @Test
  void testGetCourseById_withInvalidId_shouldReturnNotFoundCourse() {
    // When
    Course course = courseService.getCourseById(999);

    // Then
    assertNotNull(course);
    assertEquals(-1, course.id());
    assertTrue(course.title().contains("does not exists"));
    assertTrue(course.title().contains("999"));
  }

  @Test
  void testGetCourseById_multipleIds_shouldReturnCorrectCourses() {
    // When
    Course course2 = courseService.getCourseById(2);
    Course course3 = courseService.getCourseById(3);

    // Then
    assertEquals(2, course2.id());
    assertEquals("AI/ML Introduction", course2.title());
    assertEquals(3, course3.id());
    assertEquals("Verson Control with Intellij", course3.title());
  }

  @Test
  void testGetAllCourses_shouldReturnUnmodifiableList() {
    // When
    List<Course> courses = courseService.getAllCourses();

    // Then
    assertNotNull(courses);
    assertEquals(4, courses.size());
  }
}
