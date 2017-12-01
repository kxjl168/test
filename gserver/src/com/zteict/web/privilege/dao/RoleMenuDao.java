package com.zteict.web.privilege.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;


import com.zteict.web.privilege.model.RoleMenu;
import com.zteict.web.privilege.model.Role;
import com.zteict.web.privilege.model.RoleMenu;
import com.zteict.web.system.model.MenuInfo;
import com.zteict.web.system.model.SysUserBean;

public interface RoleMenuDao {

	/**
	 * 添加RoleMenu
	 * 
	 * @param RoleMenu
	 * @return
	 */
	public int addRoleMenu(RoleMenu query);

	/**
	 * 删除角色下的所有菜单
	 * 
	 * @param id
	 * @return
	 */
	public int deleteRoleMenu(RoleMenu query);



	/**
	 * 获取橘色的所有菜单
	 * 
	 * @param query
	 * @return
	 * @date 2016-8-4
	 */
	public List<MenuInfo> getRoleMenuList(Role query);
}
