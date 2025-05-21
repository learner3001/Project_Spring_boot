package com.cts.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.client.CourseClient;
import com.cts.client.NotificationClient;
import com.cts.client.UserClient;
import com.cts.entity.Email;
import com.cts.entity.Enrollment;
import com.cts.entity.User;
import com.cts.exception.EnrollmentAlreadyExistException;
import com.cts.exception.EnrollmentDoesnotExistException;
import com.cts.repo.IEnrollmentRepository;

@Service
public class EnrollmentService {

	@Autowired
	NotificationClient notificationClient;
	
	@Autowired
	UserClient userClient;
	
	@Autowired
	CourseClient courseClient;
	
    @Autowired
    private IEnrollmentRepository enrollmentRepository;

    public void addEnrollment(Enrollment enrollment) {
        if (enrollmentRepository.existsById(enrollment.getEnrollmentID())) {
            throw new EnrollmentAlreadyExistException(
                "Enrollment ID " + enrollment.getEnrollmentID() + " already exists"
            );
        }
        enrollmentRepository.save(enrollment);
        User user = userClient.getUserById(enrollment.getUser().getUserId());
        String coursetitle = courseClient.getCourseById(enrollment.getCourse().getCourseID()).getBody().getTitle();
        
        notificationClient.email(
				new Email(user.getName(),
						user.getEmail(),
						"Successfully Enrolled for Course","Dear "+user.getName()+" ,\r\n"
								+ "\r\n"
								+ "We are delighted to inform you that you have been successfully enrolled in our "+coursetitle+" course! Your registration has been processed, and we are excited to have you join us on this learning journey."));
        
    }

    public Enrollment getByEnrollmentId(int id) {
        return enrollmentRepository.findById(id)
            .orElseThrow(() -> new EnrollmentDoesnotExistException("Enrollment ID " + id + " not found"));
    }

    public List<Enrollment> findAllEnrollment() {
    	List<Enrollment> enrollments = enrollmentRepository.findAll();
    	if(enrollments.size()==0) {
    		throw new EnrollmentDoesnotExistException("Enrollment Table is Empty");
    	}
        return enrollments;
    }

    public Enrollment updateEnrollment(int id, Enrollment enrollment) {
        Enrollment existingEnrollment = enrollmentRepository.findById(id)
            .orElseThrow(() -> new EnrollmentDoesnotExistException("Enrollment ID " + id + " not found"));
        existingEnrollment.setProgress(enrollment.getProgress()); 
        return enrollmentRepository.save(existingEnrollment);
    }

    public boolean deleteById(int id) {
    	boolean flag = false;
        if (!enrollmentRepository.existsById(id)) {
            throw new EnrollmentDoesnotExistException("Enrollment ID " + id + " not found");
        }
        enrollmentRepository.deleteById(id);
        flag = true;
        return flag;
    }
}
