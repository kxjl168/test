package com.zteict.web.translog.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.zteict.web.translog.dao.TranslogDao;
import com.zteict.web.translog.model.Translog;
import com.zteict.web.translog.service.TranslogService;
import com.zteict.web.system.dao.SystemParamsDao;

@Service(value="TranslogService")
public class TranslogServiceImpl implements TranslogService{
	
	@Autowired
	TranslogDao bannerDao;
	
	@Autowired
	SystemParamsDao sysDao;

	/**
	 * 分页获取banner列表
	 * @param query
	 * @return
	 * @date 2016-8-4
	 */
	@Override
	public List<Translog> getTranslogPageList(Translog query) {
		return bannerDao.getTranslogPageList(query);
	}

	/**
	 * 获取banner总条数
	 * @param query
	 * @return
	 * @date 2016-8-4
	 */
	@Override
	public int getTranslogPageListCount(Translog query) {
		return bannerDao.getTranslogPageListCount(query);
	}

	
}
