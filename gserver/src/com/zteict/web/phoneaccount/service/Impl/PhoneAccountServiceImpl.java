package com.zteict.web.phoneaccount.service.Impl;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.zteict.web.phoneaccount.dao.PhoneAccountDao;
import com.zteict.web.phoneaccount.model.PhoneAccount;
import com.zteict.web.phoneaccount.service.PhoneAccountService;
import com.zteict.web.system.dao.SystemParamsDao;

@Service(value="PhoneAccountService")
public class PhoneAccountServiceImpl implements PhoneAccountService{
	
	@Autowired
	PhoneAccountDao phoneDao;
	
	@Autowired
	SystemParamsDao sysDao;

	/**
	 * 分页获取banner列表
	 * @param query
	 * @return
	 * @date 2016-8-4
	 */
	@Override
	public List<PhoneAccount> getPhoneAccountPageList(PhoneAccount query) {
		return phoneDao.getPhoneAccountPageList(query);
	}

	/**
	 * 获取banner总条数
	 * @param query
	 * @return
	 * @date 2016-8-4
	 */
	@Override
	public int getPhoneAccountPageListCount(PhoneAccount query) {
		return phoneDao.getPhoneAccountPageListCount(query);
	}

	@Override
	public int addPhoneAccount(PhoneAccount PhoneAccount) {
		return phoneDao.addPhoneAccount(PhoneAccount);
	}

	@Override
	public int deletePhoneAccount(@Param("id") Integer id) {
		return phoneDao.deletePhoneAccount(id);
	}

	@Override
	public int updatePhoneAccount(PhoneAccount PhoneAccount) {
		return phoneDao.updatePhoneAccount(PhoneAccount);
	}
	/**
	 * 根据accountID获取PhoneAccount信息
	 * @param bannerID
	 * @return
	 * @date 2016-8-4
	 */
	public PhoneAccount getPhoneAccountInfoByAccountId(@Param(value="id")String id)
	{
		return phoneDao.getPhoneAccountInfoByAccountId(id);
	}
	
	/**
	 * 根据ID获取PhoneAccount信息
	 * @param bannerID
	 * @return
	 * @date 2016-8-4
	 */
	public PhoneAccount getPhoneAccountInfoById(@Param(value="id")Integer id)
	{
		return phoneDao.getPhoneAccountInfoById(id);
	}

	
}
