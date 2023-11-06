package com.example.ergo.util;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class AESDecryptor {
    private static final String SECRET_KEY = "3f98e2c576a4b8d01a2b3c4d5e6f7g8h"; // 请替换为你自己的密钥
    private static final String INITIALIZATION_VECTOR = "1a2b3c4d5e6f7g8h"; // 请替换为你的初始化向量

//    public static void main(String[] args) throws Exception {
//        String text = "Hello, World!";
//        String encryptedText = encrypt(text);
//        System.out.println("加密后的文本： " + encryptedText);
//
//        String decryptedText = decrypt(encryptedText);
//        System.out.println("解密后的文本： " + decryptedText);
//    }

    public static String encrypt(String requestData) throws Exception {
        IvParameterSpec iv = new IvParameterSpec(INITIALIZATION_VECTOR.getBytes(StandardCharsets.UTF_8));
        SecretKeySpec skeySpec = new SecretKeySpec(SECRET_KEY.getBytes(StandardCharsets.UTF_8), "AES");

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

        byte[] encrypted = cipher.doFinal(requestData.getBytes());
        return Base64.getEncoder().encodeToString(encrypted);
    }

    public static String decrypt(String requestData) throws Exception {
        IvParameterSpec iv = new IvParameterSpec(INITIALIZATION_VECTOR.getBytes(StandardCharsets.UTF_8));
        SecretKeySpec skeySpec = new SecretKeySpec(SECRET_KEY.getBytes(StandardCharsets.UTF_8), "AES");

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
        cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);

        byte[] original = cipher.doFinal(Base64.getDecoder().decode(requestData));

        return new String(original);
    }
}