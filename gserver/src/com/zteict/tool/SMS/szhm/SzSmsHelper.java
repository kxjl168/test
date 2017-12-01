package com.zteict.tool.SMS.szhm;

import java.io.UnsupportedEncodingException;
import java.util.Random;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;

import com.sun.xml.internal.ws.util.xml.XmlUtil;
import com.zteict.tool.config.ConfigReader;
import com.zteict.tool.httpPost.HttpNormalReq;
import com.zteict.tool.httpPost.SendPostRequest;
import com.zteict.tool.utils.Base64;
import com.zteict.tool.utils.StringUtil;

public class SzSmsHelper {

	private static Logger logger = Logger.getLogger(SzSmsHelper.class);

	/*
	 * 
	 * @date 2016-8-16
	 * 
	 * @author zj
	 */

	public static String passport_encrypt(String txt, String key)
			throws UnsupportedEncodingException {
		Random rnd = new Random(100);
		Integer Integer_encrypt_key = rnd.nextInt(32000);
		String encrypt_key = Integer_encrypt_key.toString();
		Integer ctr = 0;
		String tmp = "";
		byte[] encodebyte = new byte[txt.length()];
		for (Integer i = 0; i < txt.length(); i++) {
			ctr = ctr == encrypt_key.length() ? 0 : ctr;
			char prefix = encrypt_key.charAt(ctr);
			char left = txt.charAt(i);
			char right = encrypt_key.charAt(ctr++);
			encodebyte[i] = (byte) (left ^ right);
			char[] block = new char[2];
			block[0] = prefix;
			block[1] = (char) encodebyte[i];
			tmp += new String(block);
		}
		String complicatedString = passport_key(tmp, key);
		byte[] inputbytes = complicatedString.getBytes("utf-8");

		String String_64 = Base64.encode(inputbytes);// Convert.ToBase64String(inputbytes,
														// 0,inputbytes.length());

		return String_64;
	}

	public static String passport_key(String txt, String encrypt_key)
			throws UnsupportedEncodingException {
		// encrypt_key = System.Web.Security.FormsAuthentication
		// .HashPasswordForStoringInConfigFile(encrypt_key, "MD5")
		// .toString().ToLower();
		//
		encrypt_key = DigestUtils.md5Hex(encrypt_key);

		Integer ctr = 0;
		String tmp = "";
		byte[] encodebyte = new byte[txt.length()];
		for (Integer i = 0; i < txt.length(); i++) {
			ctr = ctr == encrypt_key.length() ? 0 : ctr;
			char left = txt.charAt(i);
			char right = encrypt_key.charAt(ctr++);
			encodebyte[i] = (byte) (left ^ right);
		}
		// tmp = System.Text.Encoding.Default.GetString(encodebyte);
		tmp = new String(encodebyte, "utf-8");// encodebyte.getBytes("utf-8");
		return tmp;
	}

	public static String StringToXML(String str) {
		if (StringUtil.isNull(str))
			return "";

		String retVal = str;
		retVal = retVal.replaceAll("&", "&amp;");
		retVal = retVal.replaceAll("<", "&lt;");
		retVal = retVal.replaceAll(">", "&gt;");
		retVal = retVal.replaceAll("\"", "&quot;");
		retVal = retVal.replaceAll("'", "&apos;");

		return retVal;
	}

	public static String XMLtoString(String str) {
		if (StringUtil.isNull(str))
			return "";

		String retVal = str;
		retVal = retVal.replaceAll("&amp;", "&");
		retVal = retVal.replaceAll("&lt;", "<");
		retVal = retVal.replaceAll("&gt;", ">");
		retVal = retVal.replaceAll("&quot;", "\"");
		retVal = retVal.replaceAll("&apos;", "'");

		return retVal;
	}

	public static void main(String[] args) {
		test();
	}

