package com.zteict.web.privilege.model;

import com.zteict.web.system.model.base.BaseModel;

/**
 * 角色
 * @date 2016-10-14
 * @author zj
 * 
 */
public class Role extends BaseModel {

	private String role_en; // 标题
	private String role_zh;
	private String role_desc;

	private String createby; // 创建者
	private String createdate; // 创建时间
	private String updatedby; // 更新者
	private String updateddate;// 更新时间
	
	
	private String select;// 更新时间
	

	public String getRole_en() {
		return role_en;
	}

	public void setRole_en(String role_en) {
		this.role_en = role_en;
	}

	public String getRole_zh() {
		return role_zh;
	}

	public void setRole_zh(String role_zh) {
		this.role_zh = role_zh;
	}

	public String getRole_desc() {
		return role_desc;
	}

	public void setRole_desc(String role_desc) {
		this.role_desc = role_desc;
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

	public String getSelect() {
		return select;
	}

	public void setSelect(String select) {
		this.select = select;
	}

}
