package com.EasyTravel.common;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class EncryptUtils {
	
	public static String createSalt() throws NoSuchAlgorithmException {
		SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
		byte[] bytes = new byte[16];
		random.nextBytes(bytes);
		
		String salt = new String(Base64.getEncoder().encode(bytes));
		
		return salt;
	}
	
	
	public static String encrypt(String password, String salt) {
		MessageDigest md;
		
		try {
			md = MessageDigest.getInstance("SHA-256");
			
			String passwordWithSalt = password + salt;
			
			md.update(passwordWithSalt.getBytes());
			
			byte[] byteData = md.digest();
			
			StringBuffer hexString = new StringBuffer();
			
			for(int i = 0; i < byteData.length; ++i) {
				String hex = Integer.toHexString(255 & byteData[i]);
				
				if(hex.length() == 1) {
					hexString.append('0');
				}
				hexString.append(hex);
			}
			return hexString.toString();
			
			
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException();
		}
		
		
	}
	
	
}
