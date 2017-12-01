package com.zteict.tool.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;


public class AESEncryptUtil {
	
	public static String crypt(String content, String key) {
		String result = null;
		try {
			Cipher c = Cipher.getInstance("AES/ECB/PKCS5Padding");
			//key = keyToHex(key);
			SecretKeySpec k = new SecretKeySpec(key.getBytes(), 0, 16, "AES");
			c.init(Cipher.ENCRYPT_MODE, k);
			result = Base64.encode(c.doFinal(content.getBytes()));
		} catch (Exception e) {
			result = null;
		}
		return result;
	}
	
	/**
	 * 解密
	 * @param content
	 * @param key
	 * @return
	 */
	public static String decrypt(byte[] content, String key) {
		String result = null;
		try {
			Cipher c = Cipher.getInstance("AES/ECB/PKCS5Padding");
			//key = keyToHex(key);
			SecretKeySpec k = new SecretKeySpec(key.getBytes("utf-8"), 0, 16, "AES");
			c.init(Cipher.DECRYPT_MODE, k);
			System.out.println(c.doFinal(content));
			result = new String(c.doFinal(content),"utf-8");
		} catch (Exception e) {
			result = null;
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 加密
	 * @param content
	 * @param key
	 * @return
	 */
	public static byte[] encryptB(String content, String key) {
		byte[] result = null;
		try {
			Cipher c = Cipher.getInstance("AES/ECB/PKCS5Padding");
			//key = keyToHex(key);
			SecretKeySpec k = new SecretKeySpec(key.getBytes("utf-8"), 0, 16, "AES");
			c.init(Cipher.ENCRYPT_MODE, k);
			result = c.doFinal(content.getBytes("utf-8"));
		} catch (Exception e) {
			result = null;
		}
		return result;
	}
	
	public static String cryptHex(String content, String key) {
		return byte2HexStr(encryptB(content, key));
	}
	
	public static String decryptHex(String content, String key) {
		return decrypt(hexStr2Bytes(content), key);
	}
	
	/**
	 * 
	 * @param src
	 * @return
	 */
	public static byte[] hexStr2Bytes(String src) {
		int m = 0, n = 0;
		int l = src.length() / 2;
		//System.out.println(l);
		byte[] ret = new byte[l];
		for (int i = 0; i < l; i++) {
			m = i * 2 + 1;
			n = m + 1;
			ret[i] = (byte)( Integer.parseInt(src.substring(i * 2, m) + src.substring(m, n), 16));
		}
		return ret;
	}

	public static String byte2HexStr(byte[] b) {
		String stmp = "";
		StringBuilder sb = new StringBuilder("");
		for (int n = 0; n < b.length; n++) {
			stmp = Integer.toHexString(b[n] & 0xFF);
			sb.append((stmp.length() == 1) ? "0" + stmp : stmp);
		}
		return sb.toString().toLowerCase().trim();
	}

	private final static char[] hexDigits = { '0', '1', '2', '3', '4', '5',
			'6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

	private static String bytesToHex(byte[] bytes) {
		StringBuffer sb = new StringBuffer();
		int t;
		for (int i = 0; i < 16; i++) {
			t = bytes[i];
			if (t < 0)
				t += 256;
			sb.append(hexDigits[(t >>> 4)]);
			sb.append(hexDigits[(t % 16)]);
		}
		return sb.toString();
	}

	public static String md5(String input) throws Exception {
		return code(input, 32);
	}
	
	public static String md516(String input) throws Exception {
		return code(input, 16).toLowerCase();
	}

	public static String code(String input, int bit) throws Exception {
		try {
			MessageDigest md = MessageDigest.getInstance(System.getProperty(
					"MD5.algorithm", "MD5"));
			if (bit == 16)
				return bytesToHex(md.digest(input.getBytes("utf-8")))
						.substring(8, 24);
			return bytesToHex(md.digest(input.getBytes("utf-8")));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			throw new Exception("Could not found MD5 algorithm.", e);
		}
	}

	public static String md5_3(String b) throws Exception {
		MessageDigest md = MessageDigest.getInstance(System.getProperty(
				"MD5.algorithm", "MD5"));
		byte[] a = md.digest(b.getBytes());
		a = md.digest(a);
		a = md.digest(a);

		return bytesToHex(a);
	}
	 private final static String HEX = "0123456789ABCDEF";
    private static String keyToHex(String key) {
        String newKey = key;
        if (key != null && key.length() < HEX.length()) {
            // 左对�?16字符,不足用空格补�?
            newKey = String.format("%-16s", key);
        } else {
            newKey = key.substring(0, 16);
        }
        return newKey;
    }
}
