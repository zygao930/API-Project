package com.project.project.security;

import java.security.*;

public class KeyPairGeneratorService {

    public static void main(String[] args) {
        try {
            // Step 1: Create KeyPairGenerator object
            KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");

            // Step 2: Initialize KeyPairGenerator with key size (e.g., 2048 bits)
            keyPairGen.initialize(2048);

            // Step 3: Generate the KeyPair (public and private key)
            KeyPair pair = keyPairGen.generateKeyPair();

            // Retrieve the Public Key
            PublicKey publicKey = pair.getPublic();

            // Retrieve the Private Key
            PrivateKey privateKey = pair.getPrivate();

            // Step 4: Print keys in Base64 encoding (Optional)
            System.out.println("Public Key: " + java.util.Base64.getEncoder().encodeToString(publicKey.getEncoded()));
            System.out.println("Private Key: " + java.util.Base64.getEncoder().encodeToString(privateKey.getEncoded()));

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}
