package pdf.kit.component.chart;

public enum CType {

	LINE("0", "line"), BAR("1", "bar"), PIE("2", "pie");

	public String value = "";
	public String desc = "";

	private CType(String val, String desc) {
		this.value = val;
		this.desc = desc;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return value.toString();
	}

	public static CType parse(String val) {
		for (CType item : CType.values()) {
			if (item.value.equals(val))
				return item;
		}
		return CType.LINE;
	}

}