package com.mahota.authorize.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

@Service
@Setter
@Getter
@RequiredArgsConstructor
public class SecurityService {
    private static final int SALT_LENGTH = 16;
    private static final int ITERATION_COUNT = 65536;
    private static final int KEY_LENGTH = 128;
    private static final String HASH_ALGORITHM = "PBKDF2WithHmacSHA1";

    public byte[] generateSalt() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] salt = new byte[SALT_LENGTH];
        secureRandom.nextBytes(salt);
        return salt;
    }

    public byte[] generateHash(String password, byte[] salt) {
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt,  ITERATION_COUNT, KEY_LENGTH);
        SecretKeyFactory factory;
        byte[] hash = new byte[0];

        try {
            factory = SecretKeyFactory.getInstance(HASH_ALGORITHM);
            hash = factory.generateSecret(spec).getEncoded();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException exception) {

        }

        return hash;
    }
}
