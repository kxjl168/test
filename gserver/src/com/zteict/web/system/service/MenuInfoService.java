package com.zteict.web.system.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.zteict.web.system.model.MenuInfo;




/**
 * 菜单 service
 * @author kangyongji
 *
 */

public interface MenuInfoService {
	
	/**
	 * 查询跟菜单
	 * @return
	 */
	public List<MenuInfo> queryRootMenus();
	
	/**
	 * 根据父菜单ID查询子菜单
	 * @param parentId
	 * @return
	 */
	public List<MenuInfo> queryMenusByParent(String parentId);
}
