package com.plf.security;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Objects;

import javax.crypto.Cipher;
import javax.crypto.KeyAgreement;
import javax.crypto.SecretKey;
import javax.crypto.interfaces.DHPublicKey;
import javax.crypto.spec.DHParameterSpec;

import org.apache.commons.codec.binary.Base64;


public class SecurityDH {

	private static String src="security dh";
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		jdkDH();
	}

	public static void jdkDH(){
		
		try {
			//1、初始化发送方秘钥
			KeyPairGenerator senderKeyPairGenerator=KeyPairGenerator.getInstance("DH");
			senderKeyPairGenerator.initialize(512);
			KeyPair senderKeyPair=senderKeyPairGenerator.generateKeyPair();
			byte[] senderPublicKeyEnc=senderKeyPair.getPublic().getEncoded();//发送方公钥，发送给接收方（网络、文件。。。）
			
			//2、初始化接受方秘钥
			KeyFactory receivekeyFactory=KeyFactory.getInstance("DH");
			X509EncodedKeySpec x509EncodeKeySpec=new X509EncodedKeySpec(senderPublicKeyEnc);
			PublicKey receiverPulicKey=receivekeyFactory.generatePublic(x509EncodeKeySpec);
			DHParameterSpec dhParameterSpec=((DHPublicKey)receiverPulicKey).getParams();
			KeyPairGenerator receiverKeyPairGenerator=KeyPairGenerator.getInstance("DH");
			receiverKeyPairGenerator.initialize(dhParameterSpec);
			KeyPair receiveKeyPair=receiverKeyPairGenerator.generateKeyPair();
			PrivateKey receivePrivateKey=receiveKeyPair.getPrivate();
			byte[] receivePublicKeyEnc=receiveKeyPair.getPublic().getEncoded();
		
			//3、秘钥构建
			KeyAgreement receiverKeyAggreement=KeyAgreement.getInstance("DH");
			receiverKeyAggreement.init(receivePrivateKey);
			receiverKeyAggreement.doPhase(receiverPulicKey, true);
			SecretKey receiveDesKey=receiverKeyAggreement.generateSecret("DES");
			
			KeyFactory senderKeyFactory=KeyFactory.getInstance("DH");
			x509EncodeKeySpec=new X509EncodedKeySpec(receivePublicKeyEnc);
			PublicKey senderPublicKey=senderKeyFactory.generatePublic(x509EncodeKeySpec);
			KeyAgreement senderKeyAgreement=KeyAgreement.getInstance("DH");
			senderKeyAgreement.init(senderKeyPair.getPrivate());
			senderKeyAgreement.doPhase(senderPublicKey, true);
			
			
			SecretKey senderDesKey=senderKeyAgreement.generateSecret("DES");
			if(Objects.equals(receiveDesKey, senderDesKey)){
				System.out.println("秘钥相等");
			}
			
			//4、加密
			Cipher cipher=Cipher.getInstance("DES");
			cipher.init(Cipher.ENCRYPT_MODE, senderDesKey);
			byte[] result=cipher.doFinal(src.getBytes());
			System.out.println("jdk dh encrypt:"+Base64.encodeBase64String(result));
			
			//5、解密
			cipher.init(Cipher.DECRYPT_MODE,receiveDesKey);
			result=cipher.doFinal(result);
			System.out.println("jdk dh decrypt:"+new String(result));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
