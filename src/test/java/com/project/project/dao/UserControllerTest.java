//package com.project.project.dao;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.project.project.controller.UserController;
//import com.project.project.entity.User;
//import com.project.project.service.EntityService.UserService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import com.project.project.security.LoginRequest;
//import java.util.Optional;
//import lombok.*;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//public class UserControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private UserService userService;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @BeforeEach
//    public void setup() {
//        userService = Mockito.mock(UserService.class);
//    }
//
//    @Test
//    public void testFindByNickName_UserFound() throws Exception {
//        User user = new User();
//        user.setNickName("Claire");
//        user.setEmail("2871698220@qq.com");
//        user.setPassword("password123");
//
//        Mockito.when(userService.getUserByNickName("Claire")).thenReturn(Optional.of(user));
//
//        mockMvc.perform(get("/api/user/findByNickName?nickName=Claire")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(content().string("User found: Claire, Email: 2871698220@qq.com"));
//    }
//
//
//    @Test
//    public void testLogin() throws Exception {
//        LoginRequest loginRequest = new LoginRequest();
//        loginRequest.setNickName("Claire");
//        loginRequest.setCaptcha("S1MUIs");
//        loginRequest.setCaptchaKey("4946123d-5e40-4bd3-af15-d7760388b7fc");
//
//        mockMvc.perform(post("/api/auth/login")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(loginRequest)))
//                .andExpect(status().isOk());
//    }
//
//
//}
