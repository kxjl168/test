package pdf.kit.test;

import java.io.Serializable;

public class TestObject implements Serializable {

	private String col1;
	private String col2;
	private String col3;
	private String col4;
	
	public TestObject(String c1,String c2,String c3,String c4)
	{
		col1=c1;
		col2=c2;
		col3=c3;
		col4=c4;
	}
	
	public String getCol1() {
		return col1;
	}
	public void setCol1(String col1) {
		this.col1 = col1;
	}
	public String getCol2() {
		return col2;
	}
	public void setCol2(String col2) {
		this.col2 = col2;
	}
	public String getCol3() {
		return col3;
	}
	public void setCol3(String col3) {
		this.col3 = col3;
	}
	public String getCol4() {
		return col4;
	}
	public void setCol4(String col4) {
		this.col4 = col4;
	}
}
