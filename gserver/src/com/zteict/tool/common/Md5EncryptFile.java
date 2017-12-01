package com.zteict.tool.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;

import org.springframework.web.multipart.MultipartFile;

public class Md5EncryptFile {
	/**
	 * 对文件全文生成MD5摘要
	 * 
	 * @param file 要加密的文件
	 * @return MDt摘要码
	 */
	static char hexdigits[] = {'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'};
	
	
	public static String getMD5(MultipartFile file)
	{
		InputStream fis = null;
		try
		{
			MessageDigest md = MessageDigest.getInstance("MD5");
			fis =  file.getInputStream();
			byte[] buffer = new byte[2048];
			int length = -1;
			long s = System.currentTimeMillis();
			
			while( (length = fis.read(buffer)) != -1)
			{
				md.update(buffer, 0, length);
			}
			byte[] b= md.digest();
			return byteToHexString(b);
			//16位加密
			//return buf.toString().substring(8, 24);
		}catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}finally
		{
			try
			{
				fis.close();
			}catch(IOException ex)
			{
				ex.printStackTrace();
			}
		}
	}
	
	
	
	public static String getMD5(File file)
	{
		FileInputStream fis = null;
		try
		{
			MessageDigest md = MessageDigest.getInstance("MD5");
			fis = new FileInputStream(file);
			byte[] buffer = new byte[2048];
			int length = -1;
			long s = System.currentTimeMillis();
			
			while( (length = fis.read(buffer)) != -1)
			{
				md.update(buffer, 0, length);
			}
			byte[] b= md.digest();
			return byteToHexString(b);
			//16位加密
			//return buf.toString().substring(8, 24);
		}catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}finally
		{
			try
			{
				fis.close();
			}catch(IOException ex)
			{
				ex.printStackTrace();
			}
		}
	}
	
	/**
	 * 把byte[]数组转换成十六进制字符串表示形式
	 * @param tmp 要转换的byte[]
	 * @return 十六进制字符串表示形式
	 */
	private static String byteToHexString(byte[] tmp)
	{
		String s;
		//用字节表示就是 16 个字节
		char str[] = new char[16 * 2];//每个字节用16进制表示的话，使用两个字符,所以表示成16进制需要32个字符
		int k = 0;//表示转换结果中对应的字符位置
		
		//从第一个字节开始，对MD5的每一个字节转换成16进制字符的转换
		for(int i = 0; i < 16; i++)
		{
			byte byte0 = tmp[i];//取第i个字节
			str[k++] = hexdigits[byte0 >>> 4 & 0xf];//取字节中高4位的数字转换,为逻辑右移，将符合位一起右移
			str[k++] = hexdigits[byte0 & 0xf];//取字节中低4为的数字转换
		}
		
		s = new String(str);//换后的结果转换为字符串
		return s;
	}
	
	public static void main(String[] args)
	{
//		System.out.println(getMD5(new File("f:/a.docx")));
		System.out.println(getMD5(new File("C:\\Users\\Administrator\\Desktop\\iXinV3.8.1.1_incre(10211900).exe")));
	}
}
