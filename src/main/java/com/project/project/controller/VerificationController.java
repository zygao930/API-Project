package com.project.project.controller;

import com.project.project.service.EmailService.VerificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for handling email verification code operations.
 */
@RestController
@RequestMapping("/api/verification")
public class VerificationController {

    @Autowired
    private VerificationService verificationService;

    /**
     * Endpoint for sending a verification code to a user's email.
     *
     * @param email Email address to which the verification code will be sent.
     * @return Success or failure response.
     */
    @PostMapping("/send")
    public ResponseEntity<String> sendVerificationCode(@RequestParam String email) {
        try {
            verificationService.sendVerificationCode(email);
            return ResponseEntity.ok("验证码已发送");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("验证码发送失败");
        }
    }

    /**
     * Endpoint for verifying a submitted verification code.
     *
     * @param email Email address of the user.
     * @param code  Verification code submitted by the user.
     * @return Success or failure response.
     */
    @PostMapping("/verify")
    public ResponseEntity<String> verifyCode(@RequestParam String email, @RequestParam String code) {
        boolean isValid = verificationService.verifyCode(email, code);
        if (isValid) {
            return ResponseEntity.ok("已验证");
        } else {
            return ResponseEntity.status(400).body("验证码失效");
        }
    }

    /**
     * Test endpoint to check if the server is running.
     *
     * @return Success message.
     */
    @PostMapping("/test")
    public ResponseEntity<String> testEndpoint() {
        return ResponseEntity.ok("终端正在运行");
    }
}
