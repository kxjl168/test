package com.zteict.web.privilege.model;

import com.zteict.web.system.model.base.BaseModel;

/**
 * 角色-菜单关系
 * @date 2016-10-14
 * @author zj
 * 
 */
public class RoleMenu extends BaseModel {

	private String role_id; // 标题
	
	private String menu_id;
	public String getRole_id() {
		return role_id;
	}
	public void setRole_id(String role_id) {
		this.role_id = role_id;
	}
	public String getMenu_id() {
		return menu_id;
	}
	public void setMenu_id(String menu_id) {
		this.menu_id = menu_id;
	}
	

}
