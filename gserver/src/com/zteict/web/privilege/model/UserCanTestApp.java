package com.zteict.web.privilege.model;

import com.zteict.web.system.model.base.BaseModel;

/**
 * 用户是否在测试分组，是否可见测试中的APP
 * @date 2016-10-18
 * @author zj
 *
 */
public class UserCanTestApp extends BaseModel {

	private String userid; // 标题

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}
	


}
