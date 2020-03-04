package com.bbd.blog.security;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MessageHash {
	private static String SALT = "GBlVmS14D7";
	
	public static String generateHash(String input) {
		try { 
            MessageDigest md = MessageDigest.getInstance("SHA-1"); 
            byte[] messageDigest = md.digest((SALT + input).getBytes()); 
            BigInteger no = new BigInteger(1, messageDigest); 
            String hashtext = no.toString(16); 
            while (hashtext.length() < 32) { 
                hashtext = "0" + hashtext; 
            } 
            return hashtext; 
        } 
        catch (NoSuchAlgorithmException e) { 
            throw new RuntimeException(e); 
        } 
		
	}
	
}
