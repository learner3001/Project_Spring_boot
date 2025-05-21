package com.cts.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cts.entity.Course;
import com.cts.entity.User;
import com.cts.exception.CourseNotFoundException;
import com.cts.repo.ICourseRepository;
import com.cts.service.CourseService;

@ExtendWith(MockitoExtension.class)
public class ServiceTest {
	
	@Mock
	ICourseRepository iCourseRepository;
	
	@InjectMocks
	CourseService courseService;
	
	static User user;
	static Course courseFail;
	static Course coursePass;
	static Course courseUpdate;
	
	@BeforeAll
	static void beforeAll() {
		user = new User(1, "Arjun", "DEV", "Arjun@gmail.com", "ABCD");
		courseFail = new Course(1, "Spring", "Description", "https://spring.io/api", user);
		coursePass = new Course(2, "Spring", "Description", "https://spring.io", user);
		courseUpdate = new Course(2, "Spring", "Spring is a powerful framework", "https://spring.io", user);
	}
	
	@Test
	void createCoursePass() {
		Course response = courseService.createCourse(coursePass);
		assertEquals(coursePass, response);
	}
	
	@Test
	void createFailPass() {
		Course response = courseService.createCourse(coursePass);
		assertNotEquals(courseFail, response);
	}
	
	@Test
	void updateCoursePass() {
	    // Mock repository behavior
	    when(iCourseRepository.findById(2)).thenReturn(Optional.of(courseUpdate));
	    when(iCourseRepository.save(any(Course.class))).thenAnswer(invocation -> invocation.getArgument(0));

	    // Create and update the course
	    Course course = courseService.createCourse(courseUpdate);
	    Course response = courseService.updateCourseById(course, 2);

	    // Assert expected behavior
	    assertEquals(courseUpdate.getTitle(), response.getTitle());
	    assertEquals(2, response.getCourseID());  // Ensuring ID assignment
	}

	
	@Test
	void updateCourseFail() {
	    // Mock repository behavior
	    when(iCourseRepository.findById(1)).thenReturn(Optional.empty());

	    // Try updating a course that doesn't exist
	    assertThrows(CourseNotFoundException.class, () -> courseService.updateCourseById(courseUpdate, 1));
	}

	@Test
	void deleteCourse() {
	    // Mock repository behavior
	    when(iCourseRepository.existsById(2)).thenReturn(true);
	    doNothing().when(iCourseRepository).deleteById(2);

	    // Perform delete operation
	    Boolean response = courseService.deleteCourseById(2);
	    
	    // Verify and assert deletion
	    assertTrue(response);
	    verify(iCourseRepository, times(1)).deleteById(2);
	}

	@Test
	void testGetAllCourse() {
	    when(iCourseRepository.findAll()).thenReturn(Arrays.asList(coursePass, courseFail));

	    List<Course> courses = courseService.getAllCourse();

	    assertEquals(2, courses.size());
	    assertEquals("Spring", courses.get(0).getTitle());
	}

	@Test
	void testGetCourseCount() {
	    when(iCourseRepository.count()).thenReturn(5L);

	    long courseCount = courseService.getCourseCount();

	    assertEquals(5, courseCount);
	}

	@Test
	void testGetCourseById_Success() {
	    when(iCourseRepository.findById(2)).thenReturn(Optional.of(coursePass));

	    Course foundCourse = courseService.getCourseById(2);

	    assertEquals(coursePass, foundCourse);
	    assertEquals("Spring", foundCourse.getTitle());
	}

	@Test
	void testGetCourseById_NotFound() {
	    when(iCourseRepository.findById(2)).thenReturn(Optional.empty());

	    assertThrows(CourseNotFoundException.class, () -> courseService.getCourseById(2));
	}

	
}
