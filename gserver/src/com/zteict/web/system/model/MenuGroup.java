package com.zteict.web.system.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 菜单分组
 * 
 * @date 2016-7-28
 * @author zj
 * 
 */
public class MenuGroup {

	private String groupName=""; // 菜单Id
	private List<MenuInfo> menus=new ArrayList<MenuInfo>();

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public List<MenuInfo> getMenus() {
		return menus;
	}

	public void setMenus(List<MenuInfo> menus) {
		this.menus = menus;
	}

}
