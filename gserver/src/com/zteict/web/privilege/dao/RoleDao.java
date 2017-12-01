package com.zteict.web.privilege.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;


import com.zteict.web.privilege.model.Role;
import com.zteict.web.system.model.MenuInfo;
import com.zteict.web.system.model.SysUserBean;

public interface RoleDao {

	/**
	 * 添加Role
	 * 
	 * @param Role
	 * @return
	 */
	public int addRole(Role Role);

	/**
	 * 删除Role
	 * 
	 * @param id
	 * @return
	 */
	public int deleteRole(Role role);

	/**
	 * 更新Role
	 * 
	 * @param Role
	 * @return
	 */
	public int updateRole(Role Role);

	/**
	 * 分页获取banner列表
	 * 
	 * @param query
	 * @return
	 * @date 2016-8-4
	 */
	public List<Role> getRolePageList(Role query);

	/**
	 * 获取banner总条数
	 * 
	 * @param query
	 * @return
	 * @date 2016-8-4
	 */
	public int getRolePageListCount(Role query);

	/**
	 * 根据ID获取Role信息
	 * 
	 * @param bannerID
	 * @return
	 * @date 2016-8-4
	 */
	public Role getRoleInfoById(Role query);
	
	
	/**
	 * 获取角色的所有菜单
	 * 
	 * @param query
	 * @return
	 * @date 2016-8-4
	 */
	public List<MenuInfo> getRoleMenusList(Role query);
	
}
