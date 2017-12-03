package com.zteict.web.system.action.base;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.zteict.tool.common.Constant;
import com.zteict.web.system.model.SysUserBean;


public class LoginInterceptor implements HandlerInterceptor {

	// 日志记录对象
	private Logger logger = Logger.getLogger(LoginInterceptor.class);

	@Override
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {
		// TODO Auto-generated method stub

	}

	/**
	 * 请求前拦截
	 */
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object arg2) throws Exception {
		// TODO Auto-generated method stub

		String uri = request.getRequestURI();
		String reqAction = uri.substring(uri.lastIndexOf("/") + 1);
		// 请求为updatePwd.action时不要求登录加密
		if (reqAction.contains("updatePwd")) {
			logger.info("请求Action为" + reqAction);
			return true;
		}
		
		if(reqAction.contains("login"))
		{
			logger.info("请求Action为" + reqAction);
			return true;
		}

		// logger.info("验证用户是否登录");
		HttpSession session = request.getSession();
		
	
		
		SysUserBean user = (SysUserBean) session.getAttribute(Constant.SESSION_USER);
		if (user == null || user.getUserid() == null
				|| user.getUserid().isEmpty()) {
			
			
			
			
			request.getRequestDispatcher("/login.jsp")
			.forward(request, response);
			
//			request.getRequestDispatcher("loginin.action").forward(request,
//					response);
			
			//logger.info("session-id:" + session.getId());
			
			logger.info("用户没有登录");
			return false;
		}
		// logger.info("用户已登录，用户ID："+user.getUserid());
		return true;
	}

}
