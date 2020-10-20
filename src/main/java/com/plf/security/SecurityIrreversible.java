package com.plf.security;

import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;

/**
 * 不可逆加密
 * 
 * @author Panlf
 *
 */
public class SecurityIrreversible {
	/**
	 * MD5加密
	 * MD5信息摘要算法
	 * @param text
	 * @return
	 */
	public static String md5(String text) {
		MessageDigest messageDigest = null;
		try {
			messageDigest = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		byte[] bytes = messageDigest.digest(text.getBytes());
		return Hex.encodeHexString(bytes);
	}

	/**
	 * SHA
	 * 安全散列算法
	 * @param text
	 * @return
	 */
	public static String sha256(String text) {
		MessageDigest messageDigest = null;
		try {
			messageDigest = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		byte[] bytes = messageDigest.digest(text.getBytes());
		return Hex.encodeHexString(bytes);
	}

	/**
	 * HMAC
	 * 密钥相关的哈希运算消息认证码
	 * @param text
	 * @param sk
	 * @return
	 */
	public static String hmacSha256(String text, SecretKeySpec sk) {
		Mac mac = null;
		try {
			mac = Mac.getInstance("HmacSHA256");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		try {
			mac.init(sk);
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		}
		byte[] rawHmac = mac.doFinal(text.getBytes());
		return new String(Base64.encodeBase64(rawHmac));
	}
}
