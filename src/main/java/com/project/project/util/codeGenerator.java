package com.project.project.util;

import java.util.Random;

/**
 * Utility class for generating a 6-digit random verification code.
 */
public class codeGenerator {
    public static String generateCode(){
        Random random = new Random();
        int code = random.nextInt(999999);
        return String.format("%06d",code);
    }
}
