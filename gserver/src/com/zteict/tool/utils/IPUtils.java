package com.zteict.tool.utils;

import java.util.ArrayList;
import java.util.List;

/*
 * 
 * @date 2016-3-21
 * 
 * @author zj
 */
public class IPUtils {

	public static void main(String[] args) {
		System.out.println(getNetMask("255.255.255.0"));

		System.out.println(getPoolMax(getNetMask("255.255.255.128")));

		generatIPList("192.168.0.1", "192.168.1.20", "255.255.254.0");

		// System.out.println(getEndIP("10.229.0.1", 24).getStartIP());

		// System.out.println(getEndIP("10.229.0.1", 24).getEndIP());
	}

	/**
	 * 根据起始IP地址和子网掩码计算终止IP
	 */
	public static Nets getEndIP(String StartIP, int netmask) {
		return getEndIP(StartIP, getMask(netmask));
	}

	/**
	 * 根据起始IP地址和子网掩码计算终止IP
	 */
	public static Nets getEndIP(String StartIP, String netmask) {
		Nets nets = new Nets();
		String[] start = Negation(StartIP, netmask).split("\\.");
		nets.setStartIP(start[0] + "." + start[1] + "." + start[2] + "."
				+ (Integer.valueOf(start[3]) + 1));
		nets.setEndIP(TaskOR(Negation(StartIP, netmask), netmask));
		nets.setNetMask(netmask);
		return nets;
	}

	/**
	 * 根据掩码位计算掩码
	 */
	public static String getMask(int masks) {
		if (masks == 1)
			return "128.0.0.0";
		else if (masks == 2)
			return "192.0.0.0";
		else if (masks == 3)
			return "224.0.0.0";
		else if (masks == 4)
			return "240.0.0.0";
		else if (masks == 5)
			return "248.0.0.0";
		else if (masks == 6)
			return "252.0.0.0";
		else if (masks == 7)
			return "254.0.0.0";
		else if (masks == 8)
			return "255.0.0.0";
		else if (masks == 9)
			return "255.128.0.0";
		else if (masks == 10)
			return "255.192.0.0";
		else if (masks == 11)
			return "255.224.0.0";
		else if (masks == 12)
			return "255.240.0.0";
		else if (masks == 13)
			return "255.248.0.0";
		else if (masks == 14)
			return "255.252.0.0";
		else if (masks == 15)
			return "255.254.0.0";
		else if (masks == 16)
			return "255.255.0.0";
		else if (masks == 17)
			return "255.255.128.0";
		else if (masks == 18)
			return "255.255.192.0";
		else if (masks == 19)
			return "255.255.224.0";
		else if (masks == 20)
			return "255.255.240.0";
		else if (masks == 21)
			return "255.255.248.0";
		else if (masks == 22)
			return "255.255.252.0";
		else if (masks == 23)
			return "255.255.254.0";
		else if (masks == 24)
			return "255.255.255.0";
		else if (masks == 25)
			return "255.255.255.128";
		else if (masks == 26)
			return "255.255.255.192";
		else if (masks == 27)
			return "255.255.255.224";
		else if (masks == 28)
			return "255.255.255.240";
		else if (masks == 29)
			return "255.255.255.248";
		else if (masks == 30)
			return "255.255.255.252";
		else if (masks == 31)
			return "255.255.255.254";
		else if (masks == 32)
			return "255.255.255.255";
		return "";
	}

	/**
	 * 将127.0.0.1 形式的IP地址转换成10进制整数，这里没有进行任何错误处理
	 * 
	 * @param strIP
	 * @return
	 */
	public static long ipToLong(String strIP) {
		long[] ip = new long[4];
		int position1 = strIP.indexOf(".");
		int position2 = strIP.indexOf(".", position1 + 1);
		int position3 = strIP.indexOf(".", position2 + 1);
		ip[0] = Long.parseLong(strIP.substring(0, position1));
		ip[1] = Long.parseLong(strIP.substring(position1 + 1, position2));
		ip[2] = Long.parseLong(strIP.substring(position2 + 1, position3));
		ip[3] = Long.parseLong(strIP.substring(position3 + 1));
		long value = (ip[0] << 24) + (ip[1] << 16) + (ip[2] << 8) + ip[3];
		return value; // ip1*256*256*256+ip2*256*256+ip3*256+ip4
	}

	/**
	 * temp1根据temp2取反
	 */
	private static String Negation(String StartIP, String netmask) {
		String[] temp1 = StartIP.trim().split("\\.");
		String[] temp2 = netmask.trim().split("\\.");
		int[] rets = new int[4];
		for (int i = 0; i < 4; i++) {
			rets[i] = Integer.parseInt(temp1[i]) & Integer.parseInt(temp2[i]);
		}
		return rets[0] + "." + rets[1] + "." + rets[2] + "." + rets[3];
	}

	/**
	 * temp1根据temp2取或
	 */
	private static String TaskOR(String StartIP, String netmask) {
		String[] temp1 = StartIP.trim().split("\\.");
		String[] temp2 = netmask.trim().split("\\.");
		int[] rets = new int[4];
		for (int i = 0; i < 4; i++) {
			rets[i] = 255 - (Integer.parseInt(temp1[i]) ^ Integer
					.parseInt(temp2[i]));
		}
		return rets[0] + "." + rets[1] + "." + rets[2] + "." + (rets[3] - 1);
	}

