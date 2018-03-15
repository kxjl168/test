package pdf.kit;

import java.util.List;

public class Row {

	private Integer height=50;
	
	private List<Col> items;

	public List<Col> getItems() {
		return items;
	}

	public void setItems(List<Col> items) {
		this.items = items;
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	
}
