package pdf.kit;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fgm on 2017/4/17.
 */
@Data
public class TemplateBO {

    private String templateName;

    private String freeMarkerUrl;

    private String ITEXTUrl;

    private String JFreeChartUrl;

    private Row hd;
    
    private Tb tb;
    
    private List<Tb> tbdetails=new ArrayList<Tb>();
    
    private List<Col> items;

	public List<Col> getItems() {
		return items;
	}

	public void setItems(List<Col> items) {
		this.items = items;
	}
    private List<String> rows;

    private String imageUrl;

    private String picUrl;

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public String getFreeMarkerUrl() {
		return freeMarkerUrl;
	}

	public void setFreeMarkerUrl(String freeMarkerUrl) {
		this.freeMarkerUrl = freeMarkerUrl;
	}

	public String getITEXTUrl() {
		return ITEXTUrl;
	}

	public void setITEXTUrl(String iTEXTUrl) {
		ITEXTUrl = iTEXTUrl;
	}

	public String getJFreeChartUrl() {
		return JFreeChartUrl;
	}

	public void setJFreeChartUrl(String jFreeChartUrl) {
		JFreeChartUrl = jFreeChartUrl;
	}


	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}



	public List<String> getRows() {
		return rows;
	}

	public void setRows(List<String> rows) {
		this.rows = rows;
	}

	public Row getHd() {
		return hd;
	}

	public void setHd(Row hd) {
		this.hd = hd;
	}

	public Tb getTb() {
		return tb;
	}

	public void setTb(Tb tb) {
		this.tb = tb;
	}

	public List<Tb> getTbdetails() {
		return tbdetails;
	}

	public void setTbdetails(List<Tb> tbdetails) {
		this.tbdetails = tbdetails;
	}


}
