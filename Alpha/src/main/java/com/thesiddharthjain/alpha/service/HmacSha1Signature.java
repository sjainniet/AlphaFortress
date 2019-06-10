package com.thesiddharthjain.alpha.service;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.Formatter;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;


@Component
public class HmacSha1Signature {
	

	private static final String HMAC_SHA256_ALGORITHM = "HmacSHA256";

	private static String toHexString(byte[] bytes) {
		Formatter formatter = new Formatter();
		
		for (byte b : bytes) {
			formatter.format("%02x", b);
		}

		return formatter.toString();
	}

	public static String calculateRFC2104HMAC(String data, String key)
		throws SignatureException, NoSuchAlgorithmException, InvalidKeyException
	{
		SecretKeySpec signingKey = new SecretKeySpec(key.getBytes(), HMAC_SHA256_ALGORITHM);
		Mac mac = Mac.getInstance(HMAC_SHA256_ALGORITHM);
		mac.init(signingKey);
		 return toHexString(mac.doFinal(data.getBytes()));
				
	}
	
	/*
	 * public static String sha256Form(String key, String data) { try { String
	 * secret = "secret"; String message = "Message";
	 * 
	 * Mac sha256_HMAC = Mac.getInstance("HmacSHA256"); SecretKeySpec secret_key =
	 * new SecretKeySpec(secret.getBytes(), "HmacSHA256");
	 * sha256_HMAC.init(secret_key);
	 * 
	 * String hash =
	 * Base64.encodeBase64String(sha256_HMAC.doFinal(message.getBytes()));
	 * System.out.println(hash); } catch (Exception e){ System.out.println("Error");
	 * } }
	 */

}
