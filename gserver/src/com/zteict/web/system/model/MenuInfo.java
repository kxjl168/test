package com.zteict.web.system.model;

/**
 *  菜单信息
 * @date 2016-7-29
 * @author zj
 *
 */
public class MenuInfo {

	private String menuId;    //菜单Id
	private String menuOrderid; //菜单排序ID
	private String menuParentid; //父菜单
	private String menuName; //菜单名
	private String menuUrl; //菜单URL
	
	private String menuICO; //菜单ICO
	
	private String menuGroup; //菜单ICO
	
	public String getMenuId() {
		return menuId;
	}
	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}
	public String getMenuOrderid() {
		return menuOrderid;
	}
	public void setMenuOrderid(String menuOrderid) {
		this.menuOrderid = menuOrderid;
	}
	public String getMenuParentid() {
		return menuParentid;
	}
	public void setMenuParentid(String menuParentid) {
		this.menuParentid = menuParentid;
	}
	public String getMenuName() {
		return menuName;
	}
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
	public String getMenuUrl() {
		return menuUrl;
	}
	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}
	public String getMenuICO() {
		return menuICO;
	}
	public void setMenuICO(String menuICO) {
		this.menuICO = menuICO;
	}
	public String getMenuGroup() {
		return menuGroup;
	}
	public void setMenuGroup(String menuGroup) {
		this.menuGroup = menuGroup;
	}
}
