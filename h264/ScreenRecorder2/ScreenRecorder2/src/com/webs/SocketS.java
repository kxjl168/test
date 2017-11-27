package com.webs;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArraySet;

import android.util.Base64;
import android.util.Log;

import com.screenshare.Common;
import com.webs.WsFrame.OPCODE;

public class SocketS {
	/**
	 * 
	 * @param map
	 * @return
	 * @author zj
	 * @date 2017-11-8
	 */

	public static void main(String args[]) {
		// start(8888);

		test();

		/*
		 * byte[] a = charToByte('A'); for (int i = 0; i < a.length; i++) {
		 * System.out.println(a[i]); }
		 * 
		 * System.out.println(0x41);
		 */
	}

	public static byte[] charToByte(char c) {
		byte[] b = new byte[2];
		b[0] = (byte) ((c & 0xFF00) >> 8);
		b[1] = (byte) (c & 0xFF);
		return b;
	}

	public static void test() {

		char h = 'l';

		// System.out.println(Integer.toHexString(h));

		String key = "/zc6eZ96KuXgJ1SpgExhkA==";

		String s = sha1(key + "258EAFA5-E914-47DA-95CA-C5AB0DC85B11");
		String nk = new String(Base64.encode(s.getBytes(), Base64.DEFAULT));

		Log.i(TAG, nk);
		/*
		 * char a[] = new char[] { 0x81, 0x05, 0x48, 0x65, 0x6c, 0x6c, 0x6f };
		 * 
		 * for (int i = 0; i < a.length; i++) { System.out.println(a[i]); }
		 */

	}

	public static boolean isbreak = false;

	public static CopyOnWriteArraySet<Handler> clinetSockets = new CopyOnWriteArraySet<Handler>();

	public static void sendDataToAllClinet(byte[] d) {
		Iterator<Handler> css = clinetSockets.iterator();
		while (css.hasNext()) {

			try {
				byte[] sendrply = WsFrame.buildFrame(d, OPCODE.BIANRY);

				Handler hs = css.next();
				hs.SendHead();
				hs.sk.getOutputStream().write(sendrply);
			} catch (Exception e) {
				// TODO: handle exception
			}

		}
	}

