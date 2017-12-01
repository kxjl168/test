package com.zteict.web.privilege.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;


import com.zteict.web.system.model.SysUserBean;

public interface SysUserBeanDao {

	/**
	 * 添加SysUserBean
	 * 
	 * @param SysUserBean
	 * @return
	 */
	public int addSysUserBean(SysUserBean SysUserBean);

	/**
	 * 删除SysUserBean
	 * 
	 * @param id
	 * @return
	 */
	public int deleteSysUserBean(SysUserBean role);

	/**
	 * 更新SysUserBean
	 * 
	 * @param SysUserBean
	 * @return
	 */
	public int updateSysUserBean(SysUserBean SysUserBean);

	/**
	 * 分页获取banner列表
	 * 
	 * @param query
	 * @return
	 * @date 2016-8-4
	 */
	public List<SysUserBean> getSysUserBeanPageList(SysUserBean query);

	/**
	 * 获取banner总条数
	 * 
	 * @param query
	 * @return
	 * @date 2016-8-4
	 */
	public int getSysUserBeanPageListCount(SysUserBean query);

	/**
	 * 根据ID获取SysUserBean信息
	 * 
	 * @param bannerID
	 * @return
	 * @date 2016-8-4
	 */
	public SysUserBean getSysUserBeanInfoById(SysUserBean query);
}
