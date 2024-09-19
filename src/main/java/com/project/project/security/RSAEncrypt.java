package com.project.project.security;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import javax.crypto.Cipher;

public class RSAEncrypt {

    private PrivateKey privateKey;

    public RSAEncrypt(PrivateKey privateKey) {
        this.privateKey = privateKey;
    }

    public static String decrypt(String encryptedPassword, PrivateKey privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedPassword));
        return new String(decryptedBytes);
    }
}

