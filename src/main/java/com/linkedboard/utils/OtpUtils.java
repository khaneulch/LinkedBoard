package com.linkedboard.utils;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Date;
import java.util.Random;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base32;

public class OtpUtils {
	
	/**
	 * OTP 인증시 필요한 key를 생성한다.
	 * @return
	 */
	public static String generateKey() {
		byte buffer[] = new byte[5 + 5 * 5];
		new Random().nextBytes(buffer);
		
		Base32 codec = new Base32();
		byte secretKey[] = Arrays.copyOf(buffer, 10);
		byte encodedKey[] = codec.encode(secretKey);
		
		return new String(encodedKey);
	}
	
	/**
	 * OTP 인증번호를 확인한다.
	 * @param key
	 * @param otpValue
	 * @return
	 */
	public static boolean checkCode(String key, String otpValue) {
		long opt = Long.parseLong(otpValue);
		long wave = new Date().getTime() / 30000;
		
		boolean result = false;
		
		try {
			Base32 codec = new Base32();
			byte decodedKey[] = codec.decode(key);
			
			for( int i = -3; i <= 3; i++) {
				long hash = verifyCode(decodedKey, wave + i);
				if( hash == opt) return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * OTP 키를 받아온다.
	 * @param key
	 * @param otpValue
	 * @return
	 * @throws NoSuchAlgorithmException 
	 * @throws InvalidKeyException 
	 */
	public static long verifyCode(byte decodedKey[], long wave) throws Exception {
		byte data[] = new byte[8];
		
		long value = wave;
		
		for(int i = 8; i-- > 0; value >>>=8) {
			data[i] = (byte) value;
		}
		
		SecretKeySpec signKey = new SecretKeySpec(decodedKey, "HmacSHA1");
		Mac mac = Mac.getInstance("HmacSHA1");
        mac.init(signKey);
        
        byte[] hash = mac.doFinal(data);
        int offset = hash[20 - 1] & 0xF;
        
        long truncatedHash = 0;
        for (int i = 0; i < 4; ++i) {
            truncatedHash <<= 8;
            truncatedHash |= (hash[offset + i] & 0xFF);
        }
        
        truncatedHash &= 0x7FFFFFFF;
        truncatedHash %= 1000000;
 
        return (int) truncatedHash;
	}
}
