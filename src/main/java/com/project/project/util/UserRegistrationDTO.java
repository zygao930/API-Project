package com.project.project.util;
import lombok.*;

import java.math.BigDecimal;

/**
 * Data Transfer Object for user registration details.
 * Contains fields for email, nickname, password, gender, and payKey.
 */
@Getter
public class UserRegistrationDTO {
    private String email;
    private String nickName;
    private String passWord;
    private String gender;
    private String payKey;
    private BigDecimal balance;
    private String avatarUrl;
}
