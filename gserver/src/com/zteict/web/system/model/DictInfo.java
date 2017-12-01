package com.zteict.web.system.model;

import com.zteict.web.system.model.base.BaseModel;

/**
 * 系统字典表
 * @date 2016-7-25
 * @author zj
 *
 */
public class DictInfo extends BaseModel {
	private Integer id ;// int(11) NOT NULL,
	private String dic_type ;//` varchar(50) DEFAULT NULL COMMENT '字典类型',
	private String dic_key ;// varchar(50) DEFAULT NULL COMMENT '字典key',
	private String dic_name  ;// varchar(100) DEFAULT NULL COMMENT '字典value',
	private String sort  ;// int(11) DEFAULT NULL COMMENT '字典排序',
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getDic_type() {
		return dic_type;
	}
	public void setDic_type(String dic_type) {
		this.dic_type = dic_type;
	}
	public String getDic_key() {
		return dic_key;
	}
	public void setDic_key(String dic_key) {
		this.dic_key = dic_key;
	}

	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public String getDic_name() {
		return dic_name;
	}
	public void setDic_name(String dic_name) {
		this.dic_name = dic_name;
	}
	
}
