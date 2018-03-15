package pdf.kit;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;


import pdf.kit.Col;
import pdf.kit.Row;
import pdf.kit.Tb;
import pdf.kit.TemplateBO;
import pdf.kit.component.PDFKit;
import pdf.kit.component.chart.AbstractChart;
import pdf.kit.component.chart.CType;
import pdf.kit.component.chart.ItemData;
import pdf.kit.component.chart.impl.DefaultBarChart;
import pdf.kit.component.chart.impl.DefaultLineChart;
import pdf.kit.component.chart.impl.DefaultPieChart;
import pdf.kit.util.FreeMarkerUtil;
import sun.misc.BASE64Encoder;

public class PdfHelper {

	// --------------------------//

	private static void addTableData(List<ReportItem> totaldatas,
			TemplateBO templateBO) {
		List<Tb> tbs = new ArrayList<Tb>();

		Tb tb1 = new Tb();

		Row head = new Row();
		List<Col> lsthead = new ArrayList<Col>();
		lsthead.add(new Col("Seq", 50));
		lsthead.add(new Col("Item", 400));
		lsthead.add(new Col("Amount", 200));
		/*
		 * for (int i = 0; i < totaldatas.size(); i++) { lsthead.add(new
		 * Col(totaldatas.get(i).getName(), 200)); }
		 */

		head.setItems(lsthead);

		// head.setHeight(30);

		List<Row> rows = new ArrayList<Row>();

		for (int i = 0; i < totaldatas.size(); i++) {

			Row r1 = new Row();
			List<Col> rd1 = new ArrayList<Col>();
			rd1.add(new Col(String.valueOf(i + 1)));
			rd1.add(new Col(totaldatas.get(i).getName()));
			rd1.add(new Col(String.valueOf(totaldatas.get(i).getNum())));

			r1.setItems(rd1);

			rows.add(r1);

		}

		tb1.setHead(head);
		tb1.setRows(rows);
	
		tbs.add(tb1);

		templateBO.setTb(tb1);
	}

	/**
	 * 详情表格
	 * 
	 * @param totaldatas
	 * @param templateBO
	 */
	private static void addDetailTableData(ReportDetailData totaldatas,
			TemplateBO templateBO) {
		List<Tb> tbs = new ArrayList<Tb>();

		Tb tb1 = new Tb();

		Row head = new Row();
		List<Col> lsthead = new ArrayList<Col>();

		for (int i = 0; i < totaldatas.getColname().size(); i++) {
			lsthead.add(totaldatas.getColname().get(i));
		}

		head.setItems(lsthead);

		// head.setHeight(30);

		List<Row> rows = new ArrayList<Row>();

		for (int i = 0; i < totaldatas.getLstdata().size(); i++) {

			Row r1 = new Row();
			List<Col> rd1 = new ArrayList<Col>();
			Object data = totaldatas.getLstdata().get(i);
			rd1.add(new Col(String.valueOf(i + 1)));
			for (int j = 0; j < totaldatas.getColattr().size(); j++) {

				String fieldName = totaldatas.getColattr().get(j);
				Class<? extends Object> t = data.getClass();
				String v = "";
				Field fd=null;
				try {
					 fd = t.getDeclaredField(fieldName);
					fd.setAccessible(true);

					/*
					 * Method m =t.getMethod("get" + fieldName); String value =
					 * (String) m.invoke(data); // 调用getter方法获取属性值
					 */
					if (fd != null)
						v = String.valueOf(fd.get(data));

				} catch (Exception e) {

					try {
						
					
					fd = t.getSuperclass().getDeclaredField(fieldName);
					fd.setAccessible(true);
					v = String.valueOf(fd.get(data));
					} catch (Exception e2) {
						// TODO: handle exception
					}
				}

				if(v.equals("null"))
					v="";
				
				rd1.add(new Col(v));
			}

			r1.setItems(rd1);

			rows.add(r1);

		}
		tb1.setTitle(totaldatas.getTitle());
		tb1.setHead(head);
		tb1.setRows(rows);
		tbs.add(tb1);

		templateBO.getTbdetails().add(tb1);
	}

