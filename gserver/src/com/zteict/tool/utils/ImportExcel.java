package com.zteict.tool.utils;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;

public class ImportExcel<T> {
	Class<T> clazz;

	public ImportExcel(Class<T> clazz) {
		this.clazz = clazz;
	}

	public Collection<T> importExcel(File file, String... pattern) {
		Collection<T> dist = new ArrayList();
		try {
			/**
			 * 类反射得到调用方法
			 */
			// 得到目标目标类的所有的字段列表
			Field filed[] = clazz.getDeclaredFields();
			// 将所有标有Annotation的字段，也就是允许导入数据的字段,放入到一个map中
			Map fieldmap = new HashMap();
			// 循环读取所有字段
			for (int i = 0; i < filed.length; i++) {
				Field f = filed[i];
				// 得到单个字段上的Annotation
				ExcelAnnotation exa = f.getAnnotation(ExcelAnnotation.class);
				// 如果标识了Annotationd的话
				if (exa != null) {
					// 构造设置了Annotation的字段的Setter方法
					String fieldname = f.getName();
					String setMethodName = "set"
							+ fieldname.substring(0, 1).toUpperCase()
							+ fieldname.substring(1);
					// 构造调用的method，
					Method setMethod = clazz.getMethod(setMethodName,
							new Class[] { f.getType() });
					// 将这个method以Annotaion的名字为key来存入。
					fieldmap.put(exa.exportName(), setMethod);
				}
			}
			/**
			 * excel的解析开始
			 */
			// 将传入的File构造为FileInputStream;
			FileInputStream in = new FileInputStream(file);
			// // 得到工作表
			Workbook book = WorkbookFactory.create(in);
			// // 得到第一页
			Sheet sheet = book.getSheetAt(0);
			// // 得到第一面的所有行
			Iterator<Row> row = sheet.rowIterator();

			/**
			 * 标题解析
			 */
			// 得到第一行，也就是标题行
			Row title = row.next();
			// 得到第一行的所有列
			Iterator<Cell> cellTitle = title.cellIterator();
			// 将标题的文字内容放入到一个map中。
			Map titlemap = new HashMap();
			// 从标题第一列开始
			int i = 0;
			// 循环标题所有的列
			while (cellTitle.hasNext()) {
				Cell cell = cellTitle.next();
				String value = cell.getStringCellValue();
				titlemap.put(i, value);
				i = i + 1;
			}
			/**
			 * 解析内容行
			 */
			//用来格式化日期的DateFormat
			SimpleDateFormat sf;
			if (pattern.length < 1) {
				sf = new SimpleDateFormat("yyyy-MM-dd");
			} else
				sf = new SimpleDateFormat(pattern[0]);
			// 循环行Row   
			for (int rowNum = 0; rowNum <= sheet.getLastRowNum(); rowNum++) {
				Row row1 = sheet.getRow(rowNum);
				// 得到传入类的实例
				T tObject = clazz.newInstance();
				if (row1 == null) {
					continue;
				}

				// 循环列Cell     
				for (int cellNum = 0; cellNum < row1.getLastCellNum(); cellNum++) {
					Cell cell = row1.getCell(cellNum);
					// 这里得到此列的对应的标题
					String titleString = (String) titlemap.get(cellNum);
					if (fieldmap.containsKey(titleString)) {
						Method setMethod = (Method) fieldmap.get(titleString);
						//得到setter方法的参数
						Type[] ts = setMethod.getGenericParameterTypes();
						//只要一个参数
						String xclass = ts[0].toString();

						if (cell == null) {
							setMethod.invoke(tObject, "");
							continue;
						}

						//判断参数类型	
						if (xclass.equals("class java.lang.String")) {
							if (Cell.CELL_TYPE_NUMERIC == cell.getCellType()) {
								//解决解析excel时如果是数字，只能取到第一位数字问题   update by fengsz at 2014/4/22
								DecimalFormat df = new DecimalFormat("0"); 
				//				String resultStr = String.valueOf(cell.getNumericCellValue());
								String resultStr = df.format(cell.getNumericCellValue()); 
				//				setMethod.invoke(tObject, resultStr.substring(0,resultStr.indexOf(".")));
								setMethod.invoke(tObject, resultStr);
							} else {
								setMethod.invoke(tObject, cell
										.getStringCellValue());
							}
						} else if (xclass.equals("class java.util.Date")) {
							setMethod.invoke(tObject, sf.parse(cell
									.getStringCellValue()));
						} else if (xclass.equals("class java.lang.Boolean")) {
							Boolean boolname = true;
							if (cell.getStringCellValue().equals("否")) {
								boolname = false;
							}
							setMethod.invoke(tObject, boolname);
						} else if (xclass.equals("class java.lang.Integer")) {
							setMethod.invoke(tObject, new Integer(cell
									.getStringCellValue()));
						}

						else if (xclass.equals("class java.lang.Long")) {
							setMethod.invoke(tObject, new Long(cell
									.getStringCellValue()));
						}
					}
				}

				dist.add(tObject);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return dist;
	}

	@SuppressWarnings("static-access")
	private String getValue(Cell cell) {
		if (cell.getCellType() == cell.CELL_TYPE_BOOLEAN) {
			return String.valueOf(cell.getBooleanCellValue());
		} else if (cell.getCellType() == cell.CELL_TYPE_NUMERIC) {
			return String.valueOf(cell.getNumericCellValue());
		} else {
			return String.valueOf(cell.getStringCellValue());
		}
	}

	
}
