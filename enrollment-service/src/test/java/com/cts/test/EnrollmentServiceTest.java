package com.cts.test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cts.entity.Course;
import com.cts.entity.Enrollment;
import com.cts.entity.User;
import com.cts.exception.EnrollmentAlreadyExistException;
import com.cts.exception.EnrollmentDoesnotExistException;
import com.cts.repo.IEnrollmentRepository;
import com.cts.service.EnrollmentService;

@ExtendWith(MockitoExtension.class)
public class EnrollmentServiceTest {

    @Mock
    private IEnrollmentRepository enrollmentRepository;

    @InjectMocks
    private EnrollmentService enrollmentService;

    private Enrollment enrollment;

    @BeforeEach
    void setUp() {
        enrollment = new Enrollment(1,new User(),new Course(),1);
    }

    @Test
    void testAddEnrollment_Success() {
        when(enrollmentRepository.existsById(enrollment.getEnrollmentID())).thenReturn(false);
        when(enrollmentRepository.save(enrollment)).thenReturn(enrollment);

        assertDoesNotThrow(() -> enrollmentService.addEnrollment(enrollment));
    }

    @Test
    void testAddEnrollment_AlreadyExists() {
        when(enrollmentRepository.existsById(enrollment.getEnrollmentID())).thenReturn(true);

        assertThrows(EnrollmentAlreadyExistException.class, () -> enrollmentService.addEnrollment(enrollment));
    }

    @Test
    void testGetByEnrollmentId_Success() {
        when(enrollmentRepository.findById(1)).thenReturn(Optional.of(enrollment));

        Enrollment result = enrollmentService.getByEnrollmentId(1);
        assertNotNull(result);
        assertEquals(1, result.getEnrollmentID());
    }

    @Test
    void testGetByEnrollmentId_NotFound() {
        when(enrollmentRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(EnrollmentDoesnotExistException.class, () -> enrollmentService.getByEnrollmentId(1));
    }

    @Test
    void testFindAllEnrollment() {
        when(enrollmentRepository.findAll()).thenReturn(Arrays.asList(enrollment));

        List<Enrollment> result = enrollmentService.findAllEnrollment();
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
    }

    @Test
    void testDeleteEnrollment_Success() {
        when(enrollmentRepository.existsById(1)).thenReturn(true);
        doNothing().when(enrollmentRepository).deleteById(1);

        assertTrue(enrollmentService.deleteById(1));
    }

    @Test
    void testDeleteEnrollment_NotFound() {
        when(enrollmentRepository.existsById(1)).thenReturn(false);

        assertThrows(EnrollmentDoesnotExistException.class, () -> enrollmentService.deleteById(1));
    }
}
