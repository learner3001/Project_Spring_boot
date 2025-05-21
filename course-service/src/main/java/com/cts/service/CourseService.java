package com.cts.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.client.LoggingClient;
import com.cts.client.NotificationClient;
import com.cts.client.UserClient;
import com.cts.entity.*;
import com.cts.exception.CourseAlreadyExistsException;
import com.cts.exception.CourseNotFoundException;
import com.cts.repo.ICourseRepository;

@Service
public class CourseService {

	@Autowired
	private UserClient userClient;
	
	@Autowired
	private NotificationClient notificationClient;
	
	@Autowired
	private LoggingClient loggingClient;
	
	@Autowired
	private ICourseRepository iCourseRepository;

	public Course createCourse(Course course) {
	    if (!iCourseRepository.existsByTitle(course.getTitle())) {
	        iCourseRepository.save(course);
	        User user = userClient.getUserById(course.getUser().getUserId());
	        notificationClient.email(
	        		new Email(
	        				user.getName(),
	        				user.getEmail(),
	        				"🎉 Your Course Has Been Successfully Added to Our E-Learning Platform!",
	        				"We are thrilled to inform you that your course, "+course.getTitle()+" , has been successfully added to our E-Learning Platform! 🚀\r\n"
	        				+ "\r\n"
	        				+ "Your expertise and dedication to educating learners are invaluable, and we are excited to have your course available for students eager to learn from you."));
	        loggingClient.logMessage("INFO","Course service added course");
	        return course;
	    } else {
	    	//
	        throw new CourseAlreadyExistsException("Course already exists");
	    }
	}


	public Course updateCourseById(Course course, int id) {
	    return iCourseRepository.findById(id)
	        .map(existingCourse -> {
	            course.setCourseID(id);
	            User user = userClient.getUserById(course.getUser().getUserId());
		        notificationClient.email(
		        		new Email(
		        				user.getName(),
		        				user.getEmail(),
		        				"✅ Your Course Has Been Successfully Updated!",
		        				"We are pleased to inform you that your course, "+course.getTitle()+", has been successfully updated on our E-Learning Platform! 🎉\r\n"
		        				+ "\r\n"
		        				+ "Your commitment to providing high-quality learning materials is truly valued, and we appreciate your efforts in refining the content for your students."));
	            loggingClient.logMessage("INFO","Course service updated course id "+id);
	            return iCourseRepository.save(course);
	        })
	        .orElseThrow(() -> new CourseNotFoundException("Course not found"));
	}



	public boolean deleteCourseById(int id) {
	    if (iCourseRepository.existsById(id)) {
	        iCourseRepository.deleteById(id);
	        Course course = iCourseRepository.findById(id).orElseThrow(()->new CourseNotFoundException("Course not found"));
	        User user = userClient.getUserById(course.getUser().getUserId());
	        notificationClient.email(
	        		new Email(
	        				user.getName(),
	        				user.getEmail(),
	        				"❌ Your Course Has Been Successfully Removed",
	        				"This is to confirm that your course, "+course.getTitle()+", has been successfully removed from our E-Learning Platform per your request.\r\n"
	        				+ "\r\n"
	        				+ "We appreciate your contributions to our learning community and hope to collaborate with you on future courses.\r\n"
	        				+ "\r\n"
	        				+ "Want to Create a New Course?\r\n"
	        				+ "🚀 You’re always welcome to add new courses that inspire learners.\r\n"
	        				+ "\r\n"
	        				+ "💡 Reach out to our support team if you need assistance with content creation.\r\n"
	        				+ "\r\n"
	        				+ "Thank you for being an integral part of our platform. We look forward to working with you again soon!"));
	        loggingClient.logMessage("INFO","Course service added enrollment");
	        return true;
	    } else {
	        throw new CourseNotFoundException("Course Not Found");
	    }
	}
	
	public List<Course> getAllCourse(){
		loggingClient.logMessage("INFO","Course service listed enrollment");
		return iCourseRepository.findAll();
	}
	
	public long getCourseCount() {
		loggingClient.logMessage("INFO","Course service counted enrollment");
		return iCourseRepository.count();
	}
	
	public Course getCourseById(int id) {
		loggingClient.logMessage("INFO","Course service fetched enrollment by Id");
		return iCourseRepository.findById(id).orElseThrow(() -> new CourseNotFoundException("Course Not Found"));
	}

}
