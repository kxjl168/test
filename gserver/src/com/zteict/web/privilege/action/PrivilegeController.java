package com.zteict.web.privilege.action;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.zteict.tool.common.Constant;
import com.zteict.tool.common.Md5Encrypt;
import com.zteict.tool.config.ConfigReader;
import com.zteict.tool.utils.JsonUtil;
import com.zteict.tool.utils.StringUtil;

import com.zteict.web.privilege.dao.RoleDao;
import com.zteict.web.privilege.dao.SysUserBeanDao;
import com.zteict.web.privilege.model.Role;
import com.zteict.web.privilege.service.PrivilegeService;
import com.zteict.web.system.action.base.BaseController;
import com.zteict.web.system.model.MenuInfo;
import com.zteict.web.system.model.SysUserBean;
import com.zteict.web.system.service.MenuInfoService;


/**
 * 权限
 * 
 * @date 2016-3-4
 * @author zj
 * 
 */
@Controller
@RequestMapping(value = "/Privilege")
public class PrivilegeController extends BaseController {

	// 日志记录对象
	private Logger logger = Logger.getLogger(PrivilegeController.class);

	@Autowired
	SysUserBeanDao suserDao;

	@Autowired
	RoleDao roleDao;

	@Autowired
	PrivilegeService privilegeService;

	@Autowired
	private MenuInfoService menuService;

	/**
	 * 后台用户管理界面
	 * 
	 * @return
	 * @date 2016-10-17
	 * @author zj
	 */
	@RequestMapping(value = "/InitManagerList")
	public ModelAndView InitManagerList() {
		ModelAndView view = new ModelAndView();
		view.setViewName("/privilege/manager");

		return view;
	}

	/**
	 * 页面-获取管理员列表
	 * 
	 * @param clientType
	 * @param packageName
	 * @param curPage
	 *            当前页
	 * @param pageCount
	 *            每页多少条
	 * @return
	 */
	@RequestMapping(value = "/getManagerList")
	public void getManagerList(HttpServletRequest request,
			HttpServletResponse response) {
		String manager_id = request.getParameter("manager_id");
		String manager_name = request.getParameter("manager_name");

		int pageCount = Integer.parseInt(request.getParameter("rows"));// request.getParameter("pageCount");
		int curPage = Integer.parseInt(request.getParameter("page"));

		SysUserBean query = new SysUserBean();
		query.setPage(curPage);
		query.setPageCount(pageCount);

		query.setName(manager_name);
		query.setUserid(manager_id);

		List<SysUserBean> infos = suserDao.getSysUserBeanPageList(query);
		int total = suserDao.getSysUserBeanPageListCount(query);

		Gson gs = new Gson();
		String jsStr = gs.toJson(infos);

		String rst = "{\"total\":" + total + ",\"rows\":" + jsStr + "}";

		JsonUtil.responseOutWithJson(response, rst);

	}

	/**
	 * 编辑或添加后台管理员信息请求
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @date 2016-8-4
	 */
	@RequestMapping(value = "/initManagerInfo")
	public ModelAndView initManagerInfo(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView view = new ModelAndView();
		view.setViewName("/privilege/AddOrUpdateManager");

		String manager_id = request.getParameter("manager_id");
		SysUserBean query = new SysUserBean();
		query.setUserid(manager_id);
		SysUserBean bean = suserDao.getSysUserBeanInfoById(query);
		
		
//		bean=new SysUserBean();
//		bean.setUserid("xxx");
		
		view.addObject("bean", bean);
		
		
		List<Role> roles = privilegeService.getManagerRoleList(bean);
	
		Role query2=new Role();
		query2.setPage(1);
		query2.setPageCount(1000);
		List<Role> AllRoles = roleDao.getRolePageList(query2);
		
		for (int i = 0; i < AllRoles.size(); i++) {
			for (int j = 0; j < roles.size(); j++) {
				if(roles.get(j).getRole_en().equals(AllRoles.get(i).getRole_en()))
					AllRoles.get(i).setSelect("select");
			}
		}
		
		
		view.addObject("roles", AllRoles);


		return view;
	}

