package com.project.project.util;
import lombok.*;

/**
 * Data Transfer Object for user registration details.
 * Contains fields for email, nickname, password, gender, and payKey.
 */
@Getter
@ToString
public class UserRegistrationDTO {
    private String email;
    private String nickName;
    private String passWord;
    private String gender;
    private String payKey;
}
