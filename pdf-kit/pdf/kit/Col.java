package pdf.kit;

public class Col {

	private String name;
	private Integer width=40;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getWidth() {
		return width;
	}
	public void setWidth(Integer width) {
		this.width = width;
	}
	
	public  Col(String n,Integer w) {
		this.name=n;
		this.width=w;
	}
	public  Col(String n) {
		this.name=n;
	
	}
	
}
