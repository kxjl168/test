package com.zteict.web.company.service.Impl;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.zteict.web.company.dao.CompanyDao;
import com.zteict.web.company.model.Company;
import com.zteict.web.company.service.CompanyService;
import com.zteict.web.system.dao.SystemParamsDao;

@Service(value="CompanyService")
public class CompanyServiceImpl implements CompanyService{
	
	@Autowired
	CompanyDao companyDao;
	
	@Autowired
	SystemParamsDao sysDao;

	/**
	 * 分页获取banner列表
	 * @param query
	 * @return
	 * @date 2016-8-4
	 */
	@Override
	public List<Company> getCompanyPageList(Company query) {
		return companyDao.getCompanyPageList(query);
	}
	
	/**
	 * 登录
	 * @param bannerID
	 * @return
	 * @date 2016-8-4
	 */
	public Company getCompanyInfoByAccountAndPass(@Param(value="id")String id,@Param(value="pass")String pass)
	{
		return companyDao.getCompanyInfoByAccountAndPass(id, pass);
	}
	

	/**
	 * 获取banner总条数
	 * @param query
	 * @return
	 * @date 2016-8-4
	 */
	@Override
	public int getCompanyPageListCount(Company query) {
		return companyDao.getCompanyPageListCount(query);
	}

	@Override
	public int addCompany(Company Company) {
		return companyDao.addCompany(Company);
	}

	@Override
	public int deleteCompany(@Param("id") Integer id) {
		return companyDao.deleteCompany(id);
	}

	@Override
	public int updateCompany(Company Company) {
		return companyDao.updateCompany(Company);
	}
	/**
	 * 根据accountID获取Company信息
	 * @param bannerID
	 * @return
	 * @date 2016-8-4
	 */
	public Company getCompanyInfoByAccountId(@Param(value="id")String id)
	{
		return companyDao.getCompanyInfoByAccountId(id);
	}
	
	/**
	 * 根据ID获取Company信息
	 * @param bannerID
	 * @return
	 * @date 2016-8-4
	 */
	public Company getCompanyInfoById(@Param(value="id")Integer id)
	{
		return companyDao.getCompanyInfoById(id);
	}

	
}
