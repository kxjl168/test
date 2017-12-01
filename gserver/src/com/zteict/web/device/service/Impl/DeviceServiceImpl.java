package com.zteict.web.device.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.zteict.web.device.dao.DeviceDao;
import com.zteict.web.device.model.Device;
import com.zteict.web.device.service.DeviceService;
import com.zteict.web.system.dao.SystemParamsDao;

@Service(value="DeviceService")
public class DeviceServiceImpl implements DeviceService{
	
	@Autowired
	DeviceDao bannerDao;
	
	@Autowired
	SystemParamsDao sysDao;

	/**
	 * 分页获取banner列表
	 * @param query
	 * @return
	 * @date 2016-8-4
	 */
	@Override
	public List<Device> getDevicePageList(Device query) {
		return bannerDao.getDevicePageList(query);
	}

	/**
	 * 获取banner总条数
	 * @param query
	 * @return
	 * @date 2016-8-4
	 */
	@Override
	public int getDevicePageListCount(Device query) {
		return bannerDao.getDevicePageListCount(query);
	}

	
}
