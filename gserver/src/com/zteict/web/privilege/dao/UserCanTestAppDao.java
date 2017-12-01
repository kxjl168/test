package com.zteict.web.privilege.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;


import com.zteict.web.privilege.model.UserCanTestApp;
import com.zteict.web.system.model.MenuInfo;
import com.zteict.web.system.model.SysUserBean;

public interface UserCanTestAppDao {

	/**
	 * 添加UserCanTestApp
	 * 
	 * @param UserCanTestApp
	 * @return
	 */
	public int addUserCanTestApp(UserCanTestApp UserCanTestApp);

	/**
	 * 删除UserCanTestApp
	 * 
	 * @param id
	 * @return
	 */
	public int deleteUserCanTestApp(UserCanTestApp role);

	/**

	/**
	 * 分页获取banner列表
	 * 
	 * @param query
	 * @return
	 * @date 2016-8-4
	 */
	public List<UserCanTestApp> getUserCanTestAppPageList(UserCanTestApp query);

	/**
	 * 获取banner总条数
	 * 
	 * @param query
	 * @return
	 * @date 2016-8-4
	 */
	public int getUserCanTestAppPageListCount(UserCanTestApp query);

	/**
	 * 根据ID获取UserCanTestApp信息
	 * 
	 * @param bannerID
	 * @return
	 * @date 2016-8-4
	 */
	public UserCanTestApp getUserCanTestAppInfoById(UserCanTestApp query);
	
	

}
