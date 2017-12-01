package com.zteict.web.system.service.Impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zteict.tool.utils.DateUtil;
import com.zteict.web.system.dao.MenuInfoDao;
import com.zteict.web.system.dao.SvrFileInfoDao;
import com.zteict.web.system.dao.SysUserDao;
import com.zteict.web.system.dao.SystemParamsDao;
import com.zteict.web.system.model.MenuInfo;
import com.zteict.web.system.model.SvrFileInfo;
import com.zteict.web.system.model.SysUserBean;
import com.zteict.web.system.service.MenuInfoService;
import com.zteict.web.system.service.SvrFileInfoService;
import com.zteict.web.system.service.SysUserService;

@Service
public class SysUserServiceImpl implements SysUserService {

	@Autowired
	private SysUserDao sysUserDao;

	/**
	 * 根据用户名和密码查询后台登录用户账号信息
	 * 
	 * @param userid
	 * @param password
	 * @return
	 */
	public SysUserBean getUserInfoByUseridAndPwd(SysUserBean model) {
		return sysUserDao.getUserInfoByUseridAndPwd(model);
	}

	/**
	 * 更新系统用户信息- 密码
	 * 
	 * @param model
	 * @return
	 * @date 2016-7-29
	 * @author zj
	 */
	public int updateSysuer(SysUserBean model) {
		return sysUserDao.updateSysuer(model);
	}

	public List<SysUserBean> getUserListInfo(SysUserBean model)

	{
		return sysUserDao.getUserListInfo(model);
	}

}
