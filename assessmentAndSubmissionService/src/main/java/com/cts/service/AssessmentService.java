package com.cts.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cts.entity.Assessment;
import com.cts.exception.AssessmentNotFoundException;
import com.cts.repos.IAssessmentRepository;

@Service
public class AssessmentService {
	
	@Autowired
	private IAssessmentRepository iAssessmentRepository;
	
	public Assessment addAssessment(Assessment assessment) {
		iAssessmentRepository.save(assessment);
		return assessment;
	}
	
	public Assessment updateAssessmentById(int id,Assessment assessment) {
		if(iAssessmentRepository.existsById(id)) {
			assessment.setAssessmentID(id);
			iAssessmentRepository.save(assessment);
			return assessment;
		}
		throw new AssessmentNotFoundException("Assessment Not Found");
		
	}
	
	public Boolean removeAssessmentById(int id) {
		if(iAssessmentRepository.existsById(id)) {
			iAssessmentRepository.deleteById(id);
			return true;
		}
		throw new AssessmentNotFoundException("Assessment Not Found");
	}
	
	public List<Assessment> getAllAssessment(){
		return iAssessmentRepository.findAll();
	}
	
	public long getAssessmentCount() {
		return iAssessmentRepository.count();
	}
	
	public Assessment getAssessmentById(int id) {
		return iAssessmentRepository.findById(id).orElseThrow(() -> new AssessmentNotFoundException("Assessment Not Found"));
	}
	
}
