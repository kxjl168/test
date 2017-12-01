package com.zteict.web.system.dao;

import java.util.List;

import com.zteict.web.system.model.MenuInfo;
import com.zteict.web.system.model.SvrFileInfo;



/**
 * 文件服务器记录 Dao
 * @date 2016-7-12
 * @author zj
 *
 */
public interface SvrFileInfoDao {
	
	/**
	 * 存储文件
	 * @param info
	 * @return
	 * @date 2016-7-12
	 * @author zj
	 */
	public int SaveFileInfo(SvrFileInfo info);
	
	
	/**
	 * 存储文件
	 * @param info
	 * @return
	 * @date 2016-7-12
	 * @author zj
	 */
	public int DeleteFileInfo(SvrFileInfo info);
	
	
	
	/**
	 * 文件下载+1
	 * @param info
	 * @return
	 * @date 2016-7-12
	 * @author zj
	 */
	public int addFileDonwLoadNums(SvrFileInfo info);
	
	/**
	 * 获取文件信息
	 * @param info
	 * @return
	 * @date 2016-7-12
	 * @author zj
	 */
	public SvrFileInfo getFileInfo(SvrFileInfo info);

}
