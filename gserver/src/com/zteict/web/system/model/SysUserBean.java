package com.zteict.web.system.model;

import com.zteict.web.system.model.base.BaseModel;

/**
 * 后台登录用户
 * @date 2016-7-29
 * @author zj
 *
 */
public class SysUserBean extends BaseModel {
	private String userid;// 用户名
	private String name;// 用户名

	private String password;// 密码

	
	private String createby; // 创建者
	private String createdate; // 创建时间
	private String updatedby; // 更新者
	private String updateddate;// 更新时间

	
	
	//查询字段
	private String role_id;   //角色
	private String role_name;

	private String menu_ids; //可见菜单

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole_id() {
		return role_id;
	}

	public void setRole_id(String role_id) {
		this.role_id = role_id;
	}

	public String getRole_name() {
		return role_name;
	}

	public void setRole_name(String role_name) {
		this.role_name = role_name;
	}

	public String getMenu_ids() {
		return menu_ids;
	}

	public void setMenu_ids(String menu_ids) {
		this.menu_ids = menu_ids;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCreateby() {
		return createby;
	}

	public void setCreateby(String createby) {
		this.createby = createby;
	}

	public String getCreatedate() {
		return createdate;
	}

	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}

	public String getUpdatedby() {
		return updatedby;
	}

	public void setUpdatedby(String updatedby) {
		this.updatedby = updatedby;
	}

	public String getUpdateddate() {
		return updateddate;
	}

	public void setUpdateddate(String updateddate) {
		this.updateddate = updateddate;
	}


}