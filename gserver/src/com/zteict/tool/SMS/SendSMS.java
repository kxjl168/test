package com.zteict.tool.SMS;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Random;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.zteict.tool.SMS.szhm.SzSmsHelper;
import com.zteict.tool.config.ConfigReader;

public class SendSMS {
	// 日志记录对象
	private static Logger logger = Logger.getLogger(SendSMS.class);

	public static boolean sendSM(String mobile, String content) {
		return SzSmsHelper.sendSM(mobile, content);
	}

	/**
	 * 生成验证码
	 * 
	 * @param num
	 *            验证码位数
	 * @return
	 * @author zj
	 * @date 2015-12-25 上午9:50:05 *
	 */
	public static String generateValieCode(int num) {
		String code = "";

		Random r = new Random(new Date().getTime());

		for (int i = 0; i < num; i++) {
			code += r.nextInt(9);
		}

		return code;
	}

	public static boolean sendSM2(String mobile, String content) {

		String smsUrl = "http://www.xinlebao.com/ipay/f6ajax/com.zteict.ipay.base.business.BusinessCenter4SendMsg.thirdParty_sendMsg";
		String sn = "SDK-BBX-010-19919";
		String pwd = "ADADDB08DE9AB4051BB3DB591798CA07";

		// SysParameter p_url = SysConfigManager.getSingleInstance()
		// .getSysParamInfo("SMSUrl");
		// if (p_url !=
		// null&&p_url.getParam_value()!=null&&!p_url.getParam_value().equals(""))
		// smsUrl = p_url.getParam_value();

		smsUrl = ConfigReader.getInstance().getProperty("SMSUrl");

		// String smsUrl = SysparamConfig.getSMS_URL_NEW();
		if (null == smsUrl || "".equals(smsUrl)) {
			System.out.println("短信服务器地址未配置!");
		}

		String result = "";
		String msgfmt = "";
		String ext = "云盾";
		BufferedReader in = null;
		String rrid = "";
		String stime = "";
		try {
			content = URLEncoder.encode(content, "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String message = smsUrl + "?sn=" + sn + "&pwd=" + pwd + "&mobile="
				+ mobile + "&content=" + content + "&ext=" + ext + "&stime="
				+ stime + "&rrid=" + rrid + "&msgfmt=" + msgfmt;

		System.out.println(message);

		System.out.println("短信服务器地址:" + smsUrl);
		URL url;
		try {
			url = new URL(message);
			HttpURLConnection uRLConnection = (HttpURLConnection) url
					.openConnection();

			String proxyIP = ConfigReader.getInstance().getProperty("proxyIP");
			String proPortStr = ConfigReader.getInstance().getProperty(
					"proPort");
			int proPort = 80;
			if (proPortStr != null && !proPortStr.equals(""))
				proPort = Integer.parseInt(proPortStr);
			if (proxyIP != null && !proxyIP.equals("")) {
				Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(
						proxyIP, proPort));
				uRLConnection = (HttpURLConnection) url.openConnection(proxy);
			}

			uRLConnection.setDoInput(true);
			uRLConnection.setDoOutput(true);
			uRLConnection.setRequestMethod("POST");
			uRLConnection.setUseCaches(false);
			uRLConnection.setInstanceFollowRedirects(false);
			uRLConnection.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			uRLConnection.connect();

			DataOutputStream out = new DataOutputStream(
					uRLConnection.getOutputStream());
			String sendContent = "mobile=" + mobile + "&content=" + content;
			System.out.println("短信发送内容：" + sendContent);
			byte[] bytes = sendContent.getBytes("UTF-8");
			out.write(bytes);
			out.flush();
			out.close();

			InputStream is = uRLConnection.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String response = "";// {"result":"0","message":null,"returnMap":null,"success":true,"submit":false,"fail":false}
			String readLine = null;
			while ((readLine = br.readLine()) != null) {
				response = response + readLine;
			}
			is.close();
			br.close();
			uRLConnection.disconnect();
			System.out.println("短信服务器返回结果：" + response);
			// 将返回结果JSON化
			JSONObject jsonObj = new JSONObject();
			if (null != response && !"".equals(response)) {
				jsonObj = JSONObject.fromObject(response);
				String retCode = jsonObj.optString("result");
				if ("0".equals(retCode)) {
					return true;
				}
			}
			return false;
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static void main(String[] args) {
		// send((int)((Math.random()*9+1)*100000) +
		// " (i信验证码，5分钟内有效)此短信验证码只能用于绑定的i信账号，转发将可能导致你的账号被盗【e城市】",
		// "13815429446");

		String msg = "sfdx对的";
		String phone = "13815429446";

		sendSM(phone, msg);

		String url = "http://sdk2.zucp.net:8060/webservice.asmx/SendSMS";
		String data = "sn=SDK-BBX-010-19919&pwd=ADADDB08DE9AB4051BB3DB591798CA07&mobile=13815429446&content=sfd";

		// String
		// url="http://sdk2.zucp.net:8060/webservice.asmx?sn=SDK-BBX-010-19919&pwd=ADADDB08DE9AB4051BB3DB591798CA07&mobile="+phone+"&content="+msg+"&ext=云盾&stime=&rrid=&msgfmt=";
		try {
			// SendPostRequest.sendHttpData(url,data);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// sendSM("13815429446","xxx");
	}
}
