package com.zteict.tool.httpPost;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import com.zteict.tool.config.ConfigReader;
import com.zteict.tool.utils.JsonUtil;

/*
 模拟表单http上传文件
 * @date 2016-1-21
 * @author zj
 */

public class HttpPostEmulator {
	// 每个post参数之间的分隔。随意设定，只要不会和其他的字符串重复即可。
	private static final String boundary = "----------HV2ymHFg03ehbqgZCaKO6jyH";

	private static final String lineEnd = "\r\n";
	private static final  String twoHyphens = "--";
	//private static final  String boundary = "*****";
	
	public static String sendHttpPostRequest(String serverUrl,
			ArrayList<FormFieldKeyValuePair> generalFormFields,
			ArrayList<UploadFileItem> filesToBeUploaded) throws Exception {
		// 向服务器发送post请求
		URL url = new URL(serverUrl/* "http://127.0.0.1:8080/test/upload" */);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();

		// 发送POST请求必须设置如下两行
		connection.setDoOutput(true);
		connection.setDoInput(true);
		connection.setUseCaches(false);
		connection.setRequestMethod("POST");
		connection.setRequestProperty("Connection", "Keep-Alive");
		connection.setRequestProperty("Charset", "UTF-8");
		connection.setRequestProperty("Content-Type",
				"multipart/form-data; boundary=" + boundary);


		OutputStream out = connection.getOutputStream();
		
	

		// 1. 处理普通表单域(即形如key = value对)的POST请求
		for (FormFieldKeyValuePair ffkvp : generalFormFields) {
			out.write((twoHyphens + boundary + lineEnd).getBytes("utf-8"));
			out.write(("Content-Disposition: form-data; name=\""+ffkvp.getKey()+"\""+ lineEnd).getBytes("utf-8"));
			out.write((lineEnd).getBytes("utf-8"));
			out.write((ffkvp.getValue()).getBytes("utf-8"));
			out.write((lineEnd).getBytes("utf-8"));
			
			
		}
		
		// 2. 处理文件上传
		for (UploadFileItem ufi : filesToBeUploaded) {
		
			out.write((twoHyphens + boundary + lineEnd).getBytes("utf-8"));
			out.write(("Content-Disposition: form-data; name=\""+ufi.getFormFieldName()+"\";filename=\"" + ufi.getFileName() +"\"" + lineEnd).getBytes("utf-8"));
			out.write(("Content-Type:application/octet-stream"+lineEnd).getBytes("utf-8"));
			out.write((lineEnd).getBytes("utf-8"));

			// 开始真正向服务器写文件
			File file = new File(ufi.getFileName());
			DataInputStream dis = new DataInputStream(new FileInputStream(file));
			int bytes = 0;
			byte[] bufferOut = new byte[(int) file.length()];
			bytes = dis.read(bufferOut);
			out.write(bufferOut, 0, bytes);
			dis.close();
		
			out.write((lineEnd).getBytes("utf-8"));
	
			
		}

		// 3. 写结尾
		out.write((twoHyphens + boundary + twoHyphens + lineEnd).getBytes("utf-8"));
		out.flush();
		out.close();

		// 4. 从服务器获得回答的内容
		String strLine = "";
		String strResponse = "";

		InputStream in = connection.getInputStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		while ((strLine = reader.readLine()) != null) {
			strResponse += strLine + "\n";
		}
		// System.out.print(strResponse);

		return strResponse;
	}
	
	
	/**
	 * 转发文件上传请求 ，
	 * @param serverUrl
	 * @param generalFormFields
	 * @param receiveFiles  文件 form-data;name  固定为 file
	 * @return
	 * @throws Exception
	 * @date 2016-7-12
	 * @author zj
	 */
	public static String sendHttpPostRequestByMutiPartFile(String serverUrl,
			ArrayList<FormFieldKeyValuePair> generalFormFields,
			MultipartFile[] receiveFiles) throws Exception {
		// 向服务器发送post请求
		URL url = new URL(serverUrl/* "http://127.0.0.1:8080/test/upload" */);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();

		
		connection.setChunkedStreamingMode(1024 * 1024 * 10);
		
		
		// 发送POST请求必须设置如下两行
		connection.setDoOutput(true);
		connection.setDoInput(true);
		connection.setUseCaches(false);
		connection.setRequestMethod("POST");
		connection.setRequestProperty("Connection", "Keep-Alive");
		connection.setRequestProperty("Charset", "UTF-8");
		connection.setRequestProperty("Content-Type",
				"multipart/form-data; boundary=" + boundary);


		OutputStream out = connection.getOutputStream();
		
	

		// 1. 处理普通表单域(即形如key = value对)的POST请求
		for (FormFieldKeyValuePair ffkvp : generalFormFields) {
			out.write((twoHyphens + boundary + lineEnd).getBytes("utf-8"));
			out.write(("Content-Disposition: form-data; name=\""+ffkvp.getKey()+"\""+ lineEnd).getBytes("utf-8"));
			out.write((lineEnd).getBytes("utf-8"));
			out.write((ffkvp.getValue()).getBytes("utf-8"));
			out.write((lineEnd).getBytes("utf-8"));
			
			
		}
		// 2. 处理文件上传
		for (int i = 0; i < receiveFiles.length; i++) {
			

			MultipartFile mfile = receiveFiles[i];

			out.write((twoHyphens + boundary + lineEnd).getBytes("utf-8"));
			out.write(("Content-Disposition: form-data; name=\"file\";filename=\"" + mfile.getOriginalFilename() +"\"" + lineEnd).getBytes("utf-8"));
			out.write(("Content-Type:application/octet-stream"+lineEnd).getBytes("utf-8"));
			out.write((lineEnd).getBytes("utf-8"));
			InputStream dis = mfile.getInputStream();
			int bytes = 0;
			byte[] bufferOut = new byte[(int) mfile.getSize()];
			bytes = dis.read(bufferOut);
			out.write(bufferOut, 0, bytes);
			dis.close();
			//mfile.getInputStream();
			//out.write(mfile.getBytes(), 0, (int)mfile.getSize());
		
			out.write((lineEnd).getBytes("utf-8"));			

		}

		// 3. 写结尾
		out.write((twoHyphens + boundary + twoHyphens + lineEnd).getBytes("utf-8"));
		out.flush();
		out.close();

		// 4. 从服务器获得回答的内容
		String strLine = "";
		String strResponse = "";

		InputStream in = connection.getInputStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		while ((strLine = reader.readLine()) != null) {
			strResponse += strLine + "\n";
		}
		// System.out.print(strResponse);

		return strResponse;
	}
	
	
	/**
	 * 转发文件上传请求 ，
	 * @param serverUrl
	 * @param generalFormFields
	 * @param receiveFiles  文件 form-data;name  固定为 file
	 * @return
	 * @throws Exception
	 * @date 2016-7-12
	 * @author zj
	 */
	public static String sendHttpPostRequestByInputStream(String serverUrl,
			ArrayList<FormFieldKeyValuePair> generalFormFields,
			InputStream receiveFiles, int byteSize, String fileName) throws Exception {
		// 向服务器发送post请求
		URL url = new URL(serverUrl/* "http://127.0.0.1:8080/test/upload" */);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();

		// 发送POST请求必须设置如下两行
		connection.setDoOutput(true);
		connection.setDoInput(true);
		connection.setUseCaches(false);
		connection.setRequestMethod("POST");
		connection.setRequestProperty("Connection", "Keep-Alive");
		connection.setRequestProperty("Charset", "UTF-8");
		connection.setRequestProperty("Content-Type",
				"multipart/form-data; boundary=" + boundary);


		OutputStream out = connection.getOutputStream();
		
	

		// 1. 处理普通表单域(即形如key = value对)的POST请求
		for (FormFieldKeyValuePair ffkvp : generalFormFields) {
			out.write((twoHyphens + boundary + lineEnd).getBytes("utf-8"));
			out.write(("Content-Disposition: form-data; name=\""+ffkvp.getKey()+"\""+ lineEnd).getBytes("utf-8"));
			out.write((lineEnd).getBytes("utf-8"));
			out.write((ffkvp.getValue()).getBytes("utf-8"));
			out.write((lineEnd).getBytes("utf-8"));
			
			
		}
		// 2. 处理文件上传
		//for (int i = 0; i < receiveFiles.length; i++) {
			

			//File mfile = receiveFiles[i];

			out.write((twoHyphens + boundary + lineEnd).getBytes("utf-8"));
			out.write(("Content-Disposition: form-data; name=\"file\";filename=\"" +fileName +"\"" + lineEnd).getBytes("utf-8"));
			out.write(("Content-Type:application/octet-stream"+lineEnd).getBytes("utf-8"));
			out.write((lineEnd).getBytes("utf-8"));
			InputStream dis =receiveFiles;
			int bytes = 0;
			byte[] bufferOut = new byte[byteSize];
			bytes = dis.read(bufferOut);
			out.write(bufferOut, 0, bytes);
			dis.close();
			//mfile.getInputStream();
			//out.write(mfile.getBytes(), 0, (int)mfile.getSize());
		
			out.write((lineEnd).getBytes("utf-8"));			

		//}

		// 3. 写结尾
		out.write((twoHyphens + boundary + twoHyphens + lineEnd).getBytes("utf-8"));
		out.flush();
		out.close();

		// 4. 从服务器获得回答的内容
		String strLine = "";
		String strResponse = "";

		InputStream in = connection.getInputStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		while ((strLine = reader.readLine()) != null) {
			strResponse += strLine + "\n";
		}
		// System.out.print(strResponse);

		return strResponse;
	}
}
