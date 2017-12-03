package com.zteict.web.translog.service;

import java.util.List;



import com.zteict.web.translog.model.Translog;


public interface TranslogService {
	/**
	 * 分页获取banner列表
	 * @param query
	 * @return
	 * @date 2016-8-4
	 */
	public List<Translog> getTranslogPageList(Translog query);

	/**
	 * 获取banner总条数
	 * @param query
	 * @return
	 * @date 2016-8-4
	 */
	public int getTranslogPageListCount(Translog query);
	
}
