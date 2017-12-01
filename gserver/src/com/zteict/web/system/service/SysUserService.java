package com.zteict.web.system.service;

import java.util.HashMap;
import java.util.List;

import com.zteict.web.system.model.DictInfo;
import com.zteict.web.system.model.SysParameter;
import com.zteict.web.system.model.SysUserBean;

public interface SysUserService {

	/**
	 * 根据用户名和密码查询后台登录用户账号信息
	 * 
	 * @param userid
	 * @param password
	 * @return
	 */
	public SysUserBean getUserInfoByUseridAndPwd(SysUserBean model);

	
	/**
	 * 更新系统用户信息- 密码
	 * @param model
	 * @return
	 * @date 2016-7-29
	 * @author zj
	 */
	public int updateSysuer(SysUserBean model);

	public List<SysUserBean> getUserListInfo(SysUserBean model) ;
}
