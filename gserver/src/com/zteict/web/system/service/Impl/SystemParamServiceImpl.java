package com.zteict.web.system.service.Impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zteict.web.system.dao.SystemParamsDao;
import com.zteict.web.system.model.DictInfo;
import com.zteict.web.system.model.SysParameter;
import com.zteict.web.system.service.SystemParamService;


@Service("systemService")
public class SystemParamServiceImpl implements SystemParamService{
	@Autowired
	public SystemParamsDao dao;
	
	
	
	
	/**
	 * 查询字典
	 * @param query
	 * @return
	 * @date 2016-7-25
	 * @author zj
	 */
	public List<DictInfo> getDictInfoList(DictInfo query)
	{
		return dao.getDictInfoList(query);
	}
	
	
	/**
	 * 查询字典数
	 * @param query
	 * @return
	 * @date 2016-8-5
	 * @author zj
	 */
	public Integer getDictInfoCount(DictInfo query)
	{
		return dao.getDictInfoCount( query);
		
	}
	
	
	
	@Override
	public List<HashMap<String, String>> getParams() {
		return dao.getParams();
	}

	@Override
	public String getParamValue(String paramName) {
		// TODO Auto-generated method stub
		return dao.getParamValue(paramName);
	}
	
	  /**
	   * 获取系统设置参数
	   *  @author yexiufang
	   * @return
	   */
	@Override
	public List<HashMap<String, String>> getSysParams() {
		// TODO Auto-generated method stub
		return dao.getSysParams();
	}

	public int updateOneSysParams(SysParameter sysParam) {
		// TODO Auto-generated method stub
		return dao.updateOneSysParams(sysParam);
	}

	@Override
	public SysParameter getOneSysParams(String param_name) {
		// TODO Auto-generated method stub
		return dao.getOneSysParams(param_name);
	}

	@Override
	public int addOneSysParams(SysParameter sysParam) {
		// TODO Auto-generated method stub
		return dao.addOneSysParams(sysParam);
	}

	@Override
	public int deleteOneSysParams(String param_name) {
		// TODO Auto-generated method stub
		return dao.deleteOneSysParams(param_name);
	}
}
