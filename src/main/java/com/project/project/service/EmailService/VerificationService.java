package com.project.project.service.EmailService;

import com.project.project.util.codeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;

/**
 * Service for handling email verification, including code generation and validation.
 */
@Service
public class VerificationService {

    @Autowired
    private MailService emailService;

    @Autowired
    private RedisService redisService;

    /**
     * Sends a verification code via email and stores it in Redis.
     *
     * @param email The recipient's email address.
     * @throws MessagingException If email sending fails.
     */
    public void sendVerificationCode(String email) throws MessagingException {
        // Generate verification code
        String code = codeGenerator.generateCode();

        // Send verification code via email
        emailService.sendTextMailMessage(email, "验证码", "你的验证码: " + code);

        // Store the code in Redis
        redisService.storeCode(email, code);
    }

    /**
     * Verifies the provided code against the one stored in Redis.
     *
     * @param email     Email address associated with the verification code.
     * @param inputCode Code input by the user.
     * @return True if the code matches, false otherwise.
     */
    public boolean verifyCode(String email, String inputCode) {
        String storedCode = redisService.getCode(email);
        return storedCode != null && storedCode.equals(inputCode);
    }
}
