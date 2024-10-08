package com.project.project.service.security;

import lombok.Data;
import lombok.Getter;

/**
 * This class represents a logout request containing the user's information needed to log out.
 * It is used to transfer logout data from the client to the server.
 */
@Data
public class LogoutRequest {

    /**
     * The nickname of the user attempting to log out.
     */
    @Getter
    private String nickName;

    /**
     * The captcha input provided by the user for verification.
     */
    private String captcha;

    /**
     * The key associated with the captcha for validation.
     */
    private String captchaKey;

    /**
     * The token associated with the user session that needs to be invalidated upon logout.
     */
    private String token;

    /**
     * The unique ID of the user attempting to log out.
     */
    private String userId;

    // Getters and Setters generated by Lombok's @Data annotation
}
