package com.project.project.service.security;

import java.security.*;

/**
 * This service class generates a public-private key pair using the RSA algorithm.
 * It demonstrates how to create a KeyPairGenerator, generate keys, and print the keys in Base64 encoding.
 */
public class KeyPairGeneratorService {

    /**
     * The main method generates an RSA key pair (2048 bits) and prints the public and private keys in Base64 encoding.
     *
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        try {
            // Step 1: Create KeyPairGenerator object for the RSA algorithm
            KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");

            // Step 2: Initialize KeyPairGenerator with the desired key size (2048 bits)
            keyPairGen.initialize(2048);

            // Step 3: Generate the KeyPair (which includes both public and private keys)
            KeyPair pair = keyPairGen.generateKeyPair();

            // Retrieve the Public Key from the generated pair
            PublicKey publicKey = pair.getPublic();

            // Retrieve the Private Key from the generated pair
            PrivateKey privateKey = pair.getPrivate();

            // Step 4: Print keys in Base64 encoding (Optional)
            System.out.println("Public Key: " + java.util.Base64.getEncoder().encodeToString(publicKey.getEncoded()));
            System.out.println("Private Key: " + java.util.Base64.getEncoder().encodeToString(privateKey.getEncoded()));

        } catch (NoSuchAlgorithmException e) {
            // Print the stack trace if the RSA algorithm is not supported on the platform
            e.printStackTrace();
        }
    }
}
