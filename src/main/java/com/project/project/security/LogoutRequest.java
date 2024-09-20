package com.project.project.security;

import lombok.Data;
import lombok.Getter;

@Data

public class LogoutRequest {
    @Getter
    private String nickName;
    private String captcha;
    private String captchaKey;
    private String token;
    private String userId;

    // Getters and Setters
}