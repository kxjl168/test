package com.zteict.web.phoneaccount.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zteict.web.phoneaccount.model.PhoneAccount;



public interface PhoneAccountDao {

	
	/**
	 * 分页获取banner列表
	 * @param query
	 * @return
	 * @date 2016-8-4
	 */
	public List<PhoneAccount> getPhoneAccountPageList(PhoneAccount query);

	/**
	 * 获取banner总条数
	 * @param query
	 * @return
	 * @date 2016-8-4
	 */
	public int getPhoneAccountPageListCount(PhoneAccount query);
	
	/**
	 * 添加PhoneAccount
	 * @param PhoneAccount
	 * @return
	 */
	public int addPhoneAccount(PhoneAccount PhoneAccount);
	
	/**
	 * 删除PhoneAccount
	 * @param id
	 * @return
	 */
	public int deletePhoneAccount(@Param(value="id")Integer id);

	/**
	 * 更新PhoneAccount
	 * @param PhoneAccount
	 * @return
	 */
	public int updatePhoneAccount(PhoneAccount PhoneAccount);
	
	

	/**
	 * 根据accountID获取PhoneAccount信息
	 * @param bannerID
	 * @return
	 * @date 2016-8-4
	 */
	public PhoneAccount getPhoneAccountInfoByAccountId(@Param(value="id")String id);
	
	/**
	 * 根据ID获取PhoneAccount信息
	 * @param bannerID
	 * @return
	 * @date 2016-8-4
	 */
	public PhoneAccount getPhoneAccountInfoById(@Param(value="id")Integer id);
}
