//package com.project.project.dao;
//
//import com.project.project.controller.AuthController;
//import com.project.project.security.*;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.http.ResponseEntity;
//import org.springframework.test.context.ActiveProfiles;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.anyString;
//import static org.mockito.Mockito.*;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//public class AuthControllerTest {
//
//    @InjectMocks
//    private AuthController authController;
//
//    @MockBean
//    private RedisTemplate<String, String> redisTemplate;
//
//    @MockBean
//    private TokenService tokenService;
//
//    @BeforeEach
//    public void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    public void testLogout_UserIsLoggedIn() {
//        // Arrange
//        String nickname = "Claire";
//        LogoutRequest logoutRequest = new LogoutRequest();
//        logoutRequest.setNickName(nickname);
//
//        when(redisTemplate.opsForValue().get(nickname)).thenReturn("someValidToken");
//        when(redisTemplate.delete(nickname)).thenReturn(true);
//
//        // Act
//        ResponseEntity<String> response = authController.logout(logoutRequest);
//
//        // Assert
//        assertEquals(200, response.getStatusCodeValue());
//        assertEquals("User logged out successfully.", response.getBody());
//    }
//
//    @Test
//    public void testLogout_UserIsNotLoggedIn() {
//        // Arrange
//        String nickname = "Claire";
//        LogoutRequest logoutRequest = new LogoutRequest();
//        logoutRequest.setNickName(nickname);
//
//        when(redisTemplate.opsForValue().get(nickname)).thenReturn(null);
//
//        // Act
//        ResponseEntity<String> response = authController.logout(logoutRequest);
//
//        // Assert
//        assertEquals(400, response.getStatusCodeValue());
//        assertEquals("User is not logged in.", response.getBody());
//    }
//
//    @Test
//    public void testLogout_FailedToDeleteUser() {
//        // Arrange
//        String nickname = "Claire";
//        LogoutRequest logoutRequest = new LogoutRequest();
//        logoutRequest.setNickName(nickname);
//
//        when(redisTemplate.opsForValue().get(nickname)).thenReturn("someValidToken");
//        when(redisTemplate.delete(nickname)).thenReturn(false);
//
//        // Act
//        ResponseEntity<String> response = authController.logout(logoutRequest);
//
//        // Assert
//        assertEquals(400, response.getStatusCodeValue());
//        assertEquals("Failed to log out user.", response.getBody());
//    }
//}
