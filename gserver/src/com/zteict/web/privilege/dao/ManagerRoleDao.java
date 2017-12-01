package com.zteict.web.privilege.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;


import com.zteict.web.privilege.model.ManagerRole;
import com.zteict.web.privilege.model.Role;
import com.zteict.web.system.model.MenuInfo;
import com.zteict.web.system.model.SysUserBean;

public interface ManagerRoleDao {

	/**
	 * 添加ManagerRole
	 * 
	 * @param ManagerRole
	 * @return
	 */
	public int addManagerRole(ManagerRole ManagerRole);

	/**
	 * 删除用户的所有角色
	 * 
	 * @param id
	 * @return
	 */
	public int deleteManagerRole(ManagerRole user);



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
	
}
