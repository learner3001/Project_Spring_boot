package com.cts.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.cts.client.NotificationClient;
import com.cts.entity.Email;
import com.cts.entity.User;
import com.cts.exception.UserAlreadyExistsException;
import com.cts.repository.UserRepo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class UserServiceTest {

    @Mock
    private UserRepo userRepo;

    @Mock
    private NotificationClient notificationClient;

    @InjectMocks
    private UserService userService;

    private BCryptPasswordEncoder encoder;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        encoder = new BCryptPasswordEncoder(12);
    }

    // ✅ Test: Add New User - Success
    @Test
    public void testAddNewUser_Success() {
        User newUser = new User();
        newUser.setEmail("john@example.com");

        when(userRepo.findByEmail("john@example.com")).thenReturn(null);
        when(userRepo.save(any(User.class))).thenReturn(newUser);

        String result = userService.addNewUser("John", "pass123", "john@example.com", "student");

        assertEquals("user has been added successfully", result);
        verify(userRepo).save(any(User.class));
    }

    // ✅ Test: Add New User - Email Already Exists
    @Test
    public void testAddNewUser_EmailExists() {
        User existingUser = new User();
        existingUser.setEmail("existing@example.com");

        when(userRepo.findByEmail("existing@example.com")).thenReturn(existingUser);

        assertThrows(UserAlreadyExistsException.class, () -> {
            userService.addNewUser("John", "pass123", "existing@example.com", "student");
        });

        verify(userRepo, never()).save(any(User.class));
    }

    // ✅ Test: Check Login - Successful Login for Student
    @Test
    public void testCheckLogin_StudentSuccess() {
        User mockUser = new User();
        mockUser.setEmail("student@example.com");
        mockUser.setPassword(encoder.encode("pass"));
        mockUser.setRole("STUDENT");

        when(userRepo.findByEmail("student@example.com")).thenReturn(mockUser);

        String result = userService.checkLogin("student@example.com", "pass");
        assertEquals("okStudent", result);
    }

    // ✅ Test: Check Login - Incorrect Password
    @Test
    public void testCheckLogin_IncorrectPassword() {
        User mockUser = new User();
        mockUser.setEmail("user@example.com");
        mockUser.setPassword(encoder.encode("correctpass"));

        when(userRepo.findByEmail("user@example.com")).thenReturn(mockUser);

        String result = userService.checkLogin("user@example.com", "wrongpass");
        assertEquals("incorrect password", result);
    }

    // ✅ Test: Forgot Password - User Found
    @Test
    public void testForgotPassword_Found() {
        User mockUser = new User();
        mockUser.setEmail("test@example.com");
        mockUser.setName("Test User");

        when(userRepo.findByEmail("test@example.com")).thenReturn(mockUser);

        String result = userService.forgotPassword("test@example.com");

        assertEquals("found", result);
        verify(notificationClient).email(any(Email.class));
    }

    // ✅ Test: Forgot Password - User Not Found
    @Test
    public void testForgotPassword_NotFound() {
        when(userRepo.findByEmail("unknown@example.com")).thenReturn(null);

        String result = userService.forgotPassword("unknown@example.com");

        assertEquals("notfound", result);
        verify(notificationClient, never()).email(any(Email.class));
    }

    // ✅ Test: Reset Password - Success
    @Test
    public void testResetPassword_Success() {
        User mockUser = new User();
        mockUser.setEmail("reset@example.com");
        mockUser.setPassword(encoder.encode("oldpass"));

        when(userRepo.findByEmail("reset@example.com")).thenReturn(mockUser);

        String result = userService.resetPassword("reset@example.com", "newpass");

        assertEquals("passwordchanged", result);
        verify(userRepo).save(mockUser);
    }

    // ✅ Test: Reset Password - Failure (User Not Found)
    @Test
    public void testResetPassword_Failure() {
        when(userRepo.findByEmail("notfound@example.com")).thenReturn(null);

        String result = userService.resetPassword("notfound@example.com", "newpass");

        assertEquals("passwordnotchanged", result);
        verify(userRepo, never()).save(any(User.class));
    }
}