	public static String getNewKey(String key) {
		String nk = "";
		MessageDigest mdTemp;
		try {
			mdTemp = MessageDigest.getInstance("SHA1");

			mdTemp.update((key + "258EAFA5-E914-47DA-95CA-C5AB0DC85B11")
					.getBytes("UTF-8"));

			byte[] md = mdTemp.digest();

			nk = new String(Base64.encode(md, Base64.DEFAULT), "utf-8");

			// System.out.println(nk);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return nk;
	}

	public static ServerSocket ss;
	private static final String TAG = "RecordScreenService";

	public static void start(int port) {
		Log.i(TAG, "start...");
		try {
			if (ss == null || ss.isClosed()) {
				ss = new ServerSocket(port);

				Log.i(TAG, "ServerSocket linsten on " + port);

				while (true) {
					Socket clintS = ss.accept();

					Handler h = new Handler(clintS);
					clinetSockets.add(h);

					new Thread(h).start();

					if (isbreak)
						break;
				}
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static String sha1(String str) {
		if (null == str || 0 == str.length()) {
			return null;
		}
		char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'a', 'b', 'c', 'd', 'e', 'f' };
		try {
			MessageDigest mdTemp = MessageDigest.getInstance("SHA1");
			mdTemp.update(str.getBytes("UTF-8"));

			byte[] md = mdTemp.digest();
			int j = md.length;
			char[] buf = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				buf[k++] = hexDigits[byte0 >>> 4 & 0xf];
				buf[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(buf);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String execPython(String param) {
		String line = "";
		String lines = "";

		// 需传入的参数
		try {
			System.out.println("start;;;");
			// 设置命令行传入参数
			String[] args = new String[] { "python",
					"F:\\zte\\00036-websocket\\p.py", param };
			Process pr;

			pr = Runtime.getRuntime().exec(args);

			BufferedReader in = new BufferedReader(new InputStreamReader(
					pr.getInputStream()));

			while ((line = in.readLine()) != null) {
				// line = decodeUnicode(line);
				System.out.println(line);

				lines += line;
			}
			in.close();
			pr.exitValue();
			// System.out.println("end");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return lines;
	}

	public static class Handler implements Runnable {

		public Socket sk;
		private boolean isclose = false;

		public boolean isfirst = true;

		public void SendHead() {

			if (isfirst) {

				try {
					String wh = String.format(HTTP_MESSAGE_TEMPLATE,
							Common.DEFAULT_SCREEN_WIDTH,
							Common.DEFAULT_SCREEN_HEIGHT);
					byte[] bswh = WsFrame.buildFrame(wh.getBytes(),
							OPCODE.BIANRY);
					sk.getOutputStream().write(bswh);

					byte[] bshd = WsFrame.buildFrame(
							H264_PREDEFINED_HEADER_1280x720, OPCODE.BIANRY);
					sk.getOutputStream().write(bshd);
					isfirst = false;
				} catch (Exception e) {
					// TODO: handle exception
				}

			}
		}

		public Handler(Socket s) {
			sk = s;
			System.out.println(sk.getInetAddress().getHostAddress() + " "
					+ sk.getPort() + " connect!");
			Log.i(TAG,
					sk.getInetAddress().getHostAddress() + " " + sk.getPort()
							+ " connect!");
		}

		@Override
		public void run() {

			while (!isclose) {
				try {
					InputStream is = sk.getInputStream();
					// int off = 0;
					int len = 0;
					int buffsize = 2048;
					byte[] bufferOut = new byte[buffsize];

					String lines = "";
					String line = "";
					LineBuffer lineBuffer = new LineBuffer(1024);

					len = is.read(bufferOut, len, buffsize);

					if (len == -1)
						return;

					lines = new String(bufferOut);

					if (lines.startsWith("GET")) {

						Log.i(TAG, lines);

						//
						// Sec-WebSocket-Key: t8tuPMuVk5IqGNHKp3IsKg==
						int start = lines.indexOf("Sec-WebSocket-Key");
						int end = lines.indexOf("\r\n", start);
						String key = lines.substring(start + 19, end);

						String newkey = getNewKey(key);// execPython(key);

						//
						String out = "";
						out += "HTTP/1.1 101 Switching Protocols\r\n";
						// out += "Server: Apache-Coyote/1.1\r\n";
						out += "Upgrade: websocket\r\n";
						out += "Connection: upgrade\r\n";
						out += "Sec-WebSocket-Accept: " + newkey;// + "\r\n";

						if (lines.contains("Sec-WebSocket-Protocol: binary"))
							out += "Sec-WebSocket-Protocol: binary\r\n";// +
																		// "\r\n";

						// out +=
						// "Sec-WebSocket-Extensions: permessage-deflate;client_max_window_bits=15\r\n";
						out += "Date: " + new Date().toString() + "\r\n\r\n";
						// out +="\r\n";

						Log.i(TAG, out);

						System.out.println(out);

						sk.getOutputStream().write(out.getBytes("utf-8"));

						// sendH264HeadData();

					} else {

						WsFrame frame = new WsFrame(bufferOut);

						System.out.println(frame.opcode.toString() + "...");
						System.out.println("");

						if (frame.opcode == OPCODE.CONN_CLOSE) {
							sk.close();
							isclose = true;
							System.out.println(frame.getMsgData());

						} else if (frame.opcode == OPCODE.PING) {
							// send PONG
						} else if (frame.opcode == OPCODE.PONG) {
							// send PONG
						} else if (frame.opcode == OPCODE.TEXT) {

							System.out.println(frame.getMsgData());

							byte[] sendrply = WsFrame.buildFrame(frame.alldata,
									OPCODE.TEXT);
							sk.getOutputStream().write(sendrply);

							// send PONG
						} else if (frame.opcode == OPCODE.BIANRY) {
							// send PONG
						}

						// sk.getOutputStream().write(("server:"+lines+"")
						// .getBytes());
					}

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					isclose = true;
					try {
						clinetSockets.remove(sk);

						if (sk != null)
							sk.close();

					} catch (Exception e2) {

					}

				}
			}
		}

	}

	/*private static void sendH264HeadData() {
		sendData(H264_PREDEFINED_HEADER_1280x720);
	}*/

	// 1280x720@25
	private static final byte[] H264_PREDEFINED_HEADER_1280x720 = {
			(byte) 0x21, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
			(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
			(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
			(byte) 0x01, (byte) 0x67, (byte) 0x42, (byte) 0x80, (byte) 0x20,
			(byte) 0xda, (byte) 0x01, (byte) 0x40, (byte) 0x16, (byte) 0xe8,
			(byte) 0x06, (byte) 0xd0, (byte) 0xa1, (byte) 0x35, (byte) 0x00,
			(byte) 0x00, (byte) 0x00, (byte) 0x01, (byte) 0x68, (byte) 0xce,
			(byte) 0x06, (byte) 0xe2, (byte) 0x32, (byte) 0x24, (byte) 0x00,
			(byte) 0x00, (byte) 0x7a, (byte) 0x83, (byte) 0x3d, (byte) 0xae,
			(byte) 0x37, (byte) 0x00, (byte) 0x00 };

	// 800x480@25
	private static final byte[] H264_PREDEFINED_HEADER_800x480 = { (byte) 0x21,
			(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
			(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
			(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x01,
			(byte) 0x67, (byte) 0x42, (byte) 0x80, (byte) 0x20, (byte) 0xda,
			(byte) 0x03, (byte) 0x20, (byte) 0xf6, (byte) 0x80, (byte) 0x6d,
			(byte) 0x0a, (byte) 0x13, (byte) 0x50, (byte) 0x00, (byte) 0x00,
			(byte) 0x00, (byte) 0x01, (byte) 0x68, (byte) 0xce, (byte) 0x06,
			(byte) 0xe2, (byte) 0x32, (byte) 0x24, (byte) 0x00, (byte) 0x00,
			(byte) 0x7a, (byte) 0x83, (byte) 0x3d, (byte) 0xae, (byte) 0x37,
			(byte) 0x00, (byte) 0x00 };

	private static final String HTTP_MESSAGE_TEMPLATE = "POST /api/v1/h264 HTTP/1.1\r\n"
			+ "Connection: close\r\n"
			+ "X-WIDTH: %1$d\r\n"
			+ "X-HEIGHT: %2$d\r\n" + "\r\n";

}
