package com.project.project.service.EmailService;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.Date;

/**
 * Service to handle email sending operations.
 */
@Service
public class MailService {
    private static final Logger logger = LoggerFactory.getLogger(MailService.class);

    @Autowired
    private JavaMailSenderImpl javaMailSender;

    @Value("${spring.mail.username}")
    private String sendMailer;

    /**
     * Validates the email parameters.
     *
     * @param to      Email recipient.
     * @param subject Email subject.
     * @param text    Email body content.
     */
    private void checkMail(String to, String subject, String text) {
        if (StringUtils.isEmpty(to)) {
            throw new RuntimeException("邮件收信人不能为空");
        }
        if (StringUtils.isEmpty(subject)) {
            throw new RuntimeException("邮件主题不能为空");
        }
        if (StringUtils.isEmpty(text)) {
            throw new RuntimeException("邮件内容不能为空");
        }
    }

    /**
     * Sends a simple text email.
     *
     * @param to      Recipient email address.
     * @param subject Subject of the email.
     * @param text    Content of the email.
     */
    public void sendTextMailMessage(String to, String subject, String text) {
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(javaMailSender.createMimeMessage(), true);
            mimeMessageHelper.setFrom(sendMailer);
            mimeMessageHelper.setTo(to.split(","));
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(text);
            mimeMessageHelper.setSentDate(new Date());

            javaMailSender.send(mimeMessageHelper.getMimeMessage());
            logger.info("发送邮件成功：{} -> {}", sendMailer, to);

        } catch (MessagingException e) {
            logger.error("发送邮件失败：{}", e.getMessage());
        }
    }
}
