package com.zteict.tool.httpPost;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.InetAddress;
import java.net.Socket;

import org.apache.log4j.Logger;

import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;


/*
 SOCKET调用
 * @date 2016-3-7
 * @author zj
 */
public class SocketHelper {

	private static Logger logger = Logger.getLogger(SocketHelper.class);

	public static void main(String[] args) {

		System.out.println("11");
		//
		SocketPack packdata = new SocketPack();
		packdata.setType(1);
		packdata.setVmid(1);
		packdata.setIp("58.67.201.7");
		packdata.setTemplate_id(2);
		byte[] dataSend = packdata.getNewLXCByteData();

		for (int i = 0; i < dataSend.length; i++) {
			System.out.print(dataSend[i]+",");
		}

		 String socketServer = "10.67.12.6";
		 int socketPort = 10000;

		// 接口调用创建 lxc虚拟机
		SocketPack rst = SendMSG(socketServer, socketPort, dataSend);

	
	}

	public static SocketPack SendMSG(String ip, int port, byte[] data) {
		SocketPack packReturn = new SocketPack();
		try {

			InetAddress adds = InetAddress.getByName(ip);
			Socket server = new Socket(adds, port);
			InputStream in;

			OutputStream out = server.getOutputStream();
			DataOutputStream dos = new DataOutputStream(out);

			dos.write(data, 0, data.length);

			out.flush();

			in = server.getInputStream();

			byte[] charBuf = new byte[4096];
			int size = 0;
			size = in.read(charBuf, 0, 4096);

			packReturn.getReturnData(charBuf);

			server.close();

			System.out
					.println("return：type:"+packReturn.getType() + "/msg:" + packReturn.getMsg());

		} catch (Exception e) {
			logger.error("********socket error**********" + e.getMessage());
			packReturn.setMsg(e.getMessage());
		}
		return packReturn;
	}

}