	/**
	 * 计算子网大小
	 */
	public static int getPoolMax(int netmask) {
		if (netmask <= 0 || netmask >= 32) {
			return 0;
		}
		int bits = 32 - netmask;
		return (int) Math.pow(2, bits) - 2;
	}

	/**
	 * 转换为验码位数
	 */
	public static int getNetMask(String netmarks) {
		StringBuffer sbf;
		String str;
		int inetmask = 0, count = 0;
		String[] ipList = netmarks.split("\\.");
		for (int n = 0; n < ipList.length; n++) {
			sbf = toBin(Integer.parseInt(ipList[n]));
			str = sbf.reverse().toString();
			count = 0;
			for (int i = 0; i < str.length(); i++) {
				i = str.indexOf('1', i);
				if (i == -1) {
					break;
				}
				count++;
			}
			inetmask += count;
		}
		return inetmask;
	}

	/**
	 * 根据起始IP,结束IP，子网掩码 生成IP段数据
	 * 
	 * @param startIP
	 * @param endIP
	 * @param subnet
	 * @return
	 * @date 2016-5-12
	 * @author zj
	 */
	public static List<String> generatIPList(String startIP, String endIP,
			String subnet) {
		List<String> ips = new ArrayList<String>();

		try {

			int netmask = getNetMask(subnet);
			int num = 32 - netmask;

			// System.out.println(toBin(192));
			//
			// String decString = toBinaryFromIP(startIP);
			// System.out.println(toIPFromBinary(decString));

			String tempSdata = toBinaryFromIP(startIP);
			String tempEdata = toBinaryFromIP(endIP);

			// 根据起始IP计算，子网掩码表示的固定位数值
			String head = tempSdata.substring(0, netmask);

			// 开始、结束IP二进制数
			String startData = tempSdata.substring(netmask, 32);
			String endData = tempEdata.substring(netmask, 32);

			for (Long i = Long.parseLong(startData, 2); i <= Long.parseLong(
					endData, 2); i++) {

				String curdata = toBinEight(i, num);

				// 拼接当中IP的二进制数
				ips.add(toIPFromBinary(head + curdata));
			}

			for (int i = 0; i < ips.size(); i++) {
				System.out.println(ips.get(i));
			}
		} catch (Exception e) {
			System.err.println("IPUtils:"+e.getMessage());
		}

		return ips;
	}

	/**
	 * 转二进制
	 * 
	 * @param x
	 * @return
	 * @date 2016-3-21
	 * @author zj
	 */
	private static StringBuffer toBin(int x) {
		StringBuffer result = new StringBuffer();
		result.append(x % 2);
		x /= 2;
		while (x > 0) {
			result.append(x % 2);

			x /= 2;
		}
		return result;
	}

	/**
	 * 转二进制,不足8位补0
	 * 
	 * @param x
	 * @return
	 * @date 2016-3-21
	 * @author zj
	 */
	private static String toBinEight(Long x, int sizeNum) {
		StringBuffer result = new StringBuffer();
		do {
			result.append(x % 2);
			x /= 2;
		} while (x > 0);
		// while (x > 0) {
		// result.append(x % 2);
		//
		//
		// x /= 2;
		// }
		int size = result.length();
		for (int i = 0; i < sizeNum - size; i++) {
			result.append(0);
		}

		result.reverse();

		return result.toString();
	}

	/**
	 * 192.168.1.1-->111111010101 全部转为二进制
	 * 
	 * @param ip
	 * @return
	 * @date 2016-5-13
	 * @author zj
	 */
	private static String toBinaryFromIP(String ip) {
		String[] tempStart = ip.split("\\.");

		String decString = (toBinEight(Long.parseLong(tempStart[0]), 8)
				+ toBinEight(Long.parseLong(tempStart[1]), 8)
				+ toBinEight(Long.parseLong(tempStart[2]), 8) + toBinEight(
				Long.parseLong(tempStart[3]), 8));

		return decString;
	}

	/**
	 * 010101111 -->192.168.123.12进制 32位二进制转IP地址
	 * 
	 * @param bdata
	 * @return
	 * @date 2016-5-13
	 * @author zj
	 */
	private static String toIPFromBinary(String bdata) {
		if (bdata.length() != 32)
			return "0.0.0.0";
		else {
			String t1 = bdata.substring(0, 8);
			String t2 = bdata.substring(8, 16);
			String t3 = bdata.substring(16, 24);
			String t4 = bdata.substring(24, 32);

			return Integer.parseInt(t1, 2) + "." + Integer.parseInt(t2, 2)
					+ "." + Integer.parseInt(t3, 2) + "."
					+ Integer.parseInt(t4, 2);
		}

	}

}

class Nets {
	private String StartIP;
	private String EndIP;
	private String NetMask;

	public String getStartIP() {
		return StartIP;
	}

	public void setStartIP(String startIP) {
		StartIP = startIP;
	}

	public String getEndIP() {
		return EndIP;
	}

	public void setEndIP(String endIP) {
		EndIP = endIP;
	}

	public String getNetMask() {
		return NetMask;
	}

	public void setNetMask(String netMask) {
		NetMask = netMask;
	}

}
