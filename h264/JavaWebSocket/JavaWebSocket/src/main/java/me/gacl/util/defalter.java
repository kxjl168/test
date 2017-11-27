package me.gacl.util;

import java.util.Arrays;
import java.util.Base64;

import java.util.zip.Deflater;
import java.util.zip.Inflater;



public class defalter {
	/**
	 * 
	 * @param map
	 * @return
	 * @author zj
	 * @date 2017-11-9
	 */

	/**
	 * 用zlib压缩
	 * 
	 * @param message
	 * @return
	 * @throws Exception
	 */
	public static byte[] zlibCompress(String message) throws Exception {
		String chatacter = "UTF-8";
		byte[] input = message.getBytes(chatacter);
		System.out.println("input length " + input.length);
		byte[] output = new byte[input.length + 10
				+ new Double(Math.ceil(input.length * 0.25f)).intValue()];
		System.out.println(output.length);
		Deflater compresser = new Deflater();
		compresser.setInput(input);
		compresser.finish();
		int compressedDataLength = compresser.deflate(output);
		System.out.println("compressedDataLength " + compressedDataLength);
		compresser.end();
		// String ostr=new String(output,0,compressedDataLength,chatacter);
		// System.out.println("out str "+ostr);
		return Arrays.copyOf(output, compressedDataLength);
	}

	/**
	 * 解压缩
	 * 
	 * @param barr
	 *            须要解压缩的字节数组
	 * @param charater
	 *            相应压缩时的编码方式
	 * @return
	 * @throws Exception
	 */
	public static byte[] zlibInfCompress(byte[] barr, String charater)
			throws Exception {
		byte[] result = new byte[2014];
		Inflater inf = new Inflater();
		inf.setInput(barr);
		int infLen = inf.inflate(result);
		inf.end();
		String strOgr = new String(result, charater);
		System.out.println("str ogr " + strOgr);
		return Arrays.copyOf(result, infLen);
	}

	public static void main(String[] args) throws Exception {

		String str = "abcde|qqqqqqqqqqqqqqq|wwwwwwwwwwwwwwwwwwww";
		// 方法一：
		/*
		 * byte[] def=StringZlibUtil.zlibCompress(str); byte[]
		 * inf=StringZlibUtil.zlibInfCompress(def, "UTF-8"); String strOgr=new
		 * String(inf,"UTF-8"); System.out.println("str org main "+strOgr);
		 */

		// 方法二：下面是使用基本base64的编码与解码
		byte[] def = zlibCompress(str);
		String strBase =org.apache.tomcat.util.codec.binary.Base64.encodeBase64String(def);
		//String strBase = new String(def);
		System.out.println("str base64 string " + strBase);
		byte[] decStr = strBase.getBytes();
		byte[] decode_str = zlibInfCompress(decStr, "UTF-8");
		String decStrOgr = new String(decode_str, "UTF-8");
		System.out.println("decStrOgr " + decStrOgr);
	}
}
