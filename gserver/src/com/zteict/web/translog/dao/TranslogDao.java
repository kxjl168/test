package com.zteict.web.translog.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;


import com.zteict.web.translog.model.Translog;

public interface TranslogDao {

	
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
	
	/*
	*//**
	 * 添加Translog
	 * @param Translog
	 * @return
	 *//*
	public int addTranslog(Translog Translog);
	
	*//**
	 * 删除Translog
	 * @param id
	 * @return
	 *//*
	public int deleteTranslog(@Param(value="banner_id")Integer id);

	*//**
	 * 更新Translog
	 * @param Translog
	 * @return
	 *//*
	public int updateTranslog(Translog Translog);
	
	*//**
	 *  获取Translog列表
	 * @return
	 *//*
	public List<Translog> getTranslogList();



	*//**
	 * 根据ID获取Translog信息
	 * @param bannerID
	 * @return
	 * @date 2016-8-4
	 *//*
	public Translog getTranslogInfoById(@Param(value="banner_id")Integer banner_id);*/
}
