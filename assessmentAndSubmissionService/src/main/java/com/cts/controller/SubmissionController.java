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
import com.cts.entity.Submission;
import com.cts.service.SubmissionService;

@RestController
@RequestMapping("/submissions")
public class SubmissionController {
	
	@Autowired
    private LoggingClient loggingClient;
	
	@Autowired
	private SubmissionService submissionService;
	
	@PostMapping("/submission")
	public ResponseEntity<Submission> addSubmission(@RequestBody Submission addSubmission){
		Submission submission = submissionService.addSubmission(addSubmission);
		loggingClient.logMessage("INFO","Submission service added submission");
		return new ResponseEntity<>(submission,HttpStatus.CREATED);
	}
	
	@PutMapping("/submission/{id}")
	public ResponseEntity<Submission> updateSubmission(@PathVariable int id,@RequestBody Submission updateSubmission){
		Submission submission = submissionService.updateSubmissionById(id,updateSubmission);
		loggingClient.logMessage("INFO","Submission service updated submission by id "+id);
		return new ResponseEntity<>(submission,HttpStatus.CREATED);
	}
	
	@DeleteMapping("/submission/{id}")
	public ResponseEntity<Boolean> deleteSubmission(@PathVariable int id){
		boolean deleteSubmissionById = submissionService.deleteSubmissionById(id);
		return new ResponseEntity<>(deleteSubmissionById,HttpStatus.OK);
	}
	
	@GetMapping("/submission")
	public ResponseEntity<List<Submission>> getAllSubmission(){
		List<Submission> submissions = submissionService.getAllSubmission();
		loggingClient.logMessage("INFO","Submission service fetched all the records");
		return new ResponseEntity<>(submissions,HttpStatus.OK);	
	}
	
	@GetMapping("/submission/count")
	public ResponseEntity<Long> getCourseCount(){
		Long submissionCount = submissionService.getSubmissionCount();
		loggingClient.logMessage("INFO","Submission Service Fetched total Count of records");
		return new ResponseEntity<>(submissionCount,HttpStatus.OK);
	}
	
	@GetMapping("/submission/{id}")
	public ResponseEntity<Submission> getCourseById(@PathVariable int id){
		Submission submission = submissionService.getSubmissionById(id);
		loggingClient.logMessage("INFO","Submission service fetched submission by id "+id);
		return new ResponseEntity<>(submission,HttpStatus.OK);
	}
}