	private static void AddPic(String chartType, String lang,
			List<ReportItem> totaldatas, TemplateBO templateBO, int width,
			int height, boolean isForWord) {

		List<ItemData> items = new ArrayList<ItemData>();

		for (int j = 0; j < totaldatas.size(); j++) {
			ItemData line = new ItemData();
			line.setXvalue(totaldatas.get(j).getName());

			line.setYvalue((double) totaldatas.get(j).getNum());
			line.setGroupName("Total Report");
			items.add(line);
		}

		AbstractChart lineChart = new DefaultBarChart();
		if (chartType.equalsIgnoreCase(CType.BAR.desc))
			lineChart = new DefaultBarChart();
		else if (chartType.equalsIgnoreCase(CType.PIE.desc))
			lineChart = new DefaultPieChart();

		if (width == 0)
			width = 500;
		if (height == 0)
			height = 300;

		lineChart.setHeight(height);
		lineChart.setWidth(width);

		// if (lang.equals("la_LA"))
		DefaultLineChart.fontName = "LeelawUI.ttf";// 老挝字体
		// else
		// DefaultLineChart.fontName = "SIMLI.TTF";// 中英文

		String picUrl = lineChart.draw(items, 0);
		templateBO.setPicUrl(picUrl);
		// pdf直接使用地址

		// word使用base64存储
		if (isForWord) {
			/** 图片路径 **/
			String imagePath = picUrl;// "D:\\apple.jpg";
			/** 将图片转化为 **/
			InputStream in = null;
			byte[] data = null;
			try {
				in = new FileInputStream(imagePath);
				data = new byte[in.available()];
				in.read(data);
				in.close();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (in != null) {
						in.close();
					}
				} catch (Exception e2) {
					// TODO: handle exception
				}

			}
			/** 进行base64位编码 **/
			BASE64Encoder encoder = new BASE64Encoder();
			picUrl = encoder.encode(data);
			templateBO.setPicUrl(picUrl);
		}
	}

	public static List<ItemData> getTemperatureLALineList() {
		List<ItemData> list = Lists.newArrayList();
		for (int i = 1; i <= 7; i++) {
			ItemData line = new ItemData();
			double random = Math.round(Math.random() * 10);
			line.setXvalue("ດເເຳ" + i);
			/*
			 * if (i == 4) line.setYvalue(0.0); else
			 */
			line.setYvalue(20 + random);
			line.setGroupName("ຢດຫກດ");
			list.add(line);
		}
		for (int i = 1; i <= 7; i++) {
			ItemData line = new ItemData();
			double random = Math.round(Math.random() * 10);
			line.setXvalue("ດເເຳ" + i);
			line.setYvalue(20 + random);
			line.setGroupName("ຫກດຫກດ");
			list.add(line);
		}
		return list;
	}

	public static String saveToFile(String title,String chartType,
			List<ReportItem> totaldatas, String lang, String fileType,
			String fileName) {

		TemplateBO templateBO = new TemplateBO();
		templateBO.setTemplateName(title);

		String path2 = FreeMarkerUtil.class.getClassLoader().getResource("")
				.getPath();

		File f = new File(FreeMarkerUtil.class.getClassLoader()
				.getResource("/").getPath());
		File pf = new File(f.getParent());
		String saveFilePath = pf.getParent() + "/upload/";
		// System.out.println();

		String freeMarkerUrl = path2 + "/templates/lo.png";
		//templateBO.setFreeMarkerUrl(freeMarkerUrl.substring(1));
		templateBO.setFreeMarkerUrl(freeMarkerUrl);

		addTableData(totaldatas, templateBO);

		boolean isforWord = false;
		if (fileType.equalsIgnoreCase("doc"))
			isforWord = true;

		if (!chartType.equalsIgnoreCase("null"))
			AddPic(chartType, lang, totaldatas, templateBO, 600, 300, isforWord);

		String path = "";
		PDFKit kit = new PDFKit();
		String tpname = "total_doc_la_LA.ftl";
		if (isforWord) {
			// 一个模板搞定
			tpname = "total_doc_la_LA.ftl";

			path = kit.exportToDoc(templateBO, saveFilePath, tpname, fileName);
		} else {

			tpname = "total.ftl";

			path = kit.exportToFile(templateBO, saveFilePath, tpname, fileName);

		}

		return path;

	}

	public static String saveToDetailFile(String title,List<ReportDetailData> datas,
			String lang, String fileType, String fileName) {

		TemplateBO templateBO = new TemplateBO();
		templateBO.setTemplateName(title);
		

		String path2 = FreeMarkerUtil.class.getClassLoader().getResource("")
				.getPath();

		File f = new File(FreeMarkerUtil.class.getClassLoader()
				.getResource("").getPath());
		File pf = new File(f.getParent());
		String saveFilePath = pf.getParent() + "/upload/";
		// System.out.println();

		String freeMarkerUrl = path2 + "/templates/lo.png";
		//templateBO.setFreeMarkerUrl(freeMarkerUrl.substring(1));
		templateBO.setFreeMarkerUrl(freeMarkerUrl);

		for (int i = 0; i < datas.size(); i++) {
			addDetailTableData(datas.get(i), templateBO);
		}
		// addDetailTableData(reports, templateBO);
		// addDetailTableData(news, templateBO);
		// addDetailTableData(messages, templateBO);

		boolean isforWord = false;
		if (fileType.equalsIgnoreCase("doc"))
			isforWord = true;

		String path = "";
		PDFKit kit = new PDFKit();
		String tpname = "detail_doc_la_LA.ftl";
		if (isforWord) {
			// 一个模板搞定
			tpname = "detail_doc_la_LA.ftl";

			path = kit.exportToDoc(templateBO, saveFilePath, tpname, fileName);
		} else {

			tpname = "detail.ftl";

			path = kit.exportToFile(templateBO, saveFilePath, tpname, fileName);

		}

		return path;

	}

}
