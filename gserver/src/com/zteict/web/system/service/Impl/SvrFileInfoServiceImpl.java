package com.zteict.web.system.service.Impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.zteict.tool.utils.DateUtil;
import com.zteict.web.system.dao.MenuInfoDao;
import com.zteict.web.system.dao.SvrFileInfoDao;
import com.zteict.web.system.dao.SystemParamsDao;
import com.zteict.web.system.model.MenuInfo;
import com.zteict.web.system.model.SvrFileInfo;
import com.zteict.web.system.service.MenuInfoService;
import com.zteict.web.system.service.SvrFileInfoService;



@Service
public class SvrFileInfoServiceImpl implements SvrFileInfoService{

	@Autowired
	private SvrFileInfoDao fileDao;
	
	@Autowired
	private SystemParamsDao sysDao;

	@Override
	public int SaveFileInfo(SvrFileInfo info) {
		
		Integer id= sysDao.getSeqNextVal("seq_svr_file");
		info.setId(id);
		info.setSave_date(DateUtil.getNowStr(""));
		
		return fileDao.SaveFileInfo(info);
	}

	@Override
	public int DeleteFileInfo(SvrFileInfo info) {
		return fileDao.DeleteFileInfo(info);
	}

	@Override
	public int addFileDonwLoadNums(SvrFileInfo info) {
		return fileDao.addFileDonwLoadNums(info);
	}

	@Override
	public SvrFileInfo getFileInfo(SvrFileInfo info) {
		return fileDao.getFileInfo(info);
	}
	

}
