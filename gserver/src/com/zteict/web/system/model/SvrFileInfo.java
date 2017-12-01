package com.zteict.web.system.model;

/**
 * 文件服务器本地存储记录表
 * 
 * @date 2016-7-12
 * @author zj create table file_svr_md5 ( id int, old_name varchar(100) comment
 *         '文件原始名称', save_name varchar(150) comment '文件存储名称', full_path
 *         varchar(400) comment '文件本地存储全路径', http_relative_path varchar(500)
 *         comment '文件访问相对路径 (不带http://xxxx/)', http_down_url int comment
 *         '文件下载路径.action-计算下载次数(不带http://xxxx/)', file_size int, save_date
 *         varchar(50), file_md5 varchar(100), down_nums int );
 */
public class SvrFileInfo {

	private Integer id;//
	private String old_name;// varchar(100) comment '文件原始名称',
	private String save_name;// varchar(150) comment '文件存储名称',
	private String full_path;// varchar(400) comment '文件本地存储全路径',
	private String http_relative_path;// varchar(500) comment '文件访问相对路径
										// (不带http://xxxx/)',
	private String http_down_url;// int comment
									// '文件下载路径.action-计算下载次数(不带http://xxxx/)',
	private Long file_size;// int,
	private String save_date;// varchar(50),
	private String file_md5;// varchar(100),
	private Integer down_nums;// int

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getOld_name() {
		return old_name;
	}

	public void setOld_name(String old_name) {
		this.old_name = old_name;
	}

	public String getSave_name() {
		return save_name;
	}

	public void setSave_name(String save_name) {
		this.save_name = save_name;
	}

	public String getFull_path() {
		return full_path;
	}

	public void setFull_path(String full_path) {
		this.full_path = full_path;
	}

	public String getHttp_relative_path() {
		return http_relative_path;
	}

	public void setHttp_relative_path(String http_relative_path) {
		this.http_relative_path = http_relative_path;
	}

	public String getHttp_down_url() {
		return http_down_url;
	}

	public void setHttp_down_url(String http_down_url) {
		this.http_down_url = http_down_url;
	}

	public Long getFile_size() {
		return file_size;
	}

	public void setFile_size(Long file_size) {
		this.file_size = file_size;
	}

	public String getSave_date() {
		return save_date;
	}

	public void setSave_date(String save_date) {
		this.save_date = save_date;
	}

	public String getFile_md5() {
		return file_md5;
	}

	public void setFile_md5(String file_md5) {
		this.file_md5 = file_md5;
	}

	public Integer getDown_nums() {
		return down_nums;
	}

	public void setDown_nums(Integer down_nums) {
		this.down_nums = down_nums;
	}
}
