package pdf.kit.component.chart;

import java.util.List;

import com.google.common.collect.Lists;

import pdf.kit.component.chart.impl.DefaultBarChart;
import pdf.kit.component.chart.impl.DefaultLineChart;
import pdf.kit.component.chart.impl.DefaultPieChart;

public class Test {
	
	
	public static void main(String[] args) {
		List<ItemData> itemList = getTemperatureLineList();
		DefaultLineChart lineChart = new DefaultLineChart();
		lineChart.setHeight(500);
		lineChart.setWidth(300);
		String picUrl = lineChart.draw(itemList, 0);

		DefaultPieChart pieChart = new DefaultPieChart();
		pieChart.draw(itemList, 0);

		DefaultBarChart barChart = new DefaultBarChart();
		barChart.draw(itemList, 0);

	}

	public static List<ItemData> getTemperatureLineList() {
		List<ItemData> list = Lists.newArrayList();
		for (int i = 1; i <= 7; i++) {
			ItemData line = new ItemData();
			double random = Math.round(Math.random() * 10);
			line.setXvalue("星期" + i);
			/*
			 * if (i == 4) line.setYvalue(0.0); else
			 */
			line.setYvalue(20 + random);
			line.setGroupName("下周");
			list.add(line);
		}
		for (int i = 1; i <= 7; i++) {
			ItemData line = new ItemData();
			double random = Math.round(Math.random() * 10);
			line.setXvalue("星期" + i);
			line.setYvalue(20 + random);
			line.setGroupName("这周");
			list.add(line);
		}
		return list;
	}
}