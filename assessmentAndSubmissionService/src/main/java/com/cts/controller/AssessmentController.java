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
import com.cts.entity.Assessment;
import com.cts.service.AssessmentService;

@RestController
@RequestMapping("/assessments")
public class AssessmentController {
	
	@Autowired
    private LoggingClient loggingClient;
	
	@Autowired
	private AssessmentService assessmentService;
	
	@PostMapping("/assessment")
	public ResponseEntity<Assessment> addAssessment(@RequestBody Assessment postAssessment){
		Assessment assessment = assessmentService.addAssessment(postAssessment);
		loggingClient.logMessage("INFO","Assessment service added Assessment");
		return new ResponseEntity<>(assessment,HttpStatus.CREATED);
	}
	
	@PutMapping("/assessment/{id}")
	public ResponseEntity<Assessment> updateAssessment(@PathVariable int id,@RequestBody Assessment assessment){
		Assessment updateAssessmentById = assessmentService.updateAssessmentById(id,assessment);
		loggingClient.logMessage("INFO","Assessment service updated Assessment");
		return new ResponseEntity<>(updateAssessmentById,HttpStatus.CREATED);
	}
	
	@DeleteMapping("/assessment/{id}")
	public ResponseEntity<Boolean> deleteAssessment(@PathVariable int id){
		boolean flag = assessmentService.removeAssessmentById(id);
		loggingClient.logMessage("INFO","Assessment service deleted Assessment "+id);
		return new ResponseEntity<>(flag,HttpStatus.OK);
	}
	
	@GetMapping("/assessment")
	public ResponseEntity<List<Assessment>> getAllCourses(){
		List<Assessment> allAssessment = assessmentService.getAllAssessment();
		loggingClient.logMessage("INFO","Assessment service fetched all the records");
		return new ResponseEntity<>(allAssessment,HttpStatus.OK);	
	}
	
	@GetMapping("/assessment/count")
	public ResponseEntity<Long> getCourseCount(){
		long assessmentCount = assessmentService.getAssessmentCount();
		loggingClient.logMessage("INFO","Assessment Service Fetched total Count of records");
		return new ResponseEntity<>(assessmentCount,HttpStatus.OK);
	}
	
	@GetMapping("/assessment/{id}")
	public ResponseEntity<Assessment> getCourseById(@PathVariable int id){
		Assessment assessmentById = assessmentService.getAssessmentById(id);
		loggingClient.logMessage("INFO","Assessment service fetched assessment by id "+id);
		return new ResponseEntity<>(assessmentById,HttpStatus.OK);
	}
	
}
