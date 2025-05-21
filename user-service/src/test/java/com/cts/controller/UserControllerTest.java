package com.cts.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import com.cts.service.UserService;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public class UserControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    // ✅ Test: Register New User
    @Test
    public void testRegisterUser_Success() throws Exception {
        String userJson = """
            {
                "name": "John",
                "password": "pass123",
                "email": "john@example.com",
                "role": "student"
            }
        """;

        when(userService.addNewUser(anyString(), anyString(), anyString(), anyString()))
            .thenReturn("User registered successfully");

        mockMvc.perform(post("/user/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson))
                .andExpect(status().isOk())
                .andExpect(content().string("User registered successfully"));
    }

    // ✅ Test: Login User - Student
    @Test
    public void testLoginUser_Student() throws Exception {
        when(userService.checkLogin("student@example.com", "pass")).thenReturn("okStudent");

        mockMvc.perform(post("/user/login")
                .param("email", "student@example.com")
                .param("password", "pass"))
                .andExpect(status().isOk())
                .andExpect(content().string("Welcome Student"));
    }

    // ✅ Test: Login User - Instructor
    @Test
    public void testLoginUser_Instructor() throws Exception {
        when(userService.checkLogin("instructor@example.com", "pass")).thenReturn("okInstructor");

        mockMvc.perform(post("/user/login")
                .param("email", "instructor@example.com")
                .param("password", "pass"))
                .andExpect(status().isOk())
                .andExpect(content().string("Welcome Instructor"));
    }

    // ✅ Test: Login User - Incorrect Password
    @Test
    public void testLoginUser_IncorrectPassword() throws Exception {
        when(userService.checkLogin("user@example.com", "wrongpass")).thenReturn("incorrect password");

        mockMvc.perform(post("/user/login")
                .param("email", "user@example.com")
                .param("password", "wrongpass"))
                .andExpect(status().isOk())
                .andExpect(content().string("incorrect password"));
    }

    // ✅ Test: Login User - User Not Found
    @Test
    public void testLoginUser_NotFound() throws Exception {
        when(userService.checkLogin("unknown@example.com", "wrong")).thenReturn("user not found");

        mockMvc.perform(post("/user/login")
                .param("email", "unknown@example.com")
                .param("password", "wrong"))
                .andExpect(status().isOk())
                .andExpect(content().string("You are not a user please Register"));
    }

    // ✅ Test: Forgot Password - Email Found
    @Test
    public void testForgotPassword_Found() throws Exception {
        when(userService.forgotPassword("test@example.com")).thenReturn("found");

        mockMvc.perform(post("/user/forgotpassword")
                .param("email", "test@example.com"))
                .andExpect(status().isOk())
                .andExpect(content().string("Mail Sent"));
    }

    // ✅ Test: Forgot Password - Email Not Found
    @Test
    public void testForgotPassword_NotFound() throws Exception {
        when(userService.forgotPassword("notfound@example.com")).thenReturn("notfound");

        mockMvc.perform(post("/user/forgotpassword")
                .param("email", "notfound@example.com"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("User not registered"));
    }

    // ✅ Test: Reset Password - Success
    @Test
    public void testResetPassword_Success() throws Exception {
        userController.globalemail = "test@example.com";
        when(userService.resetPassword("test@example.com", "newpass")).thenReturn("passwordchanged");

        mockMvc.perform(post("/user/resetpassword")
                .param("newPassword", "newpass"))
                .andExpect(status().isOk())
                .andExpect(content().string("passwordchanged"));
    }

    // ✅ Test: Reset Password - Failure
    @Test
    public void testResetPassword_Failure() throws Exception {
        userController.globalemail = "test@example.com";
        when(userService.resetPassword("test@example.com", "newpass")).thenReturn("password not changed");

        mockMvc.perform(post("/user/resetpassword")
                .param("newPassword", "newpass"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("password not changed"));
    }
}
