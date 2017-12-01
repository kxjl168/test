package com.zteict.web.device.service;

import java.util.List;



import com.zteict.web.device.model.Device;


public interface DeviceService {
	/**
	 * 分页获取banner列表
	 * @param query
	 * @return
	 * @date 2016-8-4
	 */
	public List<Device> getDevicePageList(Device query);

	/**
	 * 获取banner总条数
	 * @param query
	 * @return
	 * @date 2016-8-4
	 */
	public int getDevicePageListCount(Device query);
	
}
