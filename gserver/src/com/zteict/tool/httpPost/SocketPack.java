package com.zteict.tool.httpPost;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

/*
 * socket报文
 * @date 2016-3-7
 * 
 * @author zj
 */
public class SocketPack {

	private int type=-1;
	private int bodyLen=0;
	private int vmid;
	private String ip;
	private int template_id;
	private int port;

	private String msg="";

	private byte[] writeInt(int hexCrc32value) {
		byte[] result = new byte[4];
		result[0] = (byte) ((hexCrc32value >> 24) & 0xFF);//
		result[1] = (byte) ((hexCrc32value >> 16) & 0xFF);//
		result[2] = (byte) ((hexCrc32value >> 8) & 0xFF); //
		result[3] = (byte) (hexCrc32value & 0xFF);//
		return result;
	}

	private byte[] write2ByteInt(int hexCrc32value) {
		byte[] result = new byte[2];
		// result[0] = (byte)((hexCrc32value >> 24) & 0xFF);//
		// result[1] = (byte)((hexCrc32value >> 16) & 0xFF);//
		result[0] = (byte) ((hexCrc32value >> 8) & 0xFF); //
		result[1] = (byte) (hexCrc32value & 0xFF);//
		return result;
	}

	private int[] getIPValue(String ip) {
		String[] Strs = ip.split("\\.");
		int ipval[] = new int[4];
		for (int i = 0; i < Strs.length; i++) {
			ipval[i] = Integer.parseInt(Strs[i]);
		}

		return ipval;
	}

	public byte[] getNewLXCByteData() {

		// int[] ips = getIPValue(ip);

		byte[] t_type = write2ByteInt(type);

		byte[] v_vmid = writeInt(vmid);

		byte[] v_ip = ip.getBytes(Charset.forName("UTF-8"));

		// byte[] v_ip1 = writeInt(ips[0]);
		// byte[] v_ip2 = writeInt(ips[1]);
		// byte[] v_ip3 = writeInt(ips[2]);
		// byte[] v_ip4 = writeInt(ips[3]);
		// int value_length = v_vmid.length + v_ip1.length + v_ip2.length
		// + v_ip3.length + v_ip4.length + v_template_id.length;

		byte[] v_template_id = writeInt(template_id);
		byte[] v_port = writeInt(port);

		//int value_length = v_vmid.length + 20 + v_template_id.length;
		int value_length = v_vmid.length + v_port.length + v_template_id.length;

		byte[] l_length = write2ByteInt(value_length);

		int socketLength = 2 + 2 + value_length;

		byte[] soc = new byte[socketLength];
		int index = 0;
		// 添加T内容
		for (int i = 0; i < t_type.length; i++) {
			soc[index++] = t_type[i];
		}
		// 添加L内容
		for (int i = 0; i < l_length.length; i++) {
			soc[index++] = l_length[i];
		}
		// 添加V内容
		for (int i = 0; i < v_vmid.length; i++) {
			soc[index++] = v_vmid[i];
		}
		/*for (int i = 0; i < 20; i++) {
			if (i < v_ip.length)
				soc[index++] = v_ip[i];
			else
				soc[index++] = 0;
		}*/
		for (int i = 0; i < v_port.length; i++) {
			soc[index++] = v_port[i];
		}
		// for (int i = 0; i < v_ip2.length; i++) {
		// soc[index++] = v_ip2[i];
		// }
		// for (int i = 0; i < v_ip3.length; i++) {
		// soc[index++] = v_ip3[i];
		// }
		// for (int i = 0; i < v_ip4.length; i++) {
		// soc[index++] = v_ip4[i];
		// }
		for (int i = 0; i < v_template_id.length; i++) {
			soc[index++] = v_template_id[i];
		}

		return soc;
	}

	public byte[] getDelLXCByteData() {

		// int[] ips = getIPValue(ip);

		byte[] t_type = write2ByteInt(type);

		byte[] v_vmid = writeInt(vmid);
		// byte[] v_ip1 = writeInt(ips[0]);
		// byte[] v_ip2 = writeInt(ips[1]);
		// byte[] v_ip3 = writeInt(ips[2]);
		// byte[] v_ip4 = writeInt(ips[3]);
		// byte[] v_template_id = writeInt(template_id);

		int value_length = v_vmid.length;
		byte[] l_length = write2ByteInt(value_length);

		int socketLength = 2 + 2 + value_length;

		byte[] soc = new byte[socketLength];
		int index = 0;
		// 添加T内容
		for (int i = 0; i < t_type.length; i++) {
			soc[index++] = t_type[i];
		}
		// 添加L内容
		for (int i = 0; i < l_length.length; i++) {
			soc[index++] = l_length[i];
		}
		// 添加V内容
		for (int i = 0; i < v_vmid.length; i++) {
			soc[index++] = v_vmid[i];
		}

		return soc;
	}
	
