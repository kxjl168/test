package pdf.kit.component.chart;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.title.LegendTitle;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.jndi.url.corbaname.corbanameURLContextFactory;

import pdf.kit.component.chart.impl.DefaultLineChart;
import pdf.kit.util.FontUtil;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * Created by fgm on 2017/4/7.
 */
@Slf4j
public abstract class AbstractChart {

	private int width;

	private int height;
	
	protected double min=Double.MAX_VALUE;
	protected double max=Double.MIN_VALUE;
	
	protected List<ItemData> _itemList;

	protected CType type;

	private static int defaultWidth = 500;
	private static int defaultHeight = 220;

	private String fileName = "1.jpeg";
	
	public static String fontName="SIMLI.TTF";

	private static Logger log = LoggerFactory.getLogger(AbstractChart.class);

	private DefaultPieDataset getDataset() {
		DefaultPieDataset dpd = new DefaultPieDataset(); // 建立一个默认的饼图
		dpd.setValue("管理人员", 25); // 输入数据
		dpd.setValue("市场人员", 25);
		dpd.setValue("开发人员", 45);
		dpd.setValue("其他人员", 10);
		return dpd;
	}

	
	private DefaultPieDataset getPieDataset(List<ItemData> itemList) {
		DefaultPieDataset dpd = new DefaultPieDataset(); // 建立一个默认的饼图
		for (int i = 0; i < itemList.size(); i++) {
			dpd.setValue(itemList.get(i).getXvalue(), itemList.get(i).getYvalue()); // 输入数据
		}
		/*
		dpd.setValue("市场人员", 25);
		dpd.setValue("开发人员", 45);
		dpd.setValue("其他人员", 10);*/
		return dpd;
	}
	
	
	private String DrawPieImage(String title, int picId,List<ItemData> itemList) {
		JFreeChart chart = ChartFactory.createPieChart(title,
				getPieDataset(itemList), true, true, false);
		chart.setTitle(new TextTitle(title,getFont(Font.PLAIN, 13)));

		LegendTitle legend = chart.getLegend(0);// 设置Legend
		legend.setItemFont(getFont(Font.PLAIN, 13));// new Font("宋体", Font.BOLD,
													// 14));
		PiePlot plot = (PiePlot) chart.getPlot();// 设置Plot
		plot.setLabelFont(getFont(Font.BOLD, 16));

		String path = this.getClass().getClassLoader().getResource("")
				.getPath();
		String filePath = path + "/images/" + type.desc + "-" + picId + ".jpeg";
		File lineChart = new File(filePath);
		if (!lineChart.getParentFile().exists()) {
			lineChart.getParentFile().mkdirs();
		}
		
		initDefaultPlot(chart);

		try {
			OutputStream os = new FileOutputStream(filePath);// 图片是文件格式的，故要用到FileOutputStream用来输出。
			ChartUtilities.writeChartAsJPEG(os, chart, getWidth(), getHeight());
			// 使用一个面向application的工具类，将chart转换成JPEG格式的图片。第3个参数是宽度，第4个参数是高度。

			os.close();// 关闭输出流
		} catch (Exception e) {
			// TODO: handle exception
		}
		return lineChart.getAbsolutePath();
	}

	public static void main(String args[]) {

	}

	
	private void comMaxMin(List<ItemData> itemList){
		for (int i = 0; i < itemList.size(); i++) {
			if(itemList.get(i).getYvalue()>max)
				max=itemList.get(i).getYvalue();
			if(itemList.get(i).getYvalue()<min)
				min=itemList.get(i).getYvalue();
		}
	}
	
	public String draw(List<ItemData> itemList, int picId) {
		if (itemList == null || itemList.size() == 0) {
			return "";
		}
		_itemList=itemList;
		comMaxMin(itemList);
		return draw("", "", "", itemList, picId);
	}

	public String draw(String title, String xLabel, String yLabel,
			List<ItemData> itemList, int picId) {

		if (itemList == null || itemList.size() == 0) {
			return "";
		}
		_itemList=itemList;
		comMaxMin(itemList);
		
		if (type == CType.PIE)
			return DrawPieImage(title,picId,itemList);
		else {

			DefaultCategoryDataset dataSet = new DefaultCategoryDataset();
			for (ItemData line : itemList) {
				dataSet.addValue(line.getYvalue(), line.getGroupName(),
						line.getXvalue());
			}
			try {
				return drawCategoryChar(title, xLabel, yLabel, dataSet, picId,
						type);
			} catch (Exception ex) {
				log.error("画图异常{}", ex);
				return "";
			}
		}
	}

