package pdf.kit;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import pdf.kit.Col;

public class ReportDetailData {

	private String title;
	private List<Serializable> lstdata=new ArrayList<Serializable>();
	private List<String> colattr;
	private List<Col> colname;

	public List<Serializable> getLstdata() {
		return lstdata;
	}

	public void setLstdata(List<Serializable> lstdata) {
		this.lstdata = lstdata;
	}

	public List<String> getColattr() {
		return colattr;
	}

	public void setColattr(List<String> colattr) {
		this.colattr = colattr;
	}

	public List<Col> getColname() {
		return colname;
	}

	public void setColname(List<Col> colname) {
		this.colname = colname;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
