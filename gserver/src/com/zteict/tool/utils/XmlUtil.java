package com.zteict.tool.utils;

import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class XmlUtil {
	/*
	 * 
	 * @date 2016-8-16
	 * 
	 * @author zj
	 */

	/**
	 * 解析XML字符串，获取指定节点值
	 * 
	 * @param protocolXML
	 * @param nodeName
	 * @return
	 * @date 2016-8-7
	 */
	public static String parseXml(String protocolXML, String nodeName) {

		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(new InputSource(new StringReader(
					protocolXML)));

			Element root = doc.getDocumentElement();
			NodeList books = root.getChildNodes();
			if (books != null) {
				for (int i = 0; i < books.getLength(); i++) {
					Node book = books.item(i);
					if (book.getNodeName().equals(nodeName))
						return book.getFirstChild().getNodeValue();
				}
			}
			return "";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

}
