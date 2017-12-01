package com.zteict.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.log4j.Logger;



public class test {
	
	
	public static void main(String args[])
	{
		testLogin();
		
		//testLoginOut();
		
		//testRegister();
		
		
		
	}
	
	private static void testLoginOut() {

		String url = "http://127.0.0.1:8080/h264s/h/app/logout";

		String json = "{\"deviceid\": \"xxx2\",\"userid\": \"t1\"}";

		String data = json;

		String responsedata = "";
		try {
			responsedata = sendHttpData(url, data);

			System.out.println("返回:" + responsedata);
		//	System.out.println("解密:" + out);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println(responsedata);
		// ***********vmInstallApp*****************

	}
	
	private static void testRegister() {

		String url = "http://127.0.0.1:8080/h264s/h/app/register_device";

		String json = "{\"deviceid\": \"xxx2\",\"ip\": \"192.168.2.112\",\"port\": \"9998\"}";

		String data = json;

		String responsedata = "";
		try {
			responsedata = sendHttpData(url, data);

			System.out.println("返回:" + responsedata);
		//	System.out.println("解密:" + out);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println(responsedata);
		// ***********vmInstallApp*****************

	}
	
	/**
	 * 
	 * @param map
	 * @return
	 * @author zj
	 * @date 2017-11-14
	 */

	private static void testLogin() {

		//String url = "http://127.0.0.1:8080/h264s/h/app/login";
		
		String url = "http://10.204.37.192:8080/h264/h/app/login";

		String json = "{\"userid\": \"t1\",\"pass\": \"123321\"}";

		String data = json;

		String responsedata = "";
		try {
			responsedata = sendHttpData(url, data);

			System.out.println("返回:" + responsedata);
		//	System.out.println("解密:" + out);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println(responsedata);
		// ***********vmInstallApp*****************

	}

	private static Logger logger = Logger.getLogger(test.class);

	public static String sendHttpData(String url, String str) throws Exception {

		logger.info("HTTP Request URL:" + url + ",HTTP Request PARAM:" + str);
		HttpClient client = new HttpClient();
		// client.getHostConfiguration().setProxy("10.41.70.8", 80);
		// client.getParams().setAuthenticationPreemptive(true);

		PostMethod httpPost = new PostMethod(url);
		InputStream is = new java.io.ByteArrayInputStream(str.getBytes("utf-8"));
		client.setTimeout(60000);

		httpPost.setRequestHeader("Content-type", "application/json");
		httpPost.setRequestHeader("Accept", "application/json");
		httpPost.setRequestHeader("Connection", "close");
		// httpPost.setRequestHeader("Authorization", "Basic YWRtaW46MTIz");
		httpPost.setRequestBody(is);

		String responseData = null;
		try {
			Exception exception = null;
			client.executeMethod(httpPost);
			int resStatusCode = httpPost.getStatusCode();
			if (resStatusCode == HttpStatus.SC_OK) {
				BufferedReader br = new BufferedReader(new InputStreamReader(
						httpPost.getResponseBodyAsStream(), "utf-8"));
				logger.info("HTTP Request CHARSET:"
						+ httpPost.getResponseCharSet());
				String res = null;
				StringBuffer sb = new StringBuffer();
				while ((res = br.readLine()) != null) {
					sb.append(res);
				}
				responseData = sb.toString();
			} else {
				logger.error("http请求失败 " + resStatusCode + ":"
						+ httpPost.getStatusText());
				exception = new Exception("[SerialHttpSender] HttpErrorCode:"
						+ resStatusCode);
			}
			if (exception != null) {
				throw exception;
			}
		} catch (java.net.ConnectException ex) {
			ex.printStackTrace();
			throw ex;
		} catch (IOException ex) {
			ex.printStackTrace();
			// org.apache.commons.httpclient.HttpRecoverableException:
			// java.net.SocketTimeoutException: Read timed out

			String message = ex.getMessage();
			if (message != null
					&& message.toLowerCase().indexOf("read timed") > -1) {
				throw new Exception(ex.getMessage());
			} else {
				ex.printStackTrace();
				throw ex;
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;

		} finally {
			httpPost.releaseConnection();

		}

		logger.info("HTTP Request Result:" + responseData);
		return responseData;
	}
}
