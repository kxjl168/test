package com.zteict.web.system.model.base;

/**
 * ibatis 分页查询参数
 * @date 2016-3-7
 * @author zj
 *
 */
public class BaseModel {
	/*
	 * 
	 * @date 2016-3-7
	 * 
	 * @author zj
	 */
	protected int pageCount=10;// 每页显示的记录数
	protected int page=1;// 第几页
	
	protected int start=0;// limit 查询开始数
	
	protected int totleRecord=0;// 总记录数



	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
		start= (page-1)*pageCount;
	}

	public int getTotleRecord() {
		return totleRecord;
	}

	public void setTotleRecord(int totleRecord) {
		this.totleRecord = totleRecord;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
		
		start= (page-1)*pageCount;
	}

}