	/**
	 * 重置密码
	 */
	@RequestMapping(value = { "/resetPassword" }, method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> resetPassword(
			@RequestParam("manager_id") String manager_id) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (manager_id == null || manager_id.isEmpty()) {
			map.put("ResponseCode", Constant.FailCode);
			map.put("ResponseMsg", "获取user_id参数值为空");
		} else {

			SysUserBean sysUser = new SysUserBean();

			sysUser.setUserid(manager_id);
			sysUser.setPassword(Md5Encrypt.MD5("123321"));

			int result = suserDao.updateSysUserBean(sysUser);
			if (result > -1) {
				map.put("ResponseCode", Constant.OKCode);
				map.put("ResponseMsg", "重置密码成功");
			} else {
				map.put("ResponseCode", Constant.FailCode);
				map.put("ResponseMsg", "重置密码失败");
			}
		}
		return map;
	}

	/**
	 * 添加或者更新后台管理用户信息
	 * 
	 * @param request
	 * @param response
	 * @date 2016-6-22
	 * @author zj
	 */
	@RequestMapping(value = "/addOrUpdateManagerInfo")
	public void addOrUpdateManagerInfo(HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {
		JSONObject jsonOut = new JSONObject();
		try {

			String manager_id = request.getParameter("manager_id");
			String password = request.getParameter("password");
			
			String manager_name = request.getParameter("manager_name");
			String roleids = request.getParameter("roleids");

			SysUserBean query = new SysUserBean();
			query.setUserid(manager_id);
			query.setName(manager_name);

			SysUserBean sysUser = new SysUserBean();

			SysUserBean tmp = suserDao.getSysUserBeanInfoById(query);

			// banner.setBanner_name(banner_name);
			sysUser.setName(manager_name);
			sysUser.setUserid(manager_id);

			SysUserBean user = (SysUserBean) session
					.getAttribute(Constant.SESSION_USER);

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String time = sdf.format(new Date());
			sysUser.setUpdateddate(time);

			int rst = -1;

			if (tmp != null) {
				sysUser.setUpdateddate(time);
				if (user != null)
					sysUser.setUpdatedby(user.getName());

				rst = suserDao.updateSysUserBean(sysUser);

			} else {

				sysUser.setCreateby(user.getName());
				sysUser.setCreatedate(time);
				if(password==null||password.trim().equals(""))
					password="123321";
				sysUser.setPassword(Md5Encrypt.MD5(password));
				rst = suserDao.addSysUserBean(sysUser);
			}

			if (rst > 0)
				// 处理用户下的角色
				rst = privilegeService.updateManagerRoleList(manager_id,
						roleids);

			if (rst > 0) {
				jsonOut.put("ResponseCode", 200);
				jsonOut.put("ResponseMsg", "OK");
			} else {
				jsonOut.put("ResponseCode", 201);
				jsonOut.put("ResponseMsg", "FAILED");
			}

		} catch (Exception e2) {
			logger.error(e2.getMessage());
			try {
				jsonOut.put("ResponseCode", "201");
				jsonOut.put("ResponseMsg", "FAILED");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		JsonUtil.responseOutWithJson(response, jsonOut.toString());

	}

	/**
	 * 删除后台用户
	 * 
	 * @param request
	 * @return
	 * @date 2016-8-22
	 */
	@RequestMapping(value = "/delManager")
	public @ResponseBody
	Map delManager(HttpServletRequest request) {
		Map res = new HashMap();
		String manager_id = request.getParameter("manager_id");

		SysUserBean sysUser = new SysUserBean();

		sysUser.setUserid(manager_id);

		if (suserDao.deleteSysUserBean(sysUser) > 0) {
			
			privilegeService.updateManagerRoleList(manager_id, "");
			
			
			res.put("responseCode", "200");
			res.put("responseMsg", "删除成功");
		} else {
			res.put("responseCode", "201");
			res.put("responseMsg", "删除失败");
		}

		return res;
	}

	/**
	 * 角色管理界面
	 * 
	 * @return
	 * @date 2016-10-17
	 * @author zj
	 */
	@RequestMapping(value = "/InitRoleList")
	public ModelAndView InitRoleList() {
		ModelAndView view = new ModelAndView();
		view.setViewName("/privilege/role");

		return view;
	}

	/**
	 * 页面-获取角色列表
	 * 
	 * @param clientType
	 * @param packageName
	 * @param curPage
	 *            当前页
	 * @param pageCount
	 *            每页多少条
	 * @return
	 */
	@RequestMapping(value = "/getRoleList")
	public void getRoleList(HttpServletRequest request,
			HttpServletResponse response) {
		String role_id = request.getParameter("role_id");
		String role_name = request.getParameter("role_name");

		int pageCount = Integer.parseInt(request.getParameter("rows"));// request.getParameter("pageCount");
		int curPage = Integer.parseInt(request.getParameter("page"));

		Role query = new Role();
		query.setPage(curPage);
		query.setPageCount(pageCount);

		query.setRole_zh(role_name);
		query.setRole_en(role_id);

		List<Role> infos = roleDao.getRolePageList(query);
		int total = roleDao.getRolePageListCount(query);

		Gson gs = new Gson();
		String jsStr = gs.toJson(infos);

		String rst = "{\"total\":" + total + ",\"rows\":" + jsStr + "}";

		JsonUtil.responseOutWithJson(response, rst);

	}

	/**
	 * 编辑或添加后台管理员信息请求
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @date 2016-8-4
	 */
	@RequestMapping(value = "/initRoleInfo")
	public ModelAndView initRoleInfo(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView view = new ModelAndView();
		view.setViewName("/privilege/AddOrUpdateRole");

		String role_id = request.getParameter("role_id");
		Role query = new Role();
		query.setRole_en(role_id);
		Role bean = roleDao.getRoleInfoById(query);
		view.addObject("bean", bean);

		
//		List<MenuInfo> selectMenus = privilegeService.getRoleMenusList(bean);
//		
//		Role query2=new Role();
//		query2.setPage(1);
//		query2.setPageCount(1000);
//		
//		List<Role> AllRoles = roleDao.getRolePageList(query2);
//		
//		List<MenuInfo> menus = menuService.queryRootMenus();
//		logger.info("获得父菜单：" + menus);
//		List<MenuInfo> all_menus = menuService.queryMenusByParent(pid);
//		
//	
//		
//		view.addObject("roles", AllRoles);

		return view;
	}
	
	/**
	 * 构造用户树数据
	 * @param orgname   用户组或者用户名称
	 * @return
	 * @date 2016-3-3
	 * @author zj
	 */
	@RequestMapping(value = { "/getMenuTree" }, method = RequestMethod.POST)
	public @ResponseBody
	List<String> getMenuTree(@RequestParam("role_id") String role_id) {
		// 获取所有部门信息
	

		List<String> lstTree = new ArrayList<String>();

		try {

		
			Role query = new Role();
			query.setRole_en(role_id);
			Role bean = roleDao.getRoleInfoById(query);
		
			List<MenuInfo> selectMenus = privilegeService.getRoleMenusList(bean);
			

			List<MenuInfo> menus = menuService.queryRootMenus();
			logger.info("获得父菜单：" + menus);
			String rootid="1";
			for (MenuInfo menu : menus) {
					StringBuffer sBuffer = new StringBuffer();
					sBuffer.append("{");
					sBuffer.append("id:\"" + menu.getMenuId() + "\",");
					sBuffer.append("pId:\"" + rootid + "\",");
					sBuffer.append("open:true,");// 根节点打开
					
					if(selectMenus!=null)
					for (int i = 0; i < selectMenus.size(); i++) {
						if(selectMenus.get(i).getMenuId().equals(menu.getMenuId()))
						{
							sBuffer.append("checked:true,");// 选中
							break;
						}
					}
					
					sBuffer.append("name:\"" + menu.getMenuName() + "\",");
					
					sBuffer.append("remark:\"" + "" + "\"");
					//sBuffer.append("iconSkin:\"" + "diygroup" + "\"");
					//sBuffer.append("icon:\"" + "images/group2.png" + "\",");
				
					sBuffer.append("}");
					lstTree.add(sBuffer.toString());
			
			}

			for (int i = 0; i < menus.size(); i++) {
				List<MenuInfo> all_menus = menuService.queryMenusByParent(menus.get(i).getMenuId());
				
				for (MenuInfo menu : all_menus) {
					StringBuffer sBuffer = new StringBuffer();
					sBuffer.append("{");
					sBuffer.append("id:\"" + menu.getMenuId() + "\",");
					sBuffer.append("pId:\"" + menus.get(i).getMenuId() + "\",");
					sBuffer.append("open:true,");// 根节点打开
					
					sBuffer.append("name:\"" + menu.getMenuName() + "\",");
					
					if(selectMenus!=null)
					for (int j = 0; j < selectMenus.size(); j++) {
						if(selectMenus.get(j).getMenuId().equals(menu.getMenuId()))
						{
							sBuffer.append("checked:true,");// 选中
							break;
						}
					}
					
					sBuffer.append("remark:\"" + "" + "\"");
//					sBuffer.append("iconSkin:\"" + "diygroup" + "\"");
					//sBuffer.append("icon:\"" + "images/group2.png" + "\",");
				
					sBuffer.append("}");
					lstTree.add(sBuffer.toString());
			
			}
			}
		
			
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		

		System.out.println("lstTree:" + lstTree.toString());
		return lstTree;
	}


	/**
	 * 添加或者更新后角色信息
	 * 
	 * @param request
	 * @param response
	 * @date 2016-6-22
	 * @author zj
	 */
	@RequestMapping(value = "/addOrUpdateRoleInfo")
	public void addOrUpdateRoleInfo(HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {
		JSONObject jsonOut = new JSONObject();
		try {

			String role_id = request.getParameter("role_id");
			String role_name = request.getParameter("role_name");
			String menuids = request.getParameter("menuids");
			String desc = request.getParameter("desc");

			Role query = new Role();
			query.setRole_en(role_id);
			query.setRole_zh(role_name);
			Role tmp = roleDao.getRoleInfoById(query);

			Role sysUser = new Role();
			// banner.setBanner_name(banner_name);
			sysUser.setRole_en(role_id);
			sysUser.setRole_zh(role_name);
			sysUser.setRole_desc(desc);
			
			SysUserBean user = (SysUserBean) session
					.getAttribute(Constant.SESSION_USER);

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String time = sdf.format(new Date());
			sysUser.setUpdateddate(time);
			
			

			int rst = -1;

			if (tmp != null) {
				sysUser.setUpdateddate(time);
				if (user != null)
					sysUser.setUpdatedby(user.getName());

				rst = roleDao.updateRole(sysUser);

			} else {

				sysUser.setCreateby(user.getName());
				sysUser.setCreatedate(time);

				rst = roleDao.addRole(sysUser);
			}

			if (rst > 0)
				// 处理角色下的菜单
				rst = privilegeService.updateRoleMenuList(role_id, menuids);

			if (rst > 0) {
				jsonOut.put("ResponseCode", 200);
				jsonOut.put("ResponseMsg", "OK");
			} else {
				jsonOut.put("ResponseCode", 201);
				jsonOut.put("ResponseMsg", "FAILED");
			}

		} catch (Exception e2) {
			try {
				jsonOut.put("ResponseCode", "201");
				jsonOut.put("ResponseMsg", "FAILED");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		JsonUtil.responseOutWithJson(response, jsonOut.toString());

	}

	/**
	 * 删除后台用户
	 * 
	 * @param request
	 * @return
	 * @date 2016-8-22
	 */
	@RequestMapping(value = "/delRole")
	public @ResponseBody
	Map delRole(HttpServletRequest request) {
		Map res = new HashMap();
		String role_id = request.getParameter("role_id");

		Role query = new Role();

		query.setRole_en(role_id);

		if (roleDao.deleteRole(query) > 0) {
			
			privilegeService.updateRoleMenuList(role_id, "");
			
			res.put("responseCode", "200");
			res.put("responseMsg", "删除成功");
		} else {
			res.put("responseCode", "201");
			res.put("responseMsg", "删除失败");
		}

		return res;
	}

}