	public static boolean sendSM(String phone, String content) {

		boolean sendrst = false;
		String phoneNum = phone;// "13815429446,";
		String phoneContent = content;// "^_^验证码1111";

		String sendBody = "<sendbody><message><orgaddr></orgaddr><mobile>"
				+ phoneNum
				+ "</mobile><content>"
				+ StringToXML(phoneContent)
				+ "</content><needreport>0</needreport><sendtime></sendtime></message><publicContent></publicContent></sendbody>";
		
		logger.info("sendSM：phoneNum-"+phoneNum+",phoneContent-"+phoneContent);

		try {

		String sms_user	=ConfigReader.getInstance()
			.getProperty("SMSUser");
		String sms_pass	=	ConfigReader.getInstance()
			.getProperty("SMSPass");
			
			String user = passport_encrypt(sms_user, "chinagdn");// passport_encrypt("244", "chinagdn");
			String pass = passport_encrypt(sms_pass, "chinagdn");//passport_encrypt("szhm0713", "chinagdn");
			String code = "01";
			System.out.println(sendBody);

			String data = "<soapenv:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:web=\"http://webservices.chinagdn.com\"> "
					+ " <soapenv:Header/> "
					+ "  <soapenv:Body> "
					+ "  <web:InsertDownSms soapenv:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\"> "
					+ "  <username xsi:type=\"soapenc:string\" xmlns:soapenc=\"http://schemas.xmlsoap.org/soap/encoding/\">"
					+ user
					+ "</username> "
					+ " <password xsi:type=\"soapenc:string\" xmlns:soapenc=\"http://schemas.xmlsoap.org/soap/encoding/\">"
					+ pass
					+ "</password> "
					+ " <batch xsi:type=\"soapenc:string\" xmlns:soapenc=\"http://schemas.xmlsoap.org/soap/encoding/\">"
					+ code
					+ "</batch> "
					+ " <sendbody xsi:type=\"soapenc:string\" xmlns:soapenc=\"http://schemas.xmlsoap.org/soap/encoding/\"><![CDATA["
					+ sendBody
					+ "]]></sendbody> "
					+ " </web:InsertDownSms> "
					+ " </soapenv:Body> " + " </soapenv:Envelope>";

			String url = ConfigReader.getInstance()
					.getProperty("SMSURL");
			
			//"http://203.91.55.69/services/Sms";

			// 代理控制
			String proxyIP = null;
			Integer proxyPort = 0;
			String vProxy = ConfigReader.getInstance().getProperty("VProxy");
			if (vProxy.equals("true")) {
				proxyIP = ConfigReader.getInstance()
						.getProperty("VProxyServer");
				proxyPort = ConfigReader.getInstance().getPropertyInt(
						"VProxyPort", 0);
			}

			String rst = HttpNormalReq
					.sendWsPost(url, data, proxyIP, proxyPort);
			String xmlReturn = XMLtoString(rst);

			String returnCode = xmlReturn.substring(
					xmlReturn.indexOf("<code>") + 6,
					xmlReturn.indexOf("</code>"));

			System.out.println("sendSM Result:" + returnCode);
			if (returnCode != null && returnCode.equals("0"))
				sendrst = true;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return sendrst;
	}

	public static void test() {

		String phoneNum = "13815429446,";
		String phoneContent = "^_^验证码1111";

		String sendBody = "<sendbody><message><orgaddr></orgaddr><mobile>"
				+ phoneNum
				+ "</mobile><content>"
				+ StringToXML(phoneContent)
				+ "</content><needreport>0</needreport><sendtime></sendtime></message><publicContent></publicContent></sendbody>";

		try {
			String user = passport_encrypt("244", "chinagdn");
			String pass = passport_encrypt("szhm0713", "chinagdn");
			String code = "01";
			System.out.println(sendBody);

			String data = "<soapenv:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:web=\"http://webservices.chinagdn.com\"> "
					+ " <soapenv:Header/> "
					+ "  <soapenv:Body> "
					+ "  <web:InsertDownSms soapenv:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\"> "
					+ "  <username xsi:type=\"soapenc:string\" xmlns:soapenc=\"http://schemas.xmlsoap.org/soap/encoding/\">"
					+ user
					+ "</username> "
					+ " <password xsi:type=\"soapenc:string\" xmlns:soapenc=\"http://schemas.xmlsoap.org/soap/encoding/\">"
					+ pass
					+ "</password> "
					+ " <batch xsi:type=\"soapenc:string\" xmlns:soapenc=\"http://schemas.xmlsoap.org/soap/encoding/\">"
					+ code
					+ "</batch> "
					+ " <sendbody xsi:type=\"soapenc:string\" xmlns:soapenc=\"http://schemas.xmlsoap.org/soap/encoding/\"><![CDATA["
					+ sendBody
					+ "]]></sendbody> "
					+ " </web:InsertDownSms> "
					+ " </soapenv:Body> " + " </soapenv:Envelope>";

			String url = "http://203.91.55.69/services/Sms";

			String rst = HttpNormalReq.sendWsPost(url, data, "10.41.70.8", 80);
			String xmlReturn = XMLtoString(rst);

			String returnCode = xmlReturn.substring(
					xmlReturn.indexOf("<code>") + 6,
					xmlReturn.indexOf("</code>"));

			System.out.println(returnCode);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// String result = smsService.InsertDownSms(
		// this.passport_encrypt("接口用户名", "chinagdn"),
		// this.passport_encrypt("接口密码", "chinagdn"), "01", sendBody); //
		// 此接口发送的短信内容一次最多1000个字符
		//
		//

		// XmlDocument();
		// xmlDoc.LoadXml(result);
		// System.Xml.XmlNodeList nodes = xmlDoc
		// .SelectNodes("//response/body/msgid");
		// if (nodes.Count > 0) {
		// // 短信发送成功
		// } else {
		// // 短信发送失败
		// }

	}

}
