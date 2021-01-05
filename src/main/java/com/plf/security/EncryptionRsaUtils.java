package com.plf.security;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;
import org.apache.commons.lang.ArrayUtils;

public class EncryptionRsaUtils {
	/**
     * 加密算法RSA
     */
    public static final String KEY_ALGORITHM = "RSA";

    /** */
    /**
     * 签名算法
     */
    public static final String SIGNATURE_ALGORITHM = "MD5withRSA";

	
	/**
     * RSA最大加密明文大小
     */
    private static final int MAX_ENCRYPT_BLOCK = 117;

    /** */
    /**
     * RSA最大解密密文大小
     */
    private static final int MAX_DECRYPT_BLOCK = 128;
	
	/**
	 * 随机生成密钥对
	 * 
	 * @throws NoSuchAlgorithmException
	 */
	public static Map<Integer, String> genKeyPair() {
		Map<Integer, String> keyMap = new HashMap<>(); // 用于封装随机产生的公钥与私钥
		// KeyPairGenerator类用于生成公钥和私钥对，基于RSA算法生成对象
		KeyPairGenerator keyPairGen;
		try {
			keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
			// 初始化密钥对生成器，密钥大小为96-1024位
			keyPairGen.initialize(1024, new SecureRandom());
			// 生成一个密钥对，保存在keyPair中
			KeyPair keyPair = keyPairGen.generateKeyPair();
			RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate(); // 得到私钥
			RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic(); // 得到公钥
			String publicKeyString = new String(Base64.encodeBase64(publicKey.getEncoded()));
			// 得到私钥字符串
			String privateKeyString = new String(Base64.encodeBase64((privateKey.getEncoded())));
			// 将公钥和私钥保存到Map
			keyMap.put(0, publicKeyString); // 0表示公钥
			keyMap.put(1, privateKeyString); // 1表示私钥
			return keyMap;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}


	/**
	 * 公钥加密
	 * 
	 * @param data
	 * @param secretKey
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 */
	public static String encryptByRsa(String data, String publicKeyStr) {
		byte[] publicKeyStrBytes = Base64.decodeBase64(publicKeyStr);
		RSAPublicKey publicKey;
		try {
			publicKey = (RSAPublicKey) KeyFactory.getInstance(KEY_ALGORITHM)
					.generatePublic(new X509EncodedKeySpec(publicKeyStrBytes));
			Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
			cipher.init(Cipher.ENCRYPT_MODE, publicKey);

			StringBuilder stringBuilder = new StringBuilder();
			byte[] dataReturn = null;
			//在UTF-8编码中:一个中文等于三个字节,中文标点占三个字节。 一个英文字符等于一个字节,英文标点占一个字节。
			//所以需要避免javax.crypto.IllegalBlockSizeException: Data must not be longer than 117 bytes
			//指定大小需要注意
			for (int i = 0; i < data.length(); i += 32) {
				int count = ((i + 32) > data.length() ? data.length() : (i + 32));
				byte[] doFinal = cipher.doFinal(data.substring(i, count).getBytes("UTF-8"));
				stringBuilder.append(new String(doFinal));
				dataReturn = ArrayUtils.addAll(dataReturn, doFinal);
			}
			String result = new String(Base64.encodeBase64(dataReturn));
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	
	/**
	 * 私钥解密
	 * 
	 * @param data
	 * @param publicKeyStr
	 * @return
	 */
	public static String decryptByRsa(String data, String privateKeyStr) {
		byte[] inputData = Base64.decodeBase64(data);
		byte[] privateKeyBytes = Base64.decodeBase64(privateKeyStr);
		RSAPrivateKey privateKey;
		try {
			privateKey = (RSAPrivateKey) KeyFactory.getInstance("RSA")
					.generatePrivate(new PKCS8EncodedKeySpec(privateKeyBytes));
			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.DECRYPT_MODE, privateKey);

			StringBuilder stringBuilder = new StringBuilder();
			for (int i = 0; i < inputData.length; i += 128) {
				int count = ((i + 128) > inputData.length ? inputData.length : (i + 128));
				byte[] doFinal = cipher.doFinal(ArrayUtils.subarray(inputData, i, count));
				stringBuilder.append(new String(doFinal));
			}
			String result = stringBuilder.toString();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 公钥加密
	 * 
	 * @param data
	 * @param secretKey
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 */
	public static byte[] encryptByRsa(byte[] data, String publicKeyStr) {
		byte[] publicKeyStrBytes = Base64.decodeBase64(publicKeyStr);
		RSAPublicKey publicKey;
		try {
			publicKey = (RSAPublicKey) KeyFactory.getInstance(KEY_ALGORITHM)
					.generatePublic(new X509EncodedKeySpec(publicKeyStrBytes));
			Cipher cipher = Cipher.getInstance(publicKey.getAlgorithm());
			cipher.init(Cipher.ENCRYPT_MODE, publicKey);
			byte[] dataReturn = null;
			ByteArrayOutputStream out = new ByteArrayOutputStream();
		    int offSet = 0;
		    int i = 0;
		    int inputLen = data.length;
		    // 对数据分段加密
	        while (inputLen - offSet > 0) {
	            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
	            	dataReturn = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
	            } else {
	            	dataReturn = cipher.doFinal(data, offSet, inputLen - offSet);
	            }
	            out.write(dataReturn, 0, dataReturn.length);
	            i++;
	            offSet = i * MAX_ENCRYPT_BLOCK;
	        }
	        byte[] encryptedData  = out.toByteArray();
	        out.close();
			return encryptedData;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 私钥解密
	 * 
	 * @param data
	 * @param publicKeyStr
	 * @return
	 */
	public static byte[] decryptByRsa(byte[] encryptedData, String privateKeyStr) {
		byte[] privateKeyBytes = Base64.decodeBase64(privateKeyStr);
		RSAPrivateKey privateKey;
		try {
			privateKey = (RSAPrivateKey) KeyFactory.getInstance(KEY_ALGORITHM)
					.generatePrivate(new PKCS8EncodedKeySpec(privateKeyBytes));
			Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
			cipher.init(Cipher.DECRYPT_MODE, privateKey);
			int inputLen = encryptedData.length;
			ByteArrayOutputStream out = new ByteArrayOutputStream();
	        int offSet = 0;
	        byte[] dataReturn = null;
	        int i=0;
	        // 对数据分段解密
	        while (inputLen - offSet > 0) {
	            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
	            	dataReturn = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
	            } else {
	            	dataReturn = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
	            }
	            out.write(dataReturn, 0, dataReturn.length);
	            i++;
	            offSet = i * MAX_DECRYPT_BLOCK;
	        }
	        byte[] decryptedData = out.toByteArray();
	        out.close();
			return decryptedData;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	


	public static void main(String[] args) throws UnsupportedEncodingException {
		// 测试生成密钥对
		Map<Integer,String> map =genKeyPair();
		String publicKeyStr = map.get(0);
		String privateKeyStr = map.get(1);
		String data = "RSA是1977年由罗纳德·李维斯特（Ron Rivest）、阿迪·萨莫尔（Adi Shamir）和伦纳德·阿德曼（Leonard Adleman）一起提出的。当时他们三人都在麻省理工学院工作。RSA就是他们三人姓氏开头字母拼在一起组成的。";
		byte[] encryData = encryptByRsa(StringUtils.getBytesUtf8(data),publicKeyStr);
		System.out.println(Base64.encodeBase64String(encryData));
		System.out.println((decryptByRsa(Base64.encodeBase64String(encryData), privateKeyStr)));
		System.out.println(new String(decryptByRsa(encryData, privateKeyStr)));
	}
}
