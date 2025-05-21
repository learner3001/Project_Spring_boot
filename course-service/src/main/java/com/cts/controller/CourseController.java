package com.cts.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.entity.Course;
import com.cts.service.CourseService;

@RestController
@RequestMapping("/course")
public class CourseController {

	@Autowired
	private CourseService courseService;

	@PostMapping("/courses")
	public ResponseEntity<Course> createCourse(@RequestBody Course course) {
		return new ResponseEntity<>(courseService.createCourse(course), HttpStatus.CREATED);
	}

	@PutMapping("/courses/{id}")
	public ResponseEntity<Course> updateCourseById(@RequestBody Course course, @PathVariable int id) {
		return new ResponseEntity<>(courseService.updateCourseById(course, id), HttpStatus.OK);
	}

	@DeleteMapping("/courses/{id}")
	public ResponseEntity<Boolean> deleteCourseById(@PathVariable int id) {
		return new ResponseEntity<>(courseService.deleteCourseById(id), HttpStatus.OK);
	}

	@GetMapping("/courses")
	public ResponseEntity<List<Course>> getAllCourses() {
		return new ResponseEntity<>(courseService.getAllCourse(), HttpStatus.OK);
	}

	@GetMapping("/courses/count")
	public ResponseEntity<Long> getCourseCount() {
		return new ResponseEntity<>(courseService.getCourseCount(), HttpStatus.OK);
	}

	@GetMapping("/courses/{id}")
	public ResponseEntity<Course> getCourseById(@PathVariable int id) {
		return new ResponseEntity<>(courseService.getCourseById(id), HttpStatus.OK);
	}

}
