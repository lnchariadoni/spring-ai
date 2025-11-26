package com.chari.learning.spring.ai.spring_ai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringAiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringAiApplication.class, args);
	}

//    @Bean
//    public List<ToolCallback> courseTools(CourseService courseService) {
//      return List.of(ToolCallbacks.from(courseService));
//    }

//  @Bean
//  ToolCallbackProvider weatherTools(CourseService courseService) {
//    return MethodToolCallbackProvider.builder().toolObjects(courseService).build();
//  }

}
