package com.zteict.tool.utils;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.zteict.tool.config.ConfigReader;

/**
 * 
 * @author zj
 * @date 2015-12-23 上午9:53:07
 */
public class FileUtil {

	/**
	 * 获取方便识别的文件大小 byte-->1.2M
	 * 
	 * @param filebyteSize
	 *            文件字节大小
	 * @return 0.4byte 1.3k 2.3M 1.4G
	 * @author zj
	 * @date 2015-12-23 上午9:53:47 *
	 */
	public static String getReadableFileSize(String StrFilebyteSize) {
		if (StrFilebyteSize == null || StrFilebyteSize.equals(""))
			return "";

		Long filebyteSize = Long.parseLong(StrFilebyteSize);

		String rst = "";

		int SIZE = 1024;

		if (filebyteSize / SIZE < SIZE) // k
		{
			double tp = filebyteSize / ((double) SIZE);
			rst = String.format("%.2f", tp) + "K";
		} else if (filebyteSize / (SIZE * SIZE) < SIZE) // k
		{
			double tp = filebyteSize / ((double) (SIZE * SIZE));
			rst = String.format("%.2f", tp) + "M";
		} else if (filebyteSize / (SIZE * SIZE * SIZE) < SIZE) // k
		{
			double tp = filebyteSize / ((double) (SIZE * SIZE * SIZE));
			rst = String.format("%.2f", tp) + "G";
		}

		return rst;
	}

	public static void main(String[] args) {
		String k = "123123132";
		System.err.println(getReadableFileSize(k));
	}

	/**
	 * 根据数组生成xls对象
	 * 
	 * @param projectreportlist
	 * @param request
	 * @return
	 * @throws Exception
	 * @date 2016-10-13
	 * @author zj
	 */
	public static HSSFWorkbook createPauseDataDetailExcel(
			List<String[]> projectreportlist) throws Exception {
		// 创建Excel对象
		HSSFWorkbook wb = new HSSFWorkbook();
		try {
			// 创建Excel Sheet页
			HSSFSheet deptRoleSheet = wb.createSheet("Data");
			// 在Sheet页中设置表头
			// 在Sheet页中设置表内容

			int startrow = 1;
			// Sheet页中的行对象
			HSSFRow row = null;

			int width = 30 * 256;

			HSSFCellStyle style = wb.createCellStyle();
			deptRoleSheet.setColumnWidth(0, width); // 第一个参数代表列id(从0开始),第2个参数代表宽度值

			// row = deptRoleSheet.createRow(0);

			// int j = 0;

			// //表头
			// for (int j = 0; j < projectreportlist.get(0).length; j++) {
			// deptRoleSheet.setColumnWidth(j, width);
			// row.createCell(j).setCellValue(new
			// HSSFRichTextString(projectreportlist.get(0)[j]));
			// }

			if (projectreportlist.size() >= 1)
				// 填数据
				for (int i = 0; i < projectreportlist.size(); i++) {
					String[] rowdata = projectreportlist.get(i);

					// 新建一行
					row = deptRoleSheet.createRow(i);

					// 表头
					for (int j = 0; j < projectreportlist.get(i).length; j++) {
						deptRoleSheet.setColumnWidth(j, width);
						row.createCell(j).setCellValue(
								new HSSFRichTextString(
										projectreportlist.get(i)[j]));
					}

				}
		} catch (Exception e) {
			throw e;
		}
		return wb;
	}

	/** http下载 */
	public static boolean httpDownload(String httpUrl, String saveFile) {
		// 下载网络文件
		int bytesum = 0;
		int byteread = 0;

		URL url = null;
		URLConnection conn = null;
		try {
			url = new URL(httpUrl);

			String vProxy = ConfigReader.getInstance().getProperty("VProxy");
			if (vProxy.equals("true")) {

				String proxyIP = ConfigReader.getInstance().getProperty(
						"VProxyServer");
				int proPort = ConfigReader.getInstance().getIntProperty(
						"VProxyServer", 80);

				if (proxyIP != null && !proxyIP.equals("")) {

					Proxy proxy = new Proxy(Proxy.Type.HTTP,
							new InetSocketAddress(proxyIP, proPort));
					conn = url.openConnection(proxy);
				}
			}

			else
				conn = url.openConnection();

		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return false;
		}

		try {

			InputStream inStream = conn.getInputStream();
			FileOutputStream fs = new FileOutputStream(saveFile);

			byte[] buffer = new byte[1204];
			while ((byteread = inStream.read(buffer)) != -1) {
				bytesum += byteread;
				System.out.println(bytesum);
				fs.write(buffer, 0, byteread);
			}
			return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

}
