package com.plf.security;

import java.security.Key;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;

import org.bouncycastle.util.encoders.Hex;

public class Security3DES {
	private static String src="security 3des";
	public static void main(String[] args) {
		jdk3DES();
	}
	public static void jdk3DES(){
		try{
			//生成KEY
			KeyGenerator keyGenerator=KeyGenerator.getInstance("DESede");
			//keyGenerator.init(168);
			keyGenerator.init(new SecureRandom());
			SecretKey secretKey=keyGenerator.generateKey();
			byte[] bytesKey=secretKey.getEncoded();
			
			//KEY转换
			DESedeKeySpec desKeySpec=new DESedeKeySpec(bytesKey);
			SecretKeyFactory factory=SecretKeyFactory.getInstance("DESede");
			Key convertSecretKey=factory.generateSecret(desKeySpec);
			
			//加密
			Cipher cipher=Cipher.getInstance("DESede/ECB/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, convertSecretKey);
			byte[] result=cipher.doFinal(src.getBytes());
			System.out.println("jdk 3des encrypt:"+Hex.encode(result));
			
			//解密
			cipher.init(Cipher.DECRYPT_MODE, convertSecretKey);
			result=cipher.doFinal(result);
			System.out.println("jdk 3des decrypt:"+new String(result));
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unused")
	private static void bc3DES(){

	}

}

