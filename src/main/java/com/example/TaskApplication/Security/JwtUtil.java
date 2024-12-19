package com.example.TaskApplication.Security;

import java.security.Key;
import java.util.Base64;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class JwtUtil {

  private static final String SECRET_KEY = "your-secret-key";
  private static final long EXPIRATION_TIME = 1000 * 60 * 60;

  public static String createToken(String username) {
    long now = System.currentTimeMillis();
    long expirationTime = now + EXPIRATION_TIME;

    String header = "{\"alg\":\"HS256\",\"typ\":\"JWT\"}";

    String payload = "{\"sub\":\"" + username + "\",\"exp\":" + expirationTime + "}";

    String encodedHeader = Base64.getUrlEncoder().encodeToString(header.getBytes());
    String encodedPayload = Base64.getUrlEncoder().encodeToString(payload.getBytes());

    String data = encodedHeader + "." + encodedPayload;

    String signature = createSignature(data);

    return data + "." + signature;
  }

  private static String createSignature(String data) {
    try {
      Key hmacKey = new SecretKeySpec(SECRET_KEY.getBytes(), "HMACSHA256");
      Mac mac = Mac.getInstance("HMACSHA256");
      mac.init(hmacKey);
      byte[] signatureBytes = mac.doFinal(data.getBytes());

      return Base64.getUrlEncoder().encodeToString(signatureBytes);
    } catch (Exception e) {
      throw new RuntimeException("Error creating JWT signature", e);
    }
  }

  public static String parseToken(String token) {
    try {
      String[] parts = token.split("\\.");
      if (parts.length != 3) {
        throw new IllegalArgumentException("Invalid JWT token format");
      }

      String header = parts[0];
      String payload = parts[1];
      String signature = parts[2];

      String data = header + "." + payload;
      String expectedSignature = createSignature(data);

      if (!signature.equals(expectedSignature)) {
        throw new IllegalArgumentException("Invalid JWT signature");
      }

      String decodedPayload = new String(Base64.getUrlDecoder().decode(payload));

      String username = decodedPayload.split(",")[0].split(":")[1].replace("\"", "");
      String expiration = decodedPayload.split(",")[1].split(":")[1];

      long expirationTime = Long.parseLong(expiration);
      if (System.currentTimeMillis() > expirationTime) {
        throw new IllegalArgumentException("JWT token has expired");
      }

      return username;

    } catch (Exception e) {
      throw new IllegalArgumentException("Invalid JWT token", e);
    }
  }

  public static boolean validateToken(String token) {
    try {
      parseToken(token);
      return true;
    } catch (Exception e) {
      return false;
    }
  }
}