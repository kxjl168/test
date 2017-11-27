package me.gacl.sk;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class WsFrame {

	public enum OPCODE {
		/*
		 * %x1 ; text frame / %x2 ; binary frame / %x3-7 ; 4 bits in length, ;
		 * reserved for further non-control frames
		 * 
		 * frame-opcode-control = %x8 ; connection close / %x9 ; ping / %xA ;
		 * pong
		 */
		TEXT(1), BIANRY(2), CONN_CLOSE(8), PING(9), PONG(10);

		private Integer value;

		private OPCODE(int v) {
			value = v;
		}

		public static OPCODE Parse(Integer value) {
			for (OPCODE op : OPCODE.values()) {
				if (op.value == value)
					return op;
			}
			return null;
		}

		public String toString() {
			return this.name().toString();
		}
	}

	public enum FrameType {
		/*
		 * %x1 ; text frame / %x2 ; binary frame / %x3-7 ; 4 bits in length, ;
		 * reserved for further non-control frames
		 * 
		 * frame-opcode-control = %x8 ; connection close / %x9 ; ping / %xA ;
		 * pong
		 */
		SINGLE(1, 100), MUTI_FIR(0, 100), MUTI_CENTER(0, 0), MUTI_LAST(1, 0);

		private Integer fin;
		private Integer op;

		public static FrameType parse(int f, int opv) {

			FrameType t = SINGLE;

			if (f == 1 && opv != 0)
				t = SINGLE;
			else if (f == 0 && opv != 0)
				t = MUTI_FIR;
			else if (f == 0 && opv == 0)
				t = MUTI_CENTER;
			else if (f == 1 && opv == 0)
				t = MUTI_LAST;

			return t;
		}

		private FrameType(int f, int opv) {
			fin = f;
			op = opv;
		}

	}

	/**
	 * 
	 * @param map
	 * @return
	 * @author zj
	 * @date 2017-11-8
	 */

	public byte fin; // fin=1 opcodevalue!=0 独立消息 / fin=0 & opcodevalue=0 后续消息 /
						// fin=1 &opcodevalue=0 最后一段消息

	public FrameType fType;

	public byte rsv1;
	public byte rsv2;
	public byte rsv3;
	public int opcodevalue; // 4bit
	public OPCODE opcode; // 4bit
	public byte mask;// mask为1，有maskkey
	public int payloadlen; // either 7, 7+16, or 7+64 bits in length

	public byte[] maskkey;// 0 或者 4 个数位

	public byte[] alldata;
	public byte[] statusdata;// data的前两个byte.
	public byte[] realdata;// data剩下的数据

	public String getStatus() {
		char a = (char) statusdata[0];
		char b = (char) statusdata[1];
		return String.valueOf(a) + String.valueOf(b);
	}

	public String getMsgData() {
		try {

			if (opcode == OPCODE.CONN_CLOSE)
				return new String(realdata, "utf-8");
			else
				return new String(alldata, "utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 根据websocket协议.构造发送数据
	 * 
	 * @param map
	 * @return
	 * @author zj
	 * @date 2017-11-9
	 */
	public static byte[] buildFrame(byte[] sendBytes, OPCODE type) {
		byte[] buf = null;

		byte b1 = 0x0;
		if (type == OPCODE.TEXT)
			b1 |= 0x81; // fin =1
		else if (type == OPCODE.BIANRY)
			b1 |= 0x82; // fin =1

		int length = sendBytes.length;

		if (length <= 125) {

			buf = new byte[length + 2];
			byte b2 = 0x0;
			b2 |= length; // fin =1
			buf[0] = b1;
			buf[1] = b2;
			System.arraycopy(sendBytes, 0, buf, 2, length);

		} else if (length < (1 << 16)) {
			buf = new byte[length + 2 + 2];

			byte b2 = 0x0;
			b2 |= 126; // fin =1
			buf[0] = b1;
			buf[1] = b2;
			buf[2] = (byte) ((((char) length) & 0xFF00) >> 8);
			buf[3] = (byte) ((((char) length) & 0xFF));
			System.arraycopy(sendBytes, 0, buf, 4, length);
		} else {

			/*
			 * long b=sendBytes.length;
			 * 
			 * 
			 * byte b2 = 0x0; b2 |= 127; // fin =1 buf[0] = b1; buf[1] = b2;
			 * buf[2] = (byte) (( b & 0xFF00000000000000) >> 56); buf[3] =
			 * (byte) ((((char) length) & 0xFF00000000000000) >> 56); buf[4] =
			 * (byte) ((((char) length) & 0xFF00000000000000) >> 56); buf[5] =
			 * (byte) ((((char) length) & 0xFF00000000000000) >> 56); buf[6] =
			 * (byte) ((((char) length) & 0xFF00000000000000) >> 56); buf[7] =
			 * (byte) ((((char) length) & 0xFF00000000000000) >> 56); buf[8] =
			 * (byte) ((((char) length) & 0xFF00000000000000) >> 56); buf[9] =
			 * (byte) ((((char) length) & 0xFF00000000000000) >> 56);
			 * System.arraycopy(sendBytes, 0, buf, 10, length);
			 */

		}

		return buf;
	}

	/**
	 * websocket数据包
	 * 
	 * @param map
	 * @return
	 * @author zj
	 * @date 2017-11-9
	 */
	public WsFrame(byte[] frameinput) {

		int cur = 0;

		// 第一字节
		fin = (byte) ((((char) frameinput[cur]) & 0x80) >> 7);
		rsv1 = (byte) ((((char) frameinput[cur]) & 0x40) >> 6);
		rsv2 = (byte) ((((char) frameinput[cur]) & 0x20) >> 5);
		rsv3 = (byte) ((((char) frameinput[cur]) & 0x10) >> 4);
		opcodevalue = (byte) ((((char) frameinput[cur]) & 0x0F));
		opcode = OPCODE.Parse(opcodevalue);
		fType = FrameType.parse(fin, opcodevalue);
		cur++;

		// 第二字节
		mask = (byte) ((((char) frameinput[cur]) & 0x80) >> 7);
		payloadlen = (byte) (((char) frameinput[cur]) & 0x7F);
		cur++;

		if (payloadlen <= 125) // payloadlen即为数据长度
		{

		} else if (payloadlen == 126) // 后续两个字节为长度
		{
			payloadlen = ((frameinput[cur] & 0xF0) << 4)
					+ (frameinput[cur] & 0xF);
			cur++;
			payloadlen += (frameinput[cur] & 0xFF);
			cur++;
		} else if (payloadlen == 127) // 后续8个字节为长度
		{

		}

		if (mask == 1) {
			maskkey = new byte[4];
			maskkey[0] = frameinput[cur++];
			maskkey[1] = frameinput[cur++];
			maskkey[2] = frameinput[cur++];
			maskkey[3] = frameinput[cur++];
		}

		if (payloadlen > 0) {
			// 解码
			alldata = new byte[payloadlen];
			for (int i = 0; i < payloadlen; i++) {
				alldata[i] = (byte) (((char) maskkey[i % 4]) ^ ((char) frameinput[cur++]));
			}

			if (opcode == OPCODE.CONN_CLOSE) {
				// msg
				realdata = new byte[payloadlen - 2];
				for (int i = 0; i < payloadlen - 2; i++) {
					realdata[i] = alldata[i + 2];
				}
				// msg code
				statusdata = new byte[2];
				statusdata[0] = alldata[0];
				statusdata[1] = alldata[1];
				System.out.println(statusdata[0] + "," + statusdata[1]);
				String strData = "";
			}
			try {

				if (opcode == OPCODE.TEXT || opcode == OPCODE.BIANRY) {
					if (rsv1 == 1) // deflater 解压缩

					{
						byte[] defalterByte = new byte[alldata.length];
						defalterByte = Arrays.copyOf(alldata, alldata.length);

						byte[] result = new byte[2014];
						Inflater inf = new Inflater();
						inf.setInput(defalterByte);
						int infLen = inf.inflate(result);
						inf.end();
						String strOgr = new String(result, "utf-8");
						// System.out.println("str ogr "+strOgr);
						// return Arrays.copyOf(result, infLen);
					}
				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
}
