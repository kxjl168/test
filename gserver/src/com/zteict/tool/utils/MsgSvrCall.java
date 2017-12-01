package com.zteict.tool.utils;

import java.util.List;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import com.zteict.tool.config.ConfigReader;
import com.zteict.tool.httpPost.SendPostRequest;
import com.zteict.tool.utils.AESEncrycptUtilZteimweb;


/**
 * 消息服务器调用
 * 
 * @date 2016-7-18
 * @author zj
 * 
 */
public class MsgSvrCall {

	public enum msgType {
		alluser, allpushuser, partuser, tag
	}

	private static Logger logger = Logger.getLogger(MsgSvrCall.class);

	private static String msg_ip;
	private static String msg_port;

	private static MsgSvrCall instance;

	private MsgSvrCall() {
	}

	public static void setConfig(String ip, String port) {
		msg_ip = ip;
		msg_port = port;
	}

	public static MsgSvrCall getInstance() {
		if (instance != null)

			return instance;
		else {
			String MsgSerIP = ConfigReader.getInstance()
					.getProperty("MsgSerIP");
			String MsgSerPort = ConfigReader.getInstance().getProperty(
					"MsgSerPort");

			// logger.info("MsgSerIP:" + MsgSerIP + ",MsgSerPort:" +
			// MsgSerPort);

			if (MsgSerIP == null || MsgSerIP.isEmpty() || MsgSerPort == null
					|| MsgSerPort.isEmpty()) {

				logger.error("配置参数中服务器信息为空！");

			}

			instance = new MsgSvrCall();
			setConfig(MsgSerIP, MsgSerPort);

			return instance;

		}

	}

	/**
	 * 调用消息服务器接口，发送消息
	 * 
	 * @param behaviour
	 *            操作方式：register：创建 remove：删除
	 * 
	 * @throws Exception
	 */
	public String SendToMsgSvr(msgType reciver_type, List<String> reciver_ids,
			String PusherID, String PusherDomainID, String Abstract,
			String Data, String dealLine, String offlineType) throws Exception {

		JSONObject all = new JSONObject();

		JSONObject jo = new JSONObject();
		jo.put("datatype", 1);
		jo.put("specterminal", 0);
		jo.put("apnsflag", 1);
		jo.put("deadline", dealLine);
		jo.put("offlineflag", Integer.parseInt(offlineType));

		JSONObject res_new = new JSONObject();

		JSONArray res = new JSONArray();
		JSONObject re = new JSONObject();

		if (reciver_type == msgType.alluser) {

			re = new JSONObject();
			re.put("ReceiveType", "alluser");

			all.put("Receivers", re);

		} else if (reciver_type == msgType.allpushuser) {

			re = new JSONObject();
			re.put("ReceiveType", "allpushuser");

			all.put("Receivers", re);

		} else if (reciver_type == msgType.partuser) {

			res_new.put("ReceiveType", "partuser");
			res = new JSONArray();

			for (int i = 0; i < reciver_ids.size(); i++) {
				re = new JSONObject();
				re.put("UID", Integer.parseInt(reciver_ids.get(i)));
				re.put("DomainID", 1);
				res.put(re);
			}

			res_new.put("Receivers", res);

			//新
			//all.put("Receivers", res_new);
			
			
			//老
			all.put("Receivers", res);

		} else if (reciver_type == msgType.tag) {

			res_new.put("ReceiveType", "tag");
			res_new.put("tagoperation ", 1);

			res = new JSONArray();
			for (int i = 0; i < reciver_ids.size(); i++) {
				res.put(reciver_ids.get(i));
			}
			res_new.put("tags", res);

			all.put("Receivers", res_new);

		}

		all.put("PusherID",Integer.parseInt( PusherID));
		all.put("PusherDomainID", Integer.parseInt(PusherDomainID));
		all.put("Attributes", jo);
		all.put("Abstract", Abstract);
		all.put("Data", Data);

		String jsonStr = all.toString();

		String d = jsonStr;// "[{\"Data\":\"1231111\",\"Attributes\":{\"specterminal\":3,\"datatype\":1,\"apnsflag\":1},\"PusherDomainID\":1,\"Abstract\":\"hello\",\"Receivers\":[{\"DomianID\":1,\"UID\":18}],\"PusherID\":260000}]";
		System.out.println("加密之前的JSON串:" + d.toString());

		
		 jsonStr =  AESEncrycptUtilZteimweb.cryptHex( d.toString(), "123321");
		//jsonStr = AESEncrypt.enCrypt("", d.toString(), "123321");
		System.out.println("加密之后的JSON串:" + jsonStr);

		String URL = "http://" + msg_ip + ":" + msg_port
				+ "/zteim_web/appsys/push2All.action";
		System.out.println("发送消息：" + URL + "操作方式:");
		// 发送post请求
		String responseCode = SendPostRequest.sendHttpData(URL, jsonStr);
		responseCode = AESEncrycptUtilZteimweb.decryptHex(responseCode, "123321");
		return responseCode;
	}

	/**
	 * 调用消息服务器接口，创建消息服务器
	 * 
	 * @param behaviour
	 *            操作方式：register：创建 remove：删除
	 * 
	 * @throws Exception
	 */
	public String register(String Nickname, String behaviour) throws Exception {
		String URL = "http://" + msg_ip + ":" + msg_port
				+ "/zteim_web/appsys/register.action";
		System.out.println("消息服务器开户：" + URL);
		String data = "{" + "\"Nickname\": \"" + Nickname + "\"}";
		// 发送post请求
		logger.info("input:" + data);
		String strdata = AESEncrycptUtilZteimweb.cryptHex(data, "123321");
		String responseCode = SendPostRequest.sendHttpData(URL, strdata);
		return responseCode;
	}

	/**
	 * 调用消息服务器接口，创建消息服务器
	 * 
	 * @param behaviour
	 *            操作方式：register：创建 remove：删除
	 * 
	 * @throws Exception
	 */
	public static String xmppSynData(String Nickname, String xmppIp,
			String xmppPort, String behaviour) throws Exception {
		String URL = "http://" + xmppIp + ":" + xmppPort
				+ "/zteim_web/appsys/register.action";
		System.out.println("公有云模式下，调用消息服务器地址：" + URL + "操作方式:" + behaviour);
		// 发送post请求
		String strdata = AESEncrycptUtilZteimweb.cryptHex("{"
				+ "\"Nickname\": \"" + Nickname + "\"}", "123321");
		String responseCode = SendPostRequest.sendHttpData(URL, strdata);
		return responseCode;
	}
}
