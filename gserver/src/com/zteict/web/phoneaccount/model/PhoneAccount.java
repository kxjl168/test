package com.zteict.web.phoneaccount.model;

import com.zteict.web.system.model.base.BaseModel;


public class PhoneAccount  extends BaseModel{
	private Integer	recordid;    		//id

	
/*	 `userid` varchar(32) DEFAULT NULL COMMENT 'userid',
	  `pass` varchar(64) DEFAULT NULL COMMENT 'pass',
	  `ip_refresh_interval` varchar(256) DEFAULT NULL COMMENT '出口IP刷新周期',
	  `position` varchar(64) DEFAULT NULL COMMENT '出口位置',
	  `ip` varchar(128) DEFAULT NULL COMMENT 'ip',
	  `company_userid` varchar(256) DEFAULT NULL COMMENT '所属公司user_id',
	*/
	private String  accountid;      
	private String 	pass;     	
	
	private String 	ip_refresh_interval;  
	private String 	city;	//链接url
	private String 	ip;  	//创建者
	private String 	company_userid;	//创建时间
	
	
	private String  create_date;
	private String  update_date;
	
	
	private String 	company_name;	//创建时间
	
	
	
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
	public String getIp_refresh_interval() {
		return ip_refresh_interval;
	}
	public void setIp_refresh_interval(String ip_refresh_interval) {
		this.ip_refresh_interval = ip_refresh_interval;
	}
	
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getCompany_userid() {
		return company_userid;
	}
	public void setCompany_userid(String company_userid) {
		this.company_userid = company_userid;
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
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
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
	
	
	
	
}
