package com.zteict.web.privilege.service;

import java.util.List;


import com.zteict.web.privilege.model.Role;
import com.zteict.web.system.model.MenuInfo;
import com.zteict.web.system.model.SysUserBean;

public interface PrivilegeService {
	

	

	/**
	 * 更新角色下的可见菜单数据
	 * @param role_id
	 * @param menuids
	 * @return
	 * @date 2016-10-17
	 * @author zj
	 */
	public int updateRoleMenuList(String role_id, String menuids);
	
	

	/**
	 * 更新后台管理员的角色数据
	 * @param role_id
	 * @param menuids
	 * @return
	 * @date 2016-10-17
	 * @author zj
	 */
	public int updateManagerRoleList(String manager_id, String roleids);
	
	

	/**
	 * 获取用户的所有角色
	 * 
	 * @param query
	 * @return
	 * @date 2016-8-4
	 */
	public List<Role> getManagerRoleList(SysUserBean query);

	
	/**
	 * 获取用户的所有菜单
	 * 
	 * @param query
	 * @return
	 * @date 2016-8-4
	 */
	public List<MenuInfo> getManagerMenusList(SysUserBean query);
	
	
	/**
	 * 获取角色的所有菜单
	 * 
	 * @param query
	 * @return
	 * @date 2016-8-4
	 */
	public List<MenuInfo> getRoleMenusList(Role query);
	
	
	
	
}
