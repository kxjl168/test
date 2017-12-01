package com.zteict.web.system.action;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.zteict.otool.utils.AESEncrypt;
import com.zteict.otool.utils.SSLClientUtil;
import com.zteict.otool.utils.SendPostRequest;
import com.zteict.tool.config.ConfigReader;
import com.zteict.tool.httpPost.FormFieldKeyValuePair;
import com.zteict.tool.httpPost.HttpPostEmulator;

import com.zteict.tool.utils.JsonUtil;

import com.zteict.web.system.action.base.BaseController;

/**
 * 图像识别
 * 
 * @author Administrator
 * 
 */
@Controller
@RequestMapping(value = "/aes")
public class AESController extends BaseController {

	@RequestMapping(value = "/enCrypt")
	public void enCrypt(HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String data = handleNoAresRequest(request);

		try {

			String KEY = "b2xkcGhvdG9vbGRw";

			String input = AESEncrypt.enCrypt(data, KEY);

			JsonUtil.responseOutWithJson(response, input);

		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

	}
	
	@RequestMapping(value = "/deCrypt")
	public void deCrypt(HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String data = handleNoAresRequest(request);

		try {

			String KEY = "b2xkcGhvdG9vbGRw";

			String input = AESEncrypt.deCrypt(data, KEY);

			JsonUtil.responseOutWithJson(response, input);

		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		// 测试
		//String url = "https://szhm.sz.gov.cn:8084/szumisp/user/QueryUsrDetailInfo.action";
		
		//encrytTest();
		
		decrytTest();
	}
	
	private static void encrytTest()
	{

		String url = "http://localhost:8080/szumisp/aes/enCrypt.action";

		

		String responsedata = "";
		try {
			JSONObject jso = new JSONObject();
			jso.put("Token", "TokenCA850F53F95DB2C368ECB0A145AB2102-n1");
			jso.put("SmUid", "SUUMNO2579785");
			jso.put("Pass", "123321");
			jso.put("Account", "zte_test");

			String data = jso.toString();
			//String input = AESEncrypt.enCrypt(data, KEY);

			//responsedata = SSLClientUtil.doPost(url, input);
			 responsedata = SendPostRequest.sendHttpData(url, jso.toString(), "", 0);

			System.out.println("加密串返回:" + responsedata);

		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
	private static void decrytTest()
	{

		//String url = "http://localhost:8080/szumisp/aes/deCrypt.action";
		
		String url = "http://58.67.201.7:18088/szumisp/aes/deCrypt.action";

		
		
		

		String responsedata = "";
		try {
			

			String data ="e28e1d3cb6783be37498b3417046e3de4a819a89d044431660ae9696bf0d234edeed8958427d4d7a9fa048c90ff16bcdd71f5e688043626ee3b14ae61fcc2bd6a067bf344232a071901d108d85135e2d5f6df98bb75f276fe0403e9a2df75aa5f8e59aa98b426c23897a07b82ae7ac2bb6a0b7e8f54f84f5fe573690f5480a1c";
			//String input = AESEncrypt.enCrypt(data, KEY);

			//responsedata = SSLClientUtil.doPost(url, input);
			responsedata = SendPostRequest.sendHttpData(url, data, "proxynj.zte.com.cn", 80);
			 //responsedata = SendPostRequest.sendHttpData(url, data, "proxynj.zte.com.cn", 80);

			System.out.println("解密串返回:" + responsedata);

		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * 更新头像
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 * @date 2016-6-27
	 * @author jxh
	 */
	@RequestMapping(value = "/DecodeImageCode", method = RequestMethod.POST)
	public void DecodeImageCode(
			@RequestParam(value = "ImageStream", required = false) MultipartFile[] receiveFiles,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String CodeType = request.getParameter("CodeType");
		String TimeOut = request.getParameter("TimeOut");

		JSONObject jsonOut = this.handleResponseChangeHead(CodeType, TimeOut,
				request, response, receiveFiles);
		// handleNoAresRequest(request, response, data);
		responseDataNoAres(request, response, jsonOut.toString());

	}

	/**
	 * 返回加密数据
	 * 
	 * @param request
	 * @param response
	 * @param data
	 * @param uService
	 * @date 2016-1-22
	 * @author zj
	 */
	public void responseDataNoAres(HttpServletRequest request,
			HttpServletResponse response, String data) {
		try {

			String res = "";
			String uri = request.getRequestURI();
			String reqAction = uri.substring(uri.lastIndexOf("/") + 1);
			res = data;// AESEncrypt.enCrypt("", data, Constant.FIRST_AES_KEY);

			response.setHeader("content-type", "text/html; charset=UTF-8");
			response.setHeader("Content-Length", ""
					+ res.getBytes("UTF-8").length);
			response.setCharacterEncoding("UTF-8");
			response.getWriter().print(res);
			response.getWriter().flush();
		} catch (Exception e) {
			// logger.error(e.toString());
		}
	}

	/**
	 * 更新头像
	 * 
	 * @param data
	 *            输入参数信息
	 * @param request
	 *            获取请求
	 * @param response
	 *            响应请求
	 * @return 输出参数信息
	 * @throws Exception
	 *             异常信息
	 */
	private JSONObject handleResponseChangeHead(String CodeType,
			String TimeOut, HttpServletRequest request,
			HttpServletResponse response, MultipartFile[] receiveFiles)
			throws JSONException {

		JSONObject jsonOut = new JSONObject();
		try {

			System.out.println("上传。。。");
			// 将文件保存到文件服务器
			String responsedata = "";
			// String serverUrl =
			// ConfigReader.getInstance().getProperty("CHANGEHEADUrl");
			String serverUrl = ConfigReader.getInstance().getProperty(
					"FILE_SVR_PATH")
					+ "FileSvr/UploadFile.action";
			ArrayList<FormFieldKeyValuePair> fds = new ArrayList<FormFieldKeyValuePair>();

			responsedata = HttpPostEmulator.sendHttpPostRequestByMutiPartFile(
					serverUrl, fds, receiveFiles);

			// 解析responsedate
			JSONObject jsonRes = new JSONObject(responsedata);
			if ((jsonRes.getString("ResponseCode")).equals("200")) {

				String url = jsonRes.getString("FileUrl");

				jsonOut.put("Msg", "url:" + url + "$$" + "CodeType:" + CodeType
						+ "$$TimeOut:" + TimeOut);
				jsonOut.put("Code", "200");
				jsonOut.put("CodeResult", "112233");

				System.out.println(jsonOut.toString());
			} else {
				jsonOut.put("Code", "201");
				jsonOut.put("Msg", "更新文件上传至文件服务器失败");
				System.out.println("更新文件上传至文件服务器失败");
				return jsonOut;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			jsonOut.put("Code", "201");
			jsonOut.put("Msg", "发生错误！");
			System.out.println("发生错误" + e.getMessage());
			return jsonOut;
		}

		return jsonOut;
	}

}
