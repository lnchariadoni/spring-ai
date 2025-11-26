package com.chari.learning.spring.ai.spring_ai.course.service;

import com.chari.learning.spring.ai.spring_ai.course.model.Course;
import jakarta.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springaicommunity.mcp.annotation.McpTool;
import org.springaicommunity.mcp.annotation.McpToolParam;
import org.springframework.stereotype.Service;

@Service
public class CourseService {
  private static final Logger logger = LoggerFactory.getLogger(CourseService.class);
  private final List<Course> courses = new ArrayList<>();

  private static Course getNotFoundCourse(Integer id) {
    return new Course(-1, String.format("The requested course with id: %d does not exists.", id), "", "");
  }

  @McpTool(name = "get_all_courses", description = "Get all available courses", title = "get_all_courses")
  public List<Course> getAllCourses() {
      logger.info("Fetching all courses. Total courses: {}", courses.size());
      return Collections.unmodifiableList(courses).stream().map(e ->
      {
        try {
            Thread.sleep(1000); // Simulate delay
            } catch (InterruptedException ex) {
//            Thread.currentThread().interrupt();
        }
        logger.info("Returning course: {}", e);
        return e;
      }).toList();
  }

  @McpTool(name = "get_course_by_id", description = "Get course details by ID", title = "get_course_by_id")
  public Course getCourseById(
      @McpToolParam(description = "id of the course to fetch details of the course") Integer id) {
      logger.info("Fetching course with ID: {}", id);
      return courses.stream()
              .filter(course -> course.id().equals(id))
              .findFirst()
              .orElse(getNotFoundCourse(id));
  }

//  @McpTool(name = "get_all_courses", description = "Get all available courses", title = "get_all_courses")
//  public Flux<Course> getAllCourses() {
//    logger.info("Fetching all courses. Total courses: {}", courses.size());
//    return Flux.fromIterable(courses); // delayElements(Duration.ofSeconds(1));
//  }
//
//  @McpTool(name = "get_course_by_id", description = "Get course details by ID", title = "get_course_by_id")
//  public Mono<Course> getCourseById(
//      @McpToolParam(description = "id of the course to fetch details of the course") Integer id) {
//    logger.info("Fetching course with ID: {}", id);
//    return Flux.fromIterable(courses)
//        .filter(course -> course.id().equals(id))
//        .next();
//  }

  @PostConstruct
  public void init() {
    courses.add(new Course(1, "MCP Server with Spring AI", "https://www.youtube.com/watch?v=w5YVHG1j3Co", "This is a short course on building an MCP server using Spring AI by Dan Vega."));
    courses.add(new Course(2, "AI/ML Introduction", "https://www.youtube.com/watch?v=jzEr_AqHXgY", "Why Java developers need to know internals of AI/ML by Frank Greco. This covers basics of AI/ML and its importance for Java developers. It is not for building AI/ML models but for understanding how to use them effectively in Java applications."));
    courses.add(new Course(3, "Verson Control with Intellij", "https://www.youtube.com/watch?v=-S3Q_-b52rA", "This is a tutorial on using version control systems like Git within the IntelliJ IDEA IDE by Dmitriy Smirnov."));
    courses.add(new Course(4, "As song sung by Bhimsen Joshi", "https://www.youtube.com/watch?v=_tdYY6lUw9g", "Devotional song Bhagyada Lakshmi Baramma sung by the legendary Bhimsen Joshi."));
    logger.info("Initialized CourseService with sample courses.");
  }
}
