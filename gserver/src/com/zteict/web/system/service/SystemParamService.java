package com.zteict.web.system.service;

import java.util.HashMap;
import java.util.List;

import com.zteict.web.system.model.DictInfo;
import com.zteict.web.system.model.SysParameter;



public interface SystemParamService {
 
	
	
	
	
	/**
	 * 查询字典
	 * @param query
	 * @return
	 * @date 2016-7-25
	 * @author zj
	 */
	public List<DictInfo> getDictInfoList(DictInfo query);
	
	/**
	 * 查询字典数
	 * @param query
	 * @return
	 * @date 2016-8-5
	 * @author zj
	 */
	public Integer getDictInfoCount(DictInfo query);
	
	
	
	
	/**
   * 获取ParamName,ParamValue
   * @return
   */
  public List<HashMap<String , String>> getParams(); 
  
  
  public String getParamValue(String paramName);
  
  /**
   * 获取系统设置参数
   *  @author yexiufang
   * @return 
   */
  public List<HashMap<String , String>> getSysParams();
  
  /**
   * 获取系统设置参数
   *  @author yexiufang
   * @return 
   */
  public SysParameter getOneSysParams(String param_name);
  
  /**
   * 修改系统设置参数
   *  @author yexiufang
   * @return
   */
 public int  updateOneSysParams(SysParameter sysParam);
 
 /**
  * 增加系统设置参数
  *  @author yexiufang
  * @return
  */
 public int  addOneSysParams(SysParameter sysParam);
 
	/**
  * 删除系统设置参数
  *  @author yexiufang
  * @return
  */
public int  deleteOneSysParams(String param_name);

}
