package pdf.kit;

import java.util.List;

public class Tb {

	private String title;
	private Row head;
	private List<Row> rows;
	public Row getHead() {
		return head;
	}
	public void setHead(Row head) {
		this.head = head;
	}
	public List<Row> getRows() {
		return rows;
	}
	public void setRows(List<Row> rows) {
		this.rows = rows;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
}
