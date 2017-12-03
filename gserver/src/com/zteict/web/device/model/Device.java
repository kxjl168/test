package com.zteict.web.device.model;

import com.zteict.web.system.model.base.BaseModel;


public class Device  extends BaseModel{
	private Integer	recordid;    		//id

	private String  routeid;      
	private String 	ip;     	
	
	private String 	city;  
	private String 	link_url;	//链接url
	private String 	onlinetime;  	//创建者
	private String 	offlinetime;	//创建时间
	
	private String 	showall;	//为true 查询全部；否则 过滤offline为空的
	
	
	public Integer getRecordid() {
		return recordid;
	}
	public void setRecordid(Integer recordid) {
		this.recordid = recordid;
	}
	
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getLink_url() {
		return link_url;
	}
	public void setLink_url(String link_url) {
		this.link_url = link_url;
	}
	public String getOnlinetime() {
		return onlinetime;
	}
	public void setOnlinetime(String onlinetime) {
		this.onlinetime = onlinetime;
	}
	public String getOfflinetime() {
		return offlinetime;
	}
	public void setOfflinetime(String offlinetime) {
		this.offlinetime = offlinetime;
	}
	public String getRouteid() {
		return routeid;
	}
	public void setRouteid(String routeid) {
		this.routeid = routeid;
	}
	public String getShowall() {
		return showall;
	}
	public void setShowall(String showall) {
		this.showall = showall;
	}
	
	
	
}
