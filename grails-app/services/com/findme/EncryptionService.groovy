package com.findme

import grails.transaction.Transactional

import java.security.Key

import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

import org.apache.commons.codec.binary.Hex


@Transactional
class EncryptionService {
	
	private static final String ALGO = "AES";
	static final def keyValue ="thebestsecretkey".getBytes()
	 
	public static String encrypt(String Data) throws Exception {
	Key key = generateKey();
	Cipher c = Cipher.getInstance(ALGO);
	c.init(Cipher.ENCRYPT_MODE, key);
	byte[] encVal = c.doFinal(Data.getBytes("UTF-8"));
	return new String(Hex.encodeHex(encVal));
	}
	 
	public static String decrypt(String encryptedData) throws Exception {
	Key key = generateKey();
	Cipher c = Cipher.getInstance(ALGO);
	c.init(Cipher.DECRYPT_MODE, key);
	// encryptedData=new String()//unhex
	byte[] decValue = c.doFinal(Hex.decodeHex(encryptedData.toCharArray()));
	return new String(decValue);
	}
	 
	private static Key generateKey() throws Exception {
	Key key = new SecretKeySpec(keyValue, ALGO);
	return key;
	}
	 
	
	 
	//String encryptedData=AESCryption.encrypt("testname")
	 
	//System.out.println("encryptedData="+encryptedData);
	 
	//System.out.println("decrypted="+AESCryption.encrypt(encryptedData);
	 
	
}
