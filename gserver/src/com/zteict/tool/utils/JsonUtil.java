package com.zteict.tool.utils;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

public class JsonUtil {

	/**
	 * 输出json数据
	 * @param response
	 * @param responseObject
	 * @date 2016-2-26
	 * @author zj
	 */
	public static void responseOutWithJson(HttpServletResponse response,
			String responseObject) {

		// responseObject="2";
		// String rst="{\"msg\":\""+responseObject+"\"}";

		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.write(responseObject);
			out.flush();

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}

	
	/**
	 * request 输出json
	 * @param response
	 * @param Map Map
	 * @author zj
	 * @date 2015-12-25 上午10:20:58 *
	 */
	public static void responseOutWithJson(HttpServletResponse response,
			Map<String, Object> Map) {

		// responseObject="2";
		// String rst="{\"msg\":\""+responseObject+"\"}";

		String rst = "{";

		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = null;

		Iterator<String> key = Map.keySet().iterator();

		while (key.hasNext()) {
			String keyval=key.next();
			rst += "\"" + keyval + "\":" + "\"" + Map.get(keyval) + "\",";
		}

		if (rst.endsWith(","))
			rst = rst.substring(0, rst.length() - 1);

		rst += "}";

		try {
			out = response.getWriter();
			out.write(rst);
			out.flush();

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}
}
