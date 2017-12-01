package com.zteict.web.system.action.base;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.servlet.ServletRequestContext;
import org.apache.log4j.Logger;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.zteict.tool.common.Constant;




public class FileUploadInterceptor implements HandlerInterceptor {

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

	  private long maxSize;
	/**
	 * 请求前拦截
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object arg2) throws Exception {
		 if(request!=null && ServletFileUpload.isMultipartContent(request)) {
	            ServletRequestContext ctx = new ServletRequestContext(request);
	            long requestSize = ctx.getContentLength();
	            if (requestSize > maxSize) {
	            	 request.getRequestDispatcher("fileUploadError.action").forward(request, response);
	            	//throw new MaxUploadSizeExceededException(maxSize);
	            	return false;
	            }
	        }
	        return true;
	}

	public long getMaxSize() {
		return maxSize;
	}

	public void setMaxSize(long maxSize) {
		this.maxSize = maxSize;
	}

}
