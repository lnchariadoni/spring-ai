package com.chari.learning.spring.ai.spring_ai;

import com.chari.learning.spring.ai.spring_ai.service.CourseService;
import java.util.List;
import org.springframework.ai.support.ToolCallbacks;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

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
