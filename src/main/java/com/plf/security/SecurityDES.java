package com.plf.security;

import java.security.Key;
import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Hex;

public class SecurityDES {
	private static String src="security des";
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		jdkDES();
	}
	public static void jdkDES(){
		try{
			//生成KEY
			KeyGenerator keyGenerator=KeyGenerator.getInstance("DES");
			keyGenerator.init(56);
			SecretKey secretKey=keyGenerator.generateKey();
			byte[] bytesKey=secretKey.getEncoded();
			
			//KEY转换
			DESKeySpec desKeySpec=new DESKeySpec(bytesKey);
			SecretKeyFactory factory=SecretKeyFactory.getInstance("DES");
			Key convertSecretKey=factory.generateSecret(desKeySpec);
			
			//加密
			Cipher cipher=Cipher.getInstance("DES/ECB/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, convertSecretKey);
			byte[] result=cipher.doFinal(src.getBytes());
			System.out.println("jdk des encrypt:"+Hex.encode(result));
			
			//解密
			cipher.init(Cipher.DECRYPT_MODE, convertSecretKey);
			result=cipher.doFinal(result);
			System.out.println("jdk des decrypt:"+new String(result));
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public static void bcDES(){
		try{
			Security.addProvider(new BouncyCastleProvider());
			//生成KEY
			KeyGenerator keyGenerator=KeyGenerator.getInstance("DES","BC");
			keyGenerator.getProvider();
			keyGenerator.init(56);
			SecretKey secretKey=keyGenerator.generateKey();
			byte[] bytesKey=secretKey.getEncoded();
			
			//KEY转换
			DESKeySpec desKeySpec=new DESKeySpec(bytesKey);
			SecretKeyFactory factory=SecretKeyFactory.getInstance("DES");
			Key convertSecretKey=factory.generateSecret(desKeySpec);
			
			//加密
			Cipher cipher=Cipher.getInstance("DES/ECB/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, convertSecretKey);
			byte[] result=cipher.doFinal(src.getBytes());
			System.out.println("jdk des encrypt:"+Hex.encode(result));
			
			//解密
			cipher.init(Cipher.DECRYPT_MODE, convertSecretKey);
			result=cipher.doFinal(result);
			System.out.println("jdk des decrypt:"+new String(result));
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