	public byte[] getVMIDtoLXCByteData() {

		byte[] t_type = write2ByteInt(type);

		byte[] v_vmid = writeInt(vmid);

		int value_length = v_vmid.length;
		byte[] l_length = write2ByteInt(value_length);

		int socketLength = 2 + 2 + value_length;

		byte[] soc = new byte[socketLength];
		int index = 0;
		// 添加T内容
		for (int i = 0; i < t_type.length; i++) {
			soc[index++] = t_type[i];
		}
		// 添加L内容
		for (int i = 0; i < l_length.length; i++) {
			soc[index++] = l_length[i];
		}
		// 添加V内容
		for (int i = 0; i < v_vmid.length; i++) {
			soc[index++] = v_vmid[i];
		}

		return soc;
	}
	
	
	/**
	 * 策略变化通知 发送数据
	 * @return
	 * @date 2016-5-16
	 * @author zj
	 */
	public byte[] getRuleChangeLXCByteData() {

		byte[] t_type = write2ByteInt(type);

		//byte[] v_vmid = writeInt(vmid);

		int value_length =0;// v_vmid.length;
		byte[] l_length = write2ByteInt(value_length);

		int socketLength = 2 + 2 + value_length;

		byte[] soc = new byte[socketLength];
		int index = 0;
		// 添加T内容
		for (int i = 0; i < t_type.length; i++) {
			soc[index++] = t_type[i];
		}
		// 添加L内容
		for (int i = 0; i < l_length.length; i++) {
			soc[index++] = l_length[i];
		}
//		// 添加V内容
//		for (int i = 0; i < v_vmid.length; i++) {
//			soc[index++] = v_vmid[i];
//		}

		return soc;
	}
	
	


	public byte[] getRealDatatoLXCByteData() {

		byte[] t_type = write2ByteInt(type);

		byte[] v_vmid = writeInt(0);

		int value_length = v_vmid.length;
		byte[] l_length = write2ByteInt(value_length);

		int socketLength = 2 + 2 + value_length;

		byte[] soc = new byte[socketLength];
		int index = 0;
		// 添加T内容
		for (int i = 0; i < t_type.length; i++) {
			soc[index++] = t_type[i];
		}
		// 添加L内容
		for (int i = 0; i < l_length.length; i++) {
			soc[index++] = l_length[i];
		}
		// 添加V内容
		for (int i = 0; i < v_vmid.length; i++) {
			soc[index++] = v_vmid[i];
		}

		return soc;
	}

	// 字节装转报文string
	public void getReturnData(byte[] socketBytes) {
		byte[] t = new byte[2];
		t[0] = socketBytes[0];
		t[1] = socketBytes[1];
		type = toInt(t);

		byte[] l = new byte[2];
		l[0] = socketBytes[2];
		l[1] = socketBytes[3];
		bodyLen = toInt(l);

		byte[] body = new byte[bodyLen];
		int index = 0;
		for (int i = 4; i < 4 + bodyLen; i++) {
			body[index++] = socketBytes[i];
		}
		msg = bytesToString(body, 0, bodyLen);
	}

	// 将byte数组bRefArr转为一个整数,字节数组的低位是整型的低字节位
	public static int toInt(byte[] bRefArr) {
		int iOutcome = 0;
		// byte bLoop;

		// result[0] = (byte) ((hexCrc32value >> 8) & 0xFF); //
		// result[1] = (byte) (hexCrc32value & 0xFF);//
		//
		iOutcome += (bRefArr[0] & 0xFF) << (8);
		iOutcome += (bRefArr[1] & 0xFF);
		return iOutcome;
	}

	// 将字节数组转化为string
	public String bytesToString(byte[] bytes, int start, int end) {
		String str = "";
		if (bytes.length < end - start) {
			return str;
		}
		byte[] bs = new byte[end - start];
		int index = 0;
		for (int i = 0; i < end - start; i++) {
			bs[i] = bytes[index++];
		}
		str = new String(bs);
		return str;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getBodyLen() {
		return bodyLen;
	}

	public void setBodyLen(int bodyLen) {
		this.bodyLen = bodyLen;
	}

	public int getVmid() {
		return vmid;
	}

	public void setVmid(int vmid) {
		this.vmid = vmid;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getTemplate_id() {
		return template_id;
	}

	public void setTemplate_id(int template_id) {
		this.template_id = template_id;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

}
