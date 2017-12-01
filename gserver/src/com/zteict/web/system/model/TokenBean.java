package com.zteict.web.system.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 存放登录成功之后的token对象
 * 2014-04-13
 * @author jinmingchun
 */
public class TokenBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * token值
	 */
	private String token;
	
	/**
	 * 用户Id
	 */
	private String userId;
	
	private Date createDate;// token创建时间
	
	private String info;// 语言
	
	public TokenBean(){
		
	}
	//mod by jinmingchun 2014-04-14 修改参数
	public TokenBean(String token, String userId, Date createDate, String info){
		this.token = token;
		this.userId = userId;
		this.createDate = createDate;
		this.info = info;
	}
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getUserId() {
		if(userId==null){
			userId = "";
		}
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}

	@Override
	public String toString() {
		return "TokenBean [token=" + token + ", userId=" + userId
				+ ", createDate=" + createDate + ", info=" + info + "]";
	}
}
