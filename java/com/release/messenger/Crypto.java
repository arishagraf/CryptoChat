package com.release.messenger;

import android.util.Base64;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class Crypto {
    private static final String pass = "skillbox";
    private static SecretKeySpec keySpec;
    static {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] bytes = pass.getBytes();
            digest.update(bytes, 0, bytes.length);
            byte[] key = digest.digest();
            keySpec = new SecretKeySpec(key, "AES");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
    public static String encrypt(String text) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, keySpec);
        byte[] encrypted = cipher.doFinal(text.getBytes());
        return Base64.encodeToString(encrypted, Base64.DEFAULT);
    }

    public static String decrypt(String encoded) throws Exception {
        byte[] encrypted = Base64.decode(encoded, Base64.DEFAULT);
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, keySpec);
        byte[] decrypted = cipher.doFinal(encrypted);
        return new String(decrypted, "UTF-8");
    }
}
