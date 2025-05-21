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

import com.cts.client.LoggingClient;
import com.cts.entity.Enrollment;
import com.cts.service.EnrollmentService;

@RestController
@RequestMapping("/enrollment")
public class EnrollmentController {

	@Autowired
	private LoggingClient loggingClient;

	@Autowired
	private EnrollmentService enrollmentService;

	@PostMapping("/enrollments")
	public ResponseEntity<Void> newEnrollment(@RequestBody Enrollment enrollment) {
		enrollmentService.addEnrollment(enrollment);
		loggingClient.logMessage("INFO", "Enrollment service added enrollment");
		return ResponseEntity.ok().build();
	}

	@GetMapping("/enrollments/{id}")
	public ResponseEntity<Enrollment> getEnrollmentById(@PathVariable Integer id) {
		Enrollment enrollment = enrollmentService.getByEnrollmentId(id);
		loggingClient.logMessage("INFO", "Enrollment service fetched enrollment by id " + id);
		return ResponseEntity.ok(enrollment);
	}

	@GetMapping("/enrollments")
	public ResponseEntity<List<Enrollment>> getAllEnrollment() {
		List<Enrollment> listEnrollment = enrollmentService.findAllEnrollment();
		loggingClient.logMessage("INFO", "Enrollment service fetched all the records");
		return ResponseEntity.ok(listEnrollment);

	}

	@PutMapping("/enrollments/{id}")
	public ResponseEntity<Enrollment> updateEnrollment(@PathVariable int id, @RequestBody Enrollment updateEnrollment) {
		Enrollment enrollment = enrollmentService.updateEnrollment(id, updateEnrollment);
		if (enrollment == null) {
			return new ResponseEntity<>(enrollment, HttpStatus.CONFLICT);
		} else {
			loggingClient.logMessage("INFO", "Enrollment service updated element by id " + id);
			return ResponseEntity.ok(enrollment);
		}
	}

	@DeleteMapping("/enrollments/{id}")
	public ResponseEntity<Boolean> deleteEnrollment(@PathVariable int id) {
		boolean flag = enrollmentService.deleteById(id);
		if (flag) {
			loggingClient.logMessage("INFO", "Enrollment service deleted element by id " + id);
			return ResponseEntity.ok(true);
		}
		return ResponseEntity.notFound().build();

	}

}
