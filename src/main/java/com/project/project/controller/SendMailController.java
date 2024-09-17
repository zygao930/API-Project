package com.project.project.controller;

import com.project.project.service.EmailService.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for handling email sending operations.
 */
@RestController
@RequestMapping("/mail")
public class SendMailController {

    @Autowired
    private MailService mailService;

    /**
     * Endpoint for sending a text-based email.
     *
     * @param to      Recipient's email address.
     * @param subject Email subject.
     * @param text    Email content.
     */
    @RequestMapping("/sendTextMail")
    public void sendTextMail(String to, String subject, String text) {
        mailService.sendTextMailMessage(to, subject, text);
    }
}
