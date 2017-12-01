package com.zteict.tool.httpPost;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.ProxyHost;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.log4j.Logger;
import org.json.JSONObject;

public class SendPostRequest {

	private static Logger logger = Logger.getLogger(SendPostRequest.class);

	public static String sendHttpData(String url, String str) throws Exception {

		return sendHttpData(url, str, null, 0);
	}

	
	public static String sendHttpXMLData(String url, String str, String proxyIP,
			int proPort) throws Exception {

		logger.info("HTTP Request URL:" + url + ",HTTP Request PARAM:" + str);
		HttpClient client = new HttpClient();
		// client.getHostConfiguration().setProxy("10.41.70.8", 80);
		// client.getParams().setAuthenticationPreemptive(true);

		// 打开和URL之间的连接
		URL realUrl = new URL(url);
		URLConnection conn = realUrl.openConnection();

		if (proxyIP != null && !proxyIP.equals("")) {
			client.getHostConfiguration().setProxy(proxyIP, proPort);
			// client.getParams().setAuthenticationPreemptive(true);
		}

		PostMethod httpPost = new PostMethod(url);
		InputStream is = new java.io.ByteArrayInputStream(str.getBytes("utf-8"));
		client.setTimeout(60000);

		httpPost.setRequestHeader("Content-type", "text/xml;charset=UTF-8");
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
	
	public static String sendHttpData(String url, String str, String proxyIP,
			int proPort) throws Exception {

		logger.info("HTTP Request URL:" + url + ",HTTP Request PARAM:" + str);
		HttpClient client = new HttpClient();
		// client.getHostConfiguration().setProxy("10.41.70.8", 80);
		// client.getParams().setAuthenticationPreemptive(true);

		// 打开和URL之间的连接
		URL realUrl = new URL(url);
		URLConnection conn = realUrl.openConnection();

		if (proxyIP != null && !proxyIP.equals("")) {
			client.getHostConfiguration().setProxy(proxyIP, proPort);
			 client.getParams().setAuthenticationPreemptive(true);
		}

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

	
	

	
	public static String sendHttpDataWithToken(String url, String str,
			String token) throws Exception {

		logger.info("url:" + url + ",strParam:" + str + ",token:" + token);
		HttpClient client = new HttpClient();
		// client.getHostConfiguration().setProxy("10.41.70.8", 80);
		client.getParams().setAuthenticationPreemptive(true);
		// 如果代理需要密码验证，这里设置用户名密码
		// client.getState().setCredentials(new AuthScope("http://10.75.72.229",
		// 8080, null), new UsernamePasswordCredentials("admin",
		// "admin"));//10.75.72.229不是本机
		// client.getState().setProxyCredentials(AuthScope.ANY, new
		// UsernamePasswordCredentials("llying.iteye.com","llying"));
		PostMethod httpPost = new PostMethod(url);
		InputStream is = new java.io.ByteArrayInputStream(str.getBytes("utf-8"));
		client.setTimeout(5000);
		byte[] aa = null;
		httpPost.setRequestHeader("Content-type", "application/json");
		httpPost.setRequestHeader("Accept", "application/json");
		httpPost.setRequestHeader("X-Auth-Token", token);
		httpPost.setRequestBody(is);

		String responseData = null;
		try {
			Exception exception = null;
			client.executeMethod(httpPost);
			int resStatusCode = httpPost.getStatusCode();
			System.out.println("resStatusCode:" + resStatusCode);
			if (resStatusCode == HttpStatus.SC_OK || resStatusCode == 202) // 九洲云创建虚拟机接口返回202也是正确的
			{
				BufferedReader br = new BufferedReader(new InputStreamReader(
						httpPost.getResponseBodyAsStream(), "utf-8"));
				System.out.println(httpPost.getResponseCharSet());
				String res = null;
				StringBuffer sb = new StringBuffer();

				while ((res = br.readLine()) != null) {
					sb.append(res);
				}
				responseData = sb.toString();
				System.out.println("br.readLine()=" + responseData);
			} else {
				// System.out.println(httpPost.getStatusText());
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

		System.out.println("return:" + responseData);
		return responseData;
	}

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		// String VNCParams = "{\"os-getVNCConsole\": {\"type\": \"novnc\"}}";
		// jsonStr = AESEncrypt.enCrypt("", jsonStr,"123321");
		String url = "http://58.247.117.134:5000/v2.0/tokens";
		// String str =
		// "{\"LoginId\":\"admin\",\"Password\":\"C8837B23FF8AAA8A2DDE915473CE0991\"}";
		// String str =
		// "{\"auth\": {\"tenantName\": \"admin\", \"passwordCredentials\": {\"username\": \"admin\", \"password\": \"admin\"}}}";
		// String str =
		// "{\"auth\": {\"tenantName\": \"admin\", \"passwordCredentials\": {\"username\": \"admin\", \"password\": \"admin\"}}}";
		String str = "{\"auth\": {\"tenantName\": \"admin\", \"passwordCredentials\": {\"username\": \"admin\", \"password\": \"admin\" }}}";
		sendHttpData(url, str, null, 0);
	}

}