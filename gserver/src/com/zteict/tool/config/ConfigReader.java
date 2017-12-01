package com.zteict.tool.config;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.zteict.tool.utils.StringUtil;

public class ConfigReader {
	private static Logger logger = Logger.getLogger(ConfigReader.class);
	// 单例模式-懒汉式(真正使用的时候实例化<延迟加载>)

	private static ConfigReader instance = null;

	private Properties innerDatas = new Properties();

	private ConfigReader() {

		reload();
	}

	public static synchronized ConfigReader getInstance() {
		// 判断是否实例化
		if (instance == null) {
			instance = new ConfigReader();
		}
		return instance;
	}

	public static String getConfigPath() {
		String rst = "";
		String configPath = "";

		if (configPath == null || configPath.equals(""))
			configPath = SysProperties.getInstance().getProperty(
					"CONFIG_FILE_PATH");

		if (configPath == null || configPath.equals("")) {
			System.err
					.println("Web.xml中名称为CONFIG_FILE_PATH的context-param缺失!! ");
		}

		// 得到一个DOM并返回给document对象
		String classPath = Thread.currentThread().getContextClassLoader()
				.getResource("/").getPath();

		rst = configPath;// classPath+".."+configPath;
		logger.info("===========loading sys.xml:" + rst);

		return rst;
	}

	public void reload() {

		innerDatas.clear();

		String sysFilePath = getConfigPath();

		// documentBuilder为抽象不能直接实例化(将XML文件转换为DOM文件)
		DocumentBuilder db = null;
		DocumentBuilderFactory dbf = null;
		try {
			// 返回documentBuilderFactory对象
			dbf = DocumentBuilderFactory.newInstance();
			// 返回db对象用documentBuilderFatory对象获得返回documentBuildr对象
			db = dbf.newDocumentBuilder();

			Document dt = db.parse(new File(sysFilePath) );
			Element element = null;
			// 得到一个elment根元素
			element = dt.getDocumentElement();
			// 获得根节点
			System.out.println("根元素：" + element.getNodeName());
			if (element.getNodeName().equals("Config")) {
				NodeList configItem = element.getChildNodes();
				for (int i = 0; i < configItem.getLength(); i++) {

					String key = "";
					String value = "";
					Node extNode = configItem.item(i);
					NodeList exts = extNode.getChildNodes();
					for (int j = 0; j < exts.getLength(); j++) {
						Node e = exts.item(j);
						String ename = e.getNodeName();
						if ("ConfigName".equals(ename)) {
							// System.out.println(e.getChildNodes().item(0).getNodeValue().toString());
							key = e.getChildNodes().item(0).getNodeValue()
									.toString();
						}
						if ("ConfigValue".equals(ename)) {
							// System.out.println(e.getChildNodes().item(0).getNodeValue());
							value = e.getChildNodes().item(0).getNodeValue()
									.toString();
						}

						innerDatas.put(key, value);

					}

				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

	// 解析xml
	public String getProperty(String name) {
		String value = "";
		try {
			value = this.innerDatas.getProperty(name);
		} catch (Exception e) {

		}
		return StringUtil.getString(value);
	}

	/**
	 * 读取数字型的配置项
	 * @param name
	 * @param defaultValue
	 * @return
	 * @date 2016-6-24
	 * @author zj
	 */
	public Integer getPropertyInt(String name, int defaultValue) {
		int rst = defaultValue;
		String value = "";
		try {
			value = this.innerDatas.getProperty(name);
		} catch (Exception e) {

		}
		try {
			rst = Integer.parseInt(value);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return rst;
	}
	
	/**
	 * 读取数字型的配置项
	 * @param name
	 * @param defaultValue
	 * @return
	 * @date 2016-6-24
	 * @author zj
	 */
	public Double getPropertyDouble(String name, double defaultValue) {
		Double rst = defaultValue;
		String value = "";
		try {
			value = this.innerDatas.getProperty(name);
		} catch (Exception e) {

		}
		try {
			rst = Double.parseDouble(value);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return rst;
	}

	/**
	 * 获取int 值
	 * 
	 * @param name
	 * @param defaultval
	 *            解析异常的默认值
	 * @return
	 * @date 2016-5-24
	 * @author zj
	 */
	public int getIntProperty(String name, int defaultval) {
		int rst = defaultval;
		String value = "";
		try {
			value = this.innerDatas.getProperty(name);
		} catch (Exception e) {

		}

		value = StringUtil.getString(value);
		try {
			rst = Integer.parseInt(value);
		} catch (Exception e) {
			rst = defaultval;
		}

		return rst;
	}
}