	/**
	 * @description 画出折线图
	 * @return 图片地址
	 */
	private String drawCategoryChar(String title, String xLabel, String yLabel,
			DefaultCategoryDataset dataSet, int picId, CType type)
			throws IOException {

		JFreeChart lineChartObject = null;
		if (type == CType.LINE)
			lineChartObject = ChartFactory.createLineChart(title, xLabel, // 横轴
					yLabel, // 纵轴
					dataSet, // 获得数据集
					PlotOrientation.VERTICAL,// 图标方向垂直
					true, // 显示图例
					false, // 不用生成工具
					false // 不用生成URL地址
					);
		else if (type == CType.BAR)
			lineChartObject = ChartFactory.createBarChart(title, xLabel, // 横轴
					yLabel, // 纵轴
					dataSet, // 获得数据集
					PlotOrientation.VERTICAL,// 图标方向垂直
					true, // 显示图例
					false, // 不用生成工具
					false // 不用生成URL地址
					);

		String path = this.getClass().getClassLoader().getResource("")
				.getPath();
		String filePath = path + "/images/" + type.desc + "-" + picId + ".jpeg";
		File lineChart = new File(filePath);
		if (!lineChart.getParentFile().exists()) {
			lineChart.getParentFile().mkdirs();
		}
		// 初始化表格样式
		initDefaultPlot(lineChartObject);

		/*
		 * //Legend lineChartObject.getLegend().setItemFont(new Font("宋体",
		 * Font.BOLD, 14));
		 * lineChartObject.getLegend().setItemFont(getFont(Font.PLAIN, 13));
		 * 
		 * 
		 * CategoryPlot plot = (CategoryPlot)
		 * lineChartObject.getCategoryPlot();// 设置Plot
		 * plot.setNoDataMessage("无对应的数据。");
		 * plot.setNoDataMessageFont(getFont(Font.PLAIN, 13));// 字体的大小
		 * plot.setNoDataMessagePaint(Color.RED);// 字体颜色
		 */

		ChartUtilities.saveChartAsJPEG(lineChart, lineChartObject, getWidth(),
				getHeight());

		return lineChart.getAbsolutePath();

	}

	private void initDefaultPlot(JFreeChart chart
			) {
		// 设置公共颜色
		chart.getTitle().setFont(getFont(Font.BOLD, 15)); // 设置标题字体
		chart.getLegend().setItemFont(getFont(Font.PLAIN, 13));// 设置图例类别字体
		chart.setBackgroundPaint(Color.white);// 设置背景色
	
		if(type==CType.BAR||type==CType.LINE){
		CategoryPlot plot = chart.getCategoryPlot();
		plot.setNoDataMessage("No Data。");
		plot.setNoDataMessageFont(getFont(Font.PLAIN, 13));// 字体的大小
		plot.setNoDataMessagePaint(Color.RED);// 字体颜色
		}
		// 设置自定义颜色
		initPlot(chart);

	}

	/**
	 * @description 设置自定义的线条和背景色
	 */
	protected abstract void initPlot(JFreeChart chart
			);

	protected void initDefaultXYPlot(CategoryPlot plot) {
		// 设置X轴
		CategoryAxis domainAxis = plot.getDomainAxis();
		domainAxis.setLabelFont(getFont(Font.PLAIN, 13)); // 设置横轴字体
		domainAxis.setTickLabelFont(getFont(Font.PLAIN, 13));// 设置坐标轴标尺值字体
		domainAxis.setLowerMargin(0.01);// 左边距 边框距离
		domainAxis.setUpperMargin(0.06);// 右边距 边框距离,防止最后边的一个数据靠近了坐标轴。
		domainAxis.setMaximumCategoryLabelLines(10);
		domainAxis.setCategoryLabelPositions(CategoryLabelPositions.DOWN_45);// 横轴
																				// lable
																				// 的位置
																				// 横轴上的
																				// Lable
																				// 45度倾斜
		   
        
				// DOWN_45
		// 设置Y轴
		NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
		rangeAxis.setLabelFont(getFont(Font.PLAIN, 13));
		rangeAxis.setAutoRangeMinimumSize(1); // 最小跨度
		rangeAxis.setUpperMargin(5);// 上边距,防止最大的一个数据靠近了坐标轴/。
		//rangeAxis.setLowerMargin(min); // 最小值显示18
		rangeAxis.setUpperMargin(0.1); // 最大值显示33
		rangeAxis.setAutoRange(false); // 不自动分配Y轴数据
		rangeAxis.setTickMarkStroke(new BasicStroke(1.6f)); // 设置坐标标记大小
		rangeAxis.setTickMarkPaint(Color.BLACK); // 设置坐标标记颜色
		rangeAxis.setTickUnit(new NumberTickUnit(3));// 每1个刻度显示一个刻度值
		        
		      

	}

	/**
	 * @description 加载自定义字体
	 */
	public static Font loadFont(String fontFileName, int style, float fontSize)
			throws IOException {
		FileInputStream fis = null;
		try {

			File file = new File(fontFileName);
			fis = new FileInputStream(file);
			Font dynamicFont = Font.createFont(style, fis);
			Font dynamicFontPt = dynamicFont.deriveFont(fontSize);

			return dynamicFontPt;
		} catch (Exception e) {
			return new Font("LeelawUI", Font.PLAIN, 14);
		} finally {
			fis.close();
		}
	}

	public static Font getFont(int style, float fontSize) {
		try {
//			String fontPath = FontUtil.getFontPath("SIMLI.TTF");
			String fontPath = FontUtil.getFontPath(fontName);//"LeelawUI.ttf");
			
			Font font = loadFont(fontPath, style, fontSize);
			return font;
		} catch (IOException e) {

			log.error("字体加载异常{}", ExceptionUtils.getFullStackTrace(e));
		}
		return null;
	}

	public int getWidth() {
		if (width == 0) {
			return defaultWidth;
		}
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		if (height == 0) {
			return defaultHeight;
		}
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public CType getType() {
		return type;
	}

	public void setType(CType type) {
		this.type = type;
	}


	public double getMin() {
		return min;
	}


	public void setMin(double min) {
		this.min = min;
	}


	public double getMax() {
		return max;
	}


	public void setMax(double max) {
		this.max = max;
	}



}
