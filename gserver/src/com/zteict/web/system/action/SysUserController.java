package com.zteict.web.system.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zteict.tool.common.Constant;
import com.zteict.tool.common.Md5Encrypt;
import com.zteict.tool.config.ConfigReader;
import com.zteict.tool.utils.AESEncrtptZteimweb;
import com.zteict.tool.utils.MsgSvrCall;

import com.zteict.web.system.action.base.BaseController;
import com.zteict.web.system.model.SysUserBean;
import com.zteict.web.system.service.SysUserService;




/**
 * 后台用户登录
 * 
 * @date 2016-7-29
 * @author zj
 * 
 */
@Controller(value = "/")
public class SysUserController extends BaseController {
	// 日志打印对象
	private Logger logger = Logger.getLogger(SysUserController.class);

	@Autowired
	private SysUserService sysUserService;


	/**
	 * 登录
	 * 
	 * @return 登录页面跳转
	 */
	@RequestMapping(value = { "/loginin" }, method = { org.springframework.web.bind.annotation.RequestMethod.GET })
	public String loginGet() {
		return "/home/login";
	}

	@RequestMapping(value ="/main")
	public String main(HttpServletRequest request,HttpSession session) {
		SysUserBean user = (SysUserBean) session
				.getAttribute(Constant.SESSION_USER);
		request.setAttribute("loginUser", user.getName());
		
		return "/home/index";
	}

	/**
	 * 后台登录请求,验证用户
	 * 
	 * @param request
	 *            获取请求
	 * @param response
	 *            响应请求
	 * @param userid
	 *            用户名
	 * @param password
	 *            密码
	 * @return
	 */
	@RequestMapping(value = { "/exlogin" })
	public @ResponseBody
	Map<String, Object> exclogin(HttpServletRequest request,
			HttpServletResponse response, HttpSession session,
			@RequestParam("username") String userid,
			@RequestParam("password") String password) {
		String msg = "";
		int sucess = 0;
		password = Md5Encrypt.MD5(password);
		// 用户名不区分大小写
		userid = userid.toLowerCase();

		SysUserBean sUser = new SysUserBean();
		sUser.setUserid(userid);
		sUser.setPassword(password);

		SysUserBean user = sysUserService.getUserInfoByUseridAndPwd(sUser);
		

		if (user == null) {
			msg = "用户名或密码错误";
			sucess = 0;
		} else {
			msg = "";
			sucess = 1;
			session.setAttribute(Constant.SESSION_USER, user);
		}
		HashMap<String, Object> map = new HashMap();
		map.put("sucess", Integer.valueOf(sucess));
		map.put("msg", msg);
		
	

		
		
		return map;
	}

	/**
	 * 后台用户 退出登录注销
	 * 
	 * @param session
	 *            session缓存对象
	 * @return
	 */
	@RequestMapping(value = "/logout")
	public String logout(HttpSession session) {
		session.removeAttribute(Constant.SESSION_USER);
		return "/home/login";
	}

	/**
	 * 管理员密码设置
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @param userid
	 * @param opwd
	 * @param npwd
	 * @return
	 */
	@RequestMapping(value = "/adminPwdSet", method = RequestMethod.POST)
	public @ResponseBody
	String setAdminPwd(HttpServletRequest request,
			HttpServletResponse response, HttpSession session, String userid,
			String opwd, String npwd) {
		SysUserBean user = (SysUserBean) session
				.getAttribute(Constant.SESSION_USER);
		if (Md5Encrypt.MD5(opwd).equals(user.getPassword())) {
			user.setPassword(Md5Encrypt.MD5(npwd));
			
			
			SysUserBean data=new SysUserBean();
			data.setUserid(user.getUserid());
			data.setPassword(Md5Encrypt.MD5(npwd));
			
			int res = sysUserService.updateSysuer(data);
			if (res > 0) {
				logger.info(user.getName() + "密码修改成功！");

				session.removeAttribute(Constant.SESSION_USER);

				return "200";
			} else {
				logger.info("管理员密码修改失败！");
				return "201";
			}
		} else {
			logger.info("旧密码不正确！");
			return "202";
		}
	}

}
