package com.project.project.controller;

import com.project.project.service.EntityService.UserService;
import com.project.project.util.UserRegistrationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for handling user-related operations like registration.
 */
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * Endpoint for registering a new user.
     *
     * @param registrationDTO User registration data transfer object.
     * @param inputCode       Verification code for the user.
     * @return Success or failure response.
     */
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserRegistrationDTO registrationDTO, String inputCode) {
        try {
            userService.registerNewUser(registrationDTO, inputCode);
            return ResponseEntity.ok("用户注册成功");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
