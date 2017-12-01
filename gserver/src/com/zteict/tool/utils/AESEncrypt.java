package com.zteict.tool.utils;

import org.apache.log4j.Logger;


public class AESEncrypt {
	
	// 日志对象
	private static Logger logger = Logger.getLogger(AESEncrypt.class);
	
	/**
	 * 加密
	 * @param seq
	 * @param content
	 * @return
	 */
	public static String enCrypt(String seq, String content,String key){
		String result = null;
		try{
			//String key = "b2xkcGhvdG9vbGRwbGFjZWkmdQ==";
//			String key = "b2xkcGhvdG9vbGRw";
			result = AESEncryptUtil.cryptHex(content, key);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("字符串:"+content+"加密失败",e);
			return null;
		}
		return result;
	}
	
	/**
	 * 解密
	 * @param seq
	 * @param content
	 * @return
	 */
	public static String deCrypt(String seq, String content,String key){
		String result = null;
		try{
			//String key = "b2xkcGhvdG9vbGRwbGFjZWkmdQ==";
//			String key = "b2xkcGhvdG9vbGRw";
			result = AESEncryptUtil.decryptHex(content, key);
			result = delStrDle(result);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("字符串:"+content+"解密失败",e);
			return null;
		}
		return result;
	}
	
	public static String delStrDle(String str){
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < str.length(); i++) {
			int c = (int)str.charAt(i);
			if (c != 16) {
				sb.append((char)c);
			}
		}
		return sb.toString();
	}
	public static void main(String[] args){
		String Result ="7d9151e88bc0a62fb0c25ba63358cf843cf7f142ee1412f2ce1f92af73f473e16e61e1168feff15920540e63209ecd5801359ba5846815e4e414179317bc1e1c63189ca57c73728b6149fe278f209dc76167d4122bce9b669194eefc2e7a6a25f14e7803e659d6b02a577563c1b15126f1b44a1edadaadd19788f027395d46f68a97748d99bda0685a55bdc873b9d14c82c637585c46b04a9bb86b4d4360f1de9ddd91adcc9280e7236df4be079123c499840a011c736d9412b4fe50eacab1913e6dcffe20907f2845a2735160696d2653cc18f648a236783e2c88c3e132194f00b914e654d89f83e178a06e5782a1c79f0ea948a6a76796ea18552255859916771c41d511d00b03608c79546e21e75a81258a8c8dbfd1cbfe0c226faf5d3e4a39496cc847322c866da6550b9e9ed04e795b9f01bb581a4a1b8418f862cc60e735d0d4235c1d952a6247633cc1f2ee8176c70911565a45c49ae27276c43548f5e66405c8f2cf436d65bc81ee746382f8";
		String key = "C8837B23FF8AAA8A2DDE915473CE0991".substring(0,16);
//		String encryptStr = enCrypt("", toEncryptStr, key);
		String deResult = deCrypt("",Result,"b2xkcGhvdG9vbGRw");
		
		String res = deCrypt("", deResult, key);
//		System.out.println(encryptStr);
		System.out.println("原始key解密"+deResult);
		System.out.println("密码前16位"+res);
	}
}