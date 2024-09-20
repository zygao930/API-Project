package com.project.project.security;

import lombok.Data;

@Data

public class LoginRequest {
    private String nickName;
    private String captcha;
    private String captchaKey;

    // Getters and Setters
}