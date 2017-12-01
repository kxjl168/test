package com.zteict.web.system.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zteict.tool.utils.JsonUtil;
import com.zteict.web.system.action.base.BaseController;


/*

 * @date 2016-2-23
 * @author zj
 */
@Controller
//@RequestMapping(value="/func1")
public class FuncMenuController extends BaseController {

	

	/**
	 * 打开虚拟机管理
	 * @return
	 * @date 2016-2-29
	 * @author zj
	 */
	@RequestMapping(value="vmManager.do")
	public String vmManager()
	{
		return "/template/vmTemplate";
	}
	
	/**
	 * 用户管理
	 * @return
	 * @date 2016-2-29
	 * @author zj
	 */
	@RequestMapping(value="getDept.do")
	public String getDept()
	{
		return "/user/userAndDept";
	}
	
	
	/**
	 * 系统管理
	 * @return
	 * @date 2016-2-29
	 * @author zj
	 */
	@RequestMapping(value="adminSet.do")
	public String adminSet()
	{
		return "/view/test/grid";
	}
	
//	/**
//	 * 日志查询
//	 * @return
//	 * @date 2016-2-29
//	 * @author zj
//	 */
//	@RequestMapping(value="/log/toLogList.do")
//	public String toLogList()
//	{
//		return "/log/loginLog";
//	}
	/**
	 * 版本管理
	 * @return
	 * @date 2016-2-29
	 * @author zj
	 */
	@RequestMapping(value="versionManager.do")
	public String versionManager()
	{
		return "/versionmanager/version";
	}
	
	/**
	 * 休眠策略管理
	 * @return
	 * @date 2016-4-8
	 * @author jxh
	 */
	@RequestMapping(value="sleepRole.do")
	public String sleepRole()
	{
		return "/sleeprule/sleepruleList";
	}
	
	
//	/**
//	 * 应用管理
//	 * @return
//	 * @date 2016-2-29
//	 * @author zj
//	 */
//	@RequestMapping(value="appInfo.do")
//	public String appInfo()
//	{
//		return "/view/test/grid";
//	}
//	
//	/**
//	 * 水印
//	 * @return
//	 * @date 2016-2-29
//	 * @author zj
//	 */
//	@RequestMapping(value="/sysBaseInfo/getWaterConfig.do")
//	public String getWaterConfig()
//	{
//		return "/view/test/grid";
//	}
	
	
}
