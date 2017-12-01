package com.zteict.web.system.model;

import java.util.Date;

public class SysParameter {

	private int param_id;
	
	private String param_name;
	
	private String param_value; 
	
	private String param_desc;
	
	private String enable_flag;
	
	private int create_by; //创建者
	
	private Date create_date;
	
	private int last_update_by;
	
	private Date last_update_date;
	
	private String memo;
	
	private String annotation;

	public int getParam_id() {
		return param_id;
	}

	public void setParam_id(int param_id) {
		this.param_id = param_id;
	}

	public String getParam_name() {
		return param_name;
	}

	public void setParam_name(String param_name) {
		this.param_name = param_name;
	}

	public String getParam_value() {
		return param_value;
	}

	public void setParam_value(String param_value) {
		this.param_value = param_value;
	}

	public String getParam_desc() {
		return param_desc;
	}

	public void setParam_desc(String param_desc) {
		this.param_desc = param_desc;
	}

	public String getEnable_flag() {
		return enable_flag;
	}

	public void setEnable_flag(String enable_flag) {
		this.enable_flag = enable_flag;
	}

	public int getCreate_by() {
		return create_by;
	}

	public void setCreate_by(int create_by) {
		this.create_by = create_by;
	}

	public Date getCreate_date() {
		return create_date;
	}

	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}

	public int getLast_update_by() {
		return last_update_by;
	}

	public void setLast_update_by(int last_update_by) {
		this.last_update_by = last_update_by;
	}

	public Date getLast_update_date() {
		return last_update_date;
	}

	public void setLast_update_date(Date last_update_date) {
		this.last_update_date = last_update_date;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getAnnotation() {
		return annotation;
	}

	public void setAnnotation(String annotation) {
		this.annotation = annotation;
	}
	
	
}
