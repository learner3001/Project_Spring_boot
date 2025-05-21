package com.cts.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cts.entity.Course;

@FeignClient(name = "course-service")
public interface CourseClient {
	@GetMapping("/course/courses/{id}")
	ResponseEntity<Course> getCourseById(@PathVariable int id);
}
