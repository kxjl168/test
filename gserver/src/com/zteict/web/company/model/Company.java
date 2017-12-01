package com.zteict.web.company.model;

import com.zteict.web.system.model.base.BaseModel;


public class Company  extends BaseModel{
	private Integer	recordid;    		//id

	
/*	 
 *  `desc` varchar(256) DEFAULT NULL COMMENT 'desc',
  `pass` varchar(64) DEFAULT NULL COMMENT 'pass',
  `userid` varchar(32) DEFAULT NULL COMMENT 'userid',
  `ip_desc` varchar(256) DEFAULT NULL COMMENT 'ip_desc',
  `ip_refresh_interval` varchar(256) DEFAULT NULL COMMENT '出口IP刷新周期',
	*/
	private String  company_name;      
	private String 	desc_info;     	
	
	private String 	pass;  
	private String 	accountid;	//链接url
	private String 	ip_desc;  	//创建者
	private String 	ip_refresh_interval;	//创建时间
	
	
	private String  create_date;
	private String  update_date;
	public Integer getRecordid() {
		return recordid;
	}
	public void setRecordid(Integer recordid) {
		this.recordid = recordid;
	}
	
	
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getIp_desc() {
		return ip_desc;
	}
	public void setIp_desc(String ip_desc) {
		this.ip_desc = ip_desc;
	}
	public String getIp_refresh_interval() {
		return ip_refresh_interval;
	}
	public void setIp_refresh_interval(String ip_refresh_interval) {
		this.ip_refresh_interval = ip_refresh_interval;
	}
	public String getCreate_date() {
		return create_date;
	}
	public void setCreate_date(String create_date) {
		this.create_date = create_date;
	}
	public String getUpdate_date() {
		return update_date;
	}
	public void setUpdate_date(String update_date) {
		this.update_date = update_date;
	}
	public String getAccountid() {
		return accountid;
	}
	public void setAccountid(String accountid) {
		this.accountid = accountid;
	}
	public String getCompany_name() {
		return company_name;
	}
	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}
	public String getDesc_info() {
		return desc_info;
	}
	public void setDesc_info(String desc_info) {
		this.desc_info = desc_info;
	}
	
	
	
	
	
}
