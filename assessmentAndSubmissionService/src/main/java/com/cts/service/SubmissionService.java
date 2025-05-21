package com.cts.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cts.entity.Submission;
import com.cts.exception.SubmissionNotFoundException;
import com.cts.repos.ISubmissionRepository;


@Service
public class SubmissionService {
	
	@Autowired
	private ISubmissionRepository iSubmissionRepository;

	public Submission addSubmission(Submission submission) {
		iSubmissionRepository.save(submission);
		return submission;
	}

	public Submission updateSubmissionById(int id, Submission submission) {
		if(iSubmissionRepository.existsById(id)) {
			submission.setSubmissionID(id);
			iSubmissionRepository.save(submission);
			return submission;
		}
		throw new SubmissionNotFoundException("Submission Not Found");
	}

	public Boolean deleteSubmissionById(int id) {
		if(iSubmissionRepository.existsById(id)) {
			iSubmissionRepository.deleteById(id);
			return true;
		}
		throw new SubmissionNotFoundException("Submission Not Found");
	}

	public List<Submission> getAllSubmission() {
		return iSubmissionRepository.findAll();
	}

	public Long getSubmissionCount() {
		return iSubmissionRepository.count();
	}

	public Submission getSubmissionById(int id) {
		return iSubmissionRepository.findById(id).orElseThrow(() -> new SubmissionNotFoundException("Submission Not Found"));
	}
	
}
