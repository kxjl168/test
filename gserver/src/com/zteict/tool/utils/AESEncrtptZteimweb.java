package com.zteict.tool.utils;

import org.apache.log4j.Logger;


public class AESEncrtptZteimweb {
	
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
			result = AESEncrycptUtilZteimweb.cryptHex(content, key);
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
			result = AESEncrycptUtilZteimweb.decryptHex(content, key);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("字符串:"+content+"解密失败",e);
			return null;
		}
		return result;
	}
	
	public static void main(String[] args){
		String Result ="ac95162f630b6e7403968c21d1ac693c66db8f88f8492824ecde7a60346843eeae138d2b6e6a0c0f720e56327a57d8cea88ceb5755691fbbd726d62606465b8060dbe02cbac38d689ecdd1aca07f8596296ab1be21932fc32ccef06fe5c82936";
		String key = "123321";
		String toEncryptStr = "{\"ReadRule\":\"1\",\"ReadRange\":\"1\",\"FileType\":\"1\",\"TerminalType\":\"1\",\"SendTime\":\"0\",\"UID\":\"1\"}";
//		String encryptStr = enCrypt("", toEncryptStr, key);
		String deResult = deCrypt("","5a2e997c968fae936f63b89b53290c2c353f78597901354f3f5a400323914eb4","123321");
//		System.out.println(encryptStr);
		System.out.println(deResult);
	}
}