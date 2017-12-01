package com.zteict.web.privilege.model;

import com.zteict.web.system.model.base.BaseModel;

/**
 * 后台管理员-角色
 * @date 2016-10-14
 * @author zj
 * 
 */
public class ManagerRole extends BaseModel {

	private String role_id; // 标题
	private String manager_id;
	public String getRole_id() {
		return role_id;
	}
	public void setRole_id(String role_id) {
		this.role_id = role_id;
	}
	public String getManager_id() {
		return manager_id;
	}
	public void setManager_id(String manager_id) {
		this.manager_id = manager_id;
	}
	


}
