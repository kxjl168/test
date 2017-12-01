package com.zteict.web.privilege.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



import com.zteict.web.privilege.dao.ManagerRoleDao;
import com.zteict.web.privilege.dao.RoleDao;
import com.zteict.web.privilege.dao.RoleMenuDao;
import com.zteict.web.privilege.model.ManagerRole;
import com.zteict.web.privilege.model.Role;
import com.zteict.web.privilege.model.RoleMenu;
import com.zteict.web.privilege.service.PrivilegeService;

import com.zteict.web.system.dao.SystemParamsDao;
import com.zteict.web.system.model.MenuInfo;
import com.zteict.web.system.model.SysUserBean;

@Service(value = "PrivilegeService")
public class PrivilegeServiceImpl implements PrivilegeService {

	@Autowired
	RoleMenuDao roleMenuDao;

	@Autowired
	ManagerRoleDao managerRoleDao;

	@Autowired
	RoleDao roleDao;
	
	
	@Override
	public int updateRoleMenuList(String role_id, String menuids) {

		int rst = -1;
		try {
			RoleMenu query = new RoleMenu();
			query.setRole_id(role_id);

			// 清空
			roleMenuDao.deleteRoleMenu(query);

			// 添加
			String[] menus = menuids.split(",");
			for (int i = 0; i < menus.length; i++) {
				if (menus[i].trim().equals(""))
					continue;

				RoleMenu item = new RoleMenu();
				item.setRole_id(role_id);
				item.setMenu_id(menus[i]);

				roleMenuDao.addRoleMenu(item);
			}

			rst = 1;
		} catch (Exception e) {
			// TODO: handle exception
		}

		return rst;
	}

	@Override
	public int updateManagerRoleList(String manager_id, String roleids) {
		int rst = -1;
		try {

			ManagerRole query = new ManagerRole();
			query.setManager_id(manager_id);

			// 清空
			managerRoleDao.deleteManagerRole(query);

			// 添加
			String[] menus = roleids.split(",");
			for (int i = 0; i < menus.length; i++) {
				if (menus[i].trim().equals(""))
					continue;

				ManagerRole item = new ManagerRole();
				item.setManager_id(manager_id);
				item.setRole_id(menus[i]);

				managerRoleDao.addManagerRole(item);
			}

			rst = 1;
		} catch (Exception e) {
			// TODO: handle exception
		}

		return rst;
	}
	
	

	/**
	 * 获取用户的所有角色
	 * 
	 * @param query
	 * @return
	 * @date 2016-8-4
	 */
	public List<Role> getManagerRoleList(SysUserBean query)
	{
		return managerRoleDao.getManagerRoleList(query);
	}

	
	/**
	 * 获取用户的所有菜单
	 * 
	 * @param query
	 * @return
	 * @date 2016-8-4
	 */
	public List<MenuInfo> getManagerMenusList(SysUserBean query){
		return managerRoleDao.getManagerMenusList(query);
	}
	
	
	/**
	 * 获取角色的所有菜单
	 * 
	 * @param query
	 * @return
	 * @date 2016-8-4
	 */
	public List<MenuInfo> getRoleMenusList(Role query)
	{
		return roleDao.getRoleMenusList(query);
	}
	

}
