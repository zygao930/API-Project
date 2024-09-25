package com.project.project.service.security;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;
import javax.crypto.Cipher;

/**
 * This class provides methods for RSA encryption and decryption.
 * It allows for the encryption and decryption of data using public and private keys,
 * as well as the encryption and decryption of AES keys.
 */
public class RSAEncrypt {

    private final PublicKey publicKey; // Public key for encryption
    private final PrivateKey privateKey; // Private key for decryption

    /**
     * Constructor to initialize RSAEncrypt with the specified public and private keys.
     *
     * @param publicKey  The public key used for encryption.
     * @param privateKey The private key used for decryption.
     */
    public RSAEncrypt(PublicKey publicKey, PrivateKey privateKey) {
        this.publicKey = publicKey;
        this.privateKey = privateKey;
    }

    /**
     * Encrypts the given data using the public key.
     *
     * @param data The data to be encrypted.
     * @return The encrypted data encoded in Base64 format.
     * @throws Exception If an error occurs during encryption.
     */
    public String encrypt(String data) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] encryptedBytes = cipher.doFinal(data.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    /**
     * Decrypts the given encrypted data using the private key.
     *
     * @param encryptedData The encrypted data in Base64 format to be decrypted.
     * @return The decrypted data as a String.
     * @throws Exception If an error occurs during decryption.
     */
    public String decrypt(String encryptedData) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedData));
        return new String(decryptedBytes);
    }

    /**
     * Encrypts an AES key using the public key.
     *
     * @param aesKey The AES key to be encrypted.
     * @return The encrypted AES key encoded in Base64 format.
     * @throws Exception If an error occurs during encryption.
     */
    public String encryptAesKey(byte[] aesKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return Base64.getEncoder().encodeToString(cipher.doFinal(aesKey));
    }

    /**
     * Decrypts an encrypted AES key using the private key.
     *
     * @param encryptedAesKey The encrypted AES key in Base64 format to be decrypted.
     * @return The decrypted AES key as a byte array.
     * @throws Exception If an error occurs during decryption.
     */
    public byte[] decryptAesKey(String encryptedAesKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return cipher.doFinal(Base64.getDecoder().decode(encryptedAesKey));
    }
}
