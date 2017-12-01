package com.zteict.web.system.action.base;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;

import com.zteict.tool.common.Constant;
import com.zteict.tool.config.ConfigReader;
import com.zteict.tool.httpPost.FormFieldKeyValuePair;
import com.zteict.tool.httpPost.HttpPostEmulator;
import com.zteict.tool.utils.AESEncrypt;
import com.zteict.tool.utils.StringUtil;
import com.zteict.web.system.model.SysUserBean;
import com.zteict.web.system.model.TokenBean;
import com.zteict.web.system.service.TokenService;


public class BaseController {

	private Logger logger = Logger.getLogger(BaseController.class);

	// private UserBean user = new UserBean();
	public String phoneRegex = "^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";
	public String emailRegex = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
	private String loginId = "";

	@Autowired
	private TokenService tService;

	
	@ExceptionHandler(RuntimeException.class)  
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)  
	public String handleUnexpectedServerError(RuntimeException ex) {
		

		
		logger.error(ex.getMessage());
	    return ex.getMessage();  
	}  
	

	 @ModelAttribute 
	    public void ImgSvrModel( Model model) {  
		 String  HTTP_PATH=getImgHttpPath();
	       model.addAttribute("HTTP_PATH", HTTP_PATH);  
	   	// 获取 文件服务器http地址图片前缀

	    }  
	
	 

	/**
	 * 保存文件至文件服务器
	 * 
	 * @param receiveFile
	 * @param dir
	 * @return
	 * @date 2016-7-14
	 * @author zj
	 */
	public JSONObject SaveToFileSvr(MultipartFile receiveFile, String dir) {
		// 将文件保存到文件服务器
		JSONObject rst = new JSONObject();

		try {

			String responsedata = "";
			String serverUrl = ConfigReader.getInstance().getProperty(
					"FILE_SVR_PATH")
					+ "FileSvr/UploadFile.action";
			ArrayList<FormFieldKeyValuePair> fds = new ArrayList<FormFieldKeyValuePair>();

			FormFieldKeyValuePair p1 = new FormFieldKeyValuePair("path", dir);
			fds.add(p1);

			try {
				responsedata = HttpPostEmulator
						.sendHttpPostRequestByMutiPartFile(serverUrl, fds,
								new MultipartFile[] { receiveFile });
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				// 文件大小超标
				try {
					rst.put("returnCode", "201");
					rst.put("returnMsg", "上传失败" + e.getMessage());

				} catch (Exception e2) {
					// TODO: handle exception
				}
			}

			// 解析responsedate
			JSONObject jsonRes = new JSONObject(responsedata);
			if ((jsonRes.getString("ResponseCode")).equals("200")) {

				// jsonOut.put("ResponseCode", "200");
				// jsonOut.put("ResponseMsg", "OK");
				// jsonOut.put("FileUrl", httpURL);
				// jsonOut.put("relativeURL", relativeURL);
				// jsonOut.put("downURL", downURL);
				// jsonOut.put("httpDownURL", httpDownURL);

				rst.put("returnCode", "200");
				rst.put("returnMsg", "OK");

				String httpURL = jsonRes.optString("relativeURL");

				rst.put("httpURL", httpURL);

			} else {

				rst.put("returnCode", "201");

				String returnMsg = jsonRes.optString("ResponseMsg");
				rst.put("returnMsg", returnMsg);
			}
			
		} catch (Exception e) {
			
		}
		return rst;
	}

	/**
	 * 获取文件服务器前缀地址 ，与数据库相对路径拼接为完整图片URL
	 * 
	 * @return
	 * @date 2016-7-11
	 * @author zj
	 */
	public String getImgHttpPath() {
		String HTTP_PATH = ConfigReader.getInstance().getProperty(
				"FILE_SVR_HTTP_PATH");
		return HTTP_PATH;
	}
	
	/**
	 * 获取文件服务器前缀地址 ，与数据库相对路径拼接为完整图片URL -外网地址
	 * 
	 * @return
	 * @date 2016-7-11
	 * @author zj
	 */
	public String getImgHttpOutPath() {
		String HTTP_PATH = ConfigReader.getInstance().getProperty(
				"FILE_SVR_HTTP_OUTER_PATH");
		return HTTP_PATH;
	}

	/**
	 * 获取post的字符串或者 get到的为data的参数
	 * 
	 * @param request
	 * @return
	 * @date 2016-3-7
	 * @author zj
	 */
	public String getUnAesData(HttpServletRequest request) {
		String datastr = "";
		try {

			datastr = request.getParameter("data");
			datastr = datastr == null ? "" : datastr;

			if (datastr.equals("")) {
				InputStream instream = request.getInputStream();
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(instream, "utf-8"));
				StringBuilder sb = new StringBuilder();
				String line = "";
				while ((line = reader.readLine()) != null) {
					sb.append(line);
				}
				datastr = sb.toString();
			}
		} catch (Exception e) {
			logger.error("获取非加密参数异常:" + e.getMessage());
		}
		return datastr;
	}

	/**
	 * 验证token信息
	 * 
	 * @param Token
	 * @return
	 * @date 2016-6-23
	 * @author zj
	 */
	public Boolean validateToken(String Token) {
		boolean rst = true;

		String VToken = ConfigReader.getInstance().getProperty("VToken");

		if (!StringUtil.isNull(VToken) && VToken.equalsIgnoreCase("true")) {

			TokenBean token = tService.hasTokenbyTokenId(Token);
			if (token != null)
				rst = true;
			else
				rst = false;

		} else
			rst = true;
		return rst;

	}

	/**
	 * 验证token信息，区分匿名及实名
	 * 
	 * @param Token
	 * @return
	 * @date 2016-6-23
	 * @author zj
	 */

	public Map<String, Object> validateToken(String Token, String userID,
			String deviceID) {
		boolean rst = true;
		Map<String, Object> map = new HashMap<String, Object>();
		String VToken = ConfigReader.getInstance().getProperty("VToken");

		if (!StringUtil.isNull(VToken) && VToken.equalsIgnoreCase("true")) {
			// TODO

			if (userID == null || userID.equals("")) {

				if (deviceID == null || deviceID.equals("")) {
					map.put("ResponseCode", 201);
					map.put("ResponseMsg", "匿名登录deviceID不能为空");
					return map;
				} else {
					map.put("ResponseCode", "200");
					map.put("ResponseMsg", "OK");
					return map;
				}

			} else {

				TokenBean token = tService.hasTokenbyUserId(userID);
				if (token == null) {
					map.put("ResponseCode", "201");
					map.put("ResponseMsg", "token已失效");
					return map;
				} else if(token.getToken().equals(Token)) {
					map.put("ResponseCode", "200");
					map.put("ResponseMsg", "OK");
					return map;
				}
				else
				{
					map.put("ResponseCode", "201");
					map.put("ResponseMsg", "Token效验失败!");
					return map;
				}

			}

		} else {
			map.put("ResponseCode", 200);
			map.put("ResponseMsg", "OK");
			return map;
		}

	}

	/**
	 * 获取请求中的用户id或者登陆id,取用户md5密码
	 * 
	 * @param request
	 * @return
	 * @date 2016-3-1
	 * @author zj
	 */
	public String getUserID(HttpServletRequest request) {
		String loginId = request.getParameter("UserId");

		// 添加判断loginId 是否为空 如果为空 则获取LoginId
		if (loginId == null) {
			loginId = request.getParameter("LoginId");
		}

		if (loginId == null || loginId.equals(""))
			logger.error("request解析失败,UserId or LoginID Needed！！");

		return loginId;
	}

	/**
	 * 返回密码前16位用作秘钥
	 * 
	 * @param mdkPass
	 * @return
	 * @date 2016-3-1
	 * @author zj
	 */
	public String get16AesKey(String mdkPass) {
		return mdkPass.substring(0, 16);
	}

	/**
	 * 获取请求的JSON串，非加密
	 * 
	 * @param request
	 * @param uService
	 * @return 实际请求数据
	 * @author
	 * @date 2015-12-15 下午2:34:43 *
	 */
	public String handleNoAresRequest(HttpServletRequest request) {
		String data = null;
		try {
			InputStream instream = request.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					instream, "utf-8"));
			StringBuilder sb = new StringBuilder();
			String line = "";
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
			reader.close();

			instream.close();
			data = sb.toString();

		} catch (Exception e) {
			logger.error(e.toString());
		}
		return data;
	}

	/**
	 * 解密输入串，返回json输入参数 ,固定加密串
	 * 
	 * @param request
	 * @return
	 * @date 2016-6-23
	 * @author zj
	 */
	public String handleRequestNoKey(HttpServletRequest request) {
		String data = null;
		try {
			InputStream instream = request.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					instream, "utf-8"));
			StringBuilder sb = new StringBuilder();
			String line = "";
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
			reader.close();

			instream.close();
			String datastr = sb.toString();
			data = AESEncrypt.deCrypt("", datastr, Constant.FIRST_AES_KEY);
			
			
			//转码
			data=xssEncode(data);
			
		} catch (Exception e) {
			logger.error(e.toString());
		}
		return data;
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
	public void responseDataNoKey(HttpServletRequest request,
			HttpServletResponse response, String data) {
		try {

			String res = "";
			String uri = request.getRequestURI();
			String reqAction = uri.substring(uri.lastIndexOf("/") + 1);
			res = AESEncrypt.enCrypt("", data, Constant.FIRST_AES_KEY);

			response.setHeader("content-type", "text/html; charset=UTF-8");
			response.setHeader("Content-Length", ""
					+ res.getBytes("UTF-8").length);
			response.setCharacterEncoding("UTF-8");
			
			//转码
			res=xssEncode(res);
			
			response.getWriter().print(res);
			response.getWriter().flush();
		} catch (Exception e) {
			logger.error(e.toString());
		}
	}
	
	
	private static String xssEncode(String s) {
		if (s == null || s.isEmpty()) {
			return s;
		}
		StringBuilder sb = new StringBuilder(s.length() + 16);
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			switch (c) {
			case '>':
				sb.append('＞');// 全角大于号
				break;
			case '<':
				sb.append('＜');// 全角小于号
				break;
			/*case '\'':
				sb.append('‘');// 全角单引号
				break;
			case '\"':
				sb.append('“');// 全角双引号
				break;
			case '&':
				sb.append('＆');// 全角
				break;
			case '\\':
				sb.append('＼');// 全角斜线
				break;
			case '#':
				sb.append('＃');// 全角井号
				break;*/
			default:
				sb.append(c);
				break;
			}
		}
		return sb.toString();
	}

	/**
	 * 返回数据部分加密
	 * 
	 * @param request
	 * @param response
	 * @param data
	 * @date 2016-7-11
	 */
	public void responseDataNoAllKey(HttpServletRequest request,
			HttpServletResponse response, String data) {
		try {
			
			//转码
			data=xssEncode(data);
			
			JSONObject jsonIn = new JSONObject(data);

			String res = "";
			String uri = request.getRequestURI();
			String reqAction = uri.substring(uri.lastIndexOf("/") + 1);
			res = AESEncrypt.enCrypt("", data, Constant.FIRST_AES_KEY);

			JSONObject jsonOut = new JSONObject();
			jsonOut.put("ResponseCode", jsonIn.optString("ResponseCode"));
			jsonOut.put("ResponseMsg", res);

			response.setHeader("content-type", "text/html; charset=UTF-8");
			response.setHeader("Content-Length", ""
					+ jsonOut.toString().getBytes("UTF-8").length);
			response.setCharacterEncoding("UTF-8");
			
			
		
			
			response.getWriter().print(jsonOut);
			response.getWriter().flush();
		} catch (Exception e) {
			logger.error(e.toString());
		}
	}

	/**
	 * 解析请求数据，解密处理-两次解密处理
	 * 
	 * @param request
	 * @param uService
	 * @return 实际请求数据
	 * @author
	 * @date 2015-12-15 下午2:34:43 *
	 */
	public String handleRequest(HttpServletRequest request,
			String second_aes_key) {

		if (true)
			return handleRequestNoKey(request);

		String data = null;
		try {
			InputStream instream = request.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					instream, "utf-8"));
			StringBuilder sb = new StringBuilder();
			String line = "";
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
			reader.close();

			instream.close();
			String datastr = sb.toString();
			loginId = request.getParameter("UserId");

			// 添加判断loginId 是否为空 如果为空 则获取LoginId
			if (loginId == null) {
				loginId = request.getParameter("LoginId");
			}
			String uri = request.getRequestURI();
			String reqAction = uri.substring(uri.lastIndexOf("/") + 1);
			logger.info("===================================");
			logger.info("请求Action为" + reqAction);
			logger.info("second_key为:" + second_aes_key);
			logger.info("===================================");
			// 请求为login.action时不加密
			if (reqAction.equals("login.action")
					|| reqAction.equals("loginMobile.action")) {
				return datastr;
			}
			// 第一次使用普通的AES解密
			datastr = AESEncrypt.deCrypt("", datastr, Constant.FIRST_AES_KEY);
			// 第二次取用密码的前十位作为KEY使用AES解密
			// user = uService.getUserInfoByLoginIdOrPhoneOrEmail(loginId);

			// String pwd = user.getPassword();
			String pwdKey = second_aes_key;// pwd.substring(0,16);
			data = AESEncrypt.deCrypt("", datastr, pwdKey);
		} catch (Exception e) {
			logger.error(e.toString());
		}
		return data;
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
	public void responseData(HttpServletRequest request,
			HttpServletResponse response, String data, String second_aes_key) {

		if (true) {
			responseDataNoKey(request, response, data);
			return;
		}

		try {
			// 第一次取密码的前十位作为key使用AES加密
			// if (Pattern.matches(phoneRegex,loginId)) {
			// user = uService.getUserInfoByTelphone(loginId);
			// }else if (Pattern.matches(emailRegex,loginId)) {
			// user = uService.getUserInfoByEmail(loginId);
			// }else {
			// user = uService.getUserInfoByLoginId(loginId);
			// }
			String res = "";
			String uri = request.getRequestURI();
			String reqAction = uri.substring(uri.lastIndexOf("/") + 1);
			// 请求为login.action时不加密
			if (!reqAction.equals("login.action")
					&& !reqAction.equals("loginMobile.action")) {
				// if (Pattern.matches(phoneRegex,loginId) ||
				// Pattern.matches(emailRegex,loginId)) {
				// user = uService.getUserInfoByLoginIdOrPhoneOrEmail(loginId);
				// }

				// if (!reqAction.equals("updatePwd.action"))
				// {
				// //如过是修改密码，则此处不再取最新的用户密码，而使用之前的user对象里面的老密码几码
				// user = uService.getUserInfoByLoginIdOrPhoneOrEmail(loginId);
				// logger.info(user.getUserid()+"/"+ user.getPassword());
				// logger.info(res);
				// }

				String pwd = second_aes_key;// user.getPassword();
				String pwdKey = pwd.substring(0, 16);
				res = AESEncrypt.enCrypt("", data, pwdKey);
			} else {
				res = data;
			}

			if (reqAction.equals("updatePwd.action")) {
				// logger.info(user.getUserid() + "/" + user.getPassword());
				// logger.info(res);
			}

			response.setHeader("content-type", "text/html; charset=UTF-8");
			response.setHeader("Content-Length", ""
					+ res.getBytes("UTF-8").length);
			response.setCharacterEncoding("UTF-8");
			response.getWriter().print(res);
			response.getWriter().flush();
		} catch (Exception e) {
			logger.error(e.toString());
		}
	}

	/**
	 * 
	 * @param request
	 * @param AesKey
	 * @return 实际请求数据
	 * @date 2016-7-1
	 */
	public String handleRequestWithKey(HttpServletRequest request, String AesKey) {
		String data = null;
		try {
			InputStream instream = request.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					instream, "utf-8"));
			StringBuilder sb = new StringBuilder();
			String line = "";
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
			reader.close();

			instream.close();
			String datastr = sb.toString();
			data = AESEncrypt.deCrypt("", datastr, AesKey);
		} catch (Exception e) {
			logger.error(e.toString());
		}
		return data;
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @param data
	 * @param AesKey
	 * @date 2016-7-1
	 */
	public void responseDataWithKey(HttpServletRequest request,
			HttpServletResponse response, String data, String AesKey) {
		try {

			String res = "";
			String uri = request.getRequestURI();
			String reqAction = uri.substring(uri.lastIndexOf("/") + 1);
			res = AESEncrypt.enCrypt("", data, AesKey);

			response.setHeader("content-type", "text/html; charset=UTF-8");
			response.setHeader("Content-Length", ""
					+ res.getBytes("UTF-8").length);
			response.setCharacterEncoding("UTF-8");
			response.getWriter().print(res);
			response.getWriter().flush();
		} catch (Exception e) {
			logger.error(e.toString());
		}
	}
}
