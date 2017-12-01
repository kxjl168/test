package com.zteict.web.company.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zteict.web.company.model.Company;



public interface CompanyDao {

	
	/**
	 * 分页获取banner列表
	 * @param query
	 * @return
	 * @date 2016-8-4
	 */
	public List<Company> getCompanyPageList(Company query);

	/**
	 * 获取banner总条数
	 * @param query
	 * @return
	 * @date 2016-8-4
	 */
	public int getCompanyPageListCount(Company query);
	
	/**
	 * 添加Company
	 * @param Company
	 * @return
	 */
	public int addCompany(Company Company);
	
	/**
	 * 删除Company
	 * @param id
	 * @return
	 */
	public int deleteCompany(@Param(value="id")Integer id);

	/**
	 * 更新Company
	 * @param Company
	 * @return
	 */
	public int updateCompany(Company Company);
	
	

	/**
	 * 根据accountID获取Company信息
	 * @param bannerID
	 * @return
	 * @date 2016-8-4
	 */
	public Company getCompanyInfoByAccountId(@Param(value="id")String id);
	
	/**
	 * 根据ID获取Company信息
	 * @param bannerID
	 * @return
	 * @date 2016-8-4
	 */
	public Company getCompanyInfoById(@Param(value="id")Integer id);
}
