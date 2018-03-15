package pdf.kit.component.chart;

import lombok.Data;

/**
 * Created by fgm on 2017/4/7.
 */
@Data
public class ItemData {
    private Double yvalue;
    private String  xvalue;
    private String groupName;
    public ItemData(){

    }
    public ItemData(Double yValue, String xValue, String groupName){
        this.yvalue=yValue;
        this.xvalue=xValue;
        this.groupName=groupName;
    }
	
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public double getYvalue() {
		return yvalue;
	}
	public void setYvalue(Double yvalue) {
		this.yvalue = yvalue;
	}
	public String getXvalue() {
		return xvalue;
	}
	public void setXvalue(String xvalue) {
		this.xvalue = xvalue;
	}



}
