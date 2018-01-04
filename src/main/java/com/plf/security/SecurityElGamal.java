package com.plf.security;

import java.security.AlgorithmParameterGenerator;
import java.security.AlgorithmParameters;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Security;

import javax.crypto.spec.DHParameterSpec;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class SecurityElGamal {

	//private static String src="security ElGamal";
	public static void main(String[] args) {
		jdkEIGamal();
	}
	
	public static void jdkEIGamal(){
		try {
			//公钥加密，私钥解密
			Security.addProvider(new BouncyCastleProvider());
			
			//1、初始化秘钥
			AlgorithmParameterGenerator algorithmParameterGenerator=AlgorithmParameterGenerator.getInstance("ElGamal");
			algorithmParameterGenerator.init(256);
			AlgorithmParameters algorithmParameters=algorithmParameterGenerator.generateParameters();
			DHParameterSpec dhParameterSpec=algorithmParameters.getParameterSpec(DHParameterSpec.class);
			KeyPairGenerator  keyPairGenerator=KeyPairGenerator.getInstance("ElGamal");
			keyPairGenerator.initialize(dhParameterSpec,new SecureRandom());
			KeyPair keyPair=keyPairGenerator.generateKeyPair();
			PublicKey eiGamalPublicKey=keyPair.getPublic();
			PrivateKey eiGamalPrivateKey=keyPair.getPrivate();
			System.out.println("Public Key:"+Base64.encodeBase64String(eiGamalPublicKey.getEncoded()));
			System.out.println("Private Key:"+Base64.encodeBase64String(eiGamalPrivateKey.getEncoded()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
