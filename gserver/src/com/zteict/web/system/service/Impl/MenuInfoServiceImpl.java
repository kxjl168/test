package com.zteict.web.system.service.Impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.zteict.web.system.dao.MenuInfoDao;
import com.zteict.web.system.model.MenuInfo;
import com.zteict.web.system.service.MenuInfoService;




/**
 * 菜单 service实现
 * @author kangyongji
 *
 */
@Service(value="menuService")
public class MenuInfoServiceImpl implements MenuInfoService{

	@Autowired
	private MenuInfoDao menuDao;
	@Override
	public List<MenuInfo> queryRootMenus() {
		// TODO Auto-generated method stub
		return menuDao.queryRootMenus();
	}

	@Override
	public List<MenuInfo> queryMenusByParent(String parentId) {
		// TODO Auto-generated method stub
		return menuDao.queryMenusByParent(parentId);
	}

}
