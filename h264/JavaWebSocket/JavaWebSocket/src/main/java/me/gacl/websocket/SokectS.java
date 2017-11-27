package me.gacl.websocket;

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
import java.util.Base64;
import java.util.Date;

import javax.print.attribute.standard.Severity;

import me.gacl.sk.WsFrame;
import me.gacl.sk.WsFrame.OPCODE;
import me.gacl.util.LineBuffer;

public class SokectS {
	/**
	 * 
	 * @param map
	 * @return
	 * @author zj
	 * @date 2017-11-8
	 */

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

	public static void main(String args[]) {
		 start(8888);

		//test();

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

	public static String getNewKey(String key)
	{
		String nk ="";
		MessageDigest mdTemp;
		try {
			mdTemp = MessageDigest.getInstance("SHA1");

			mdTemp.update((key + "258EAFA5-E914-47DA-95CA-C5AB0DC85B11")
					.getBytes("UTF-8"));

			byte[] md = mdTemp.digest();

			 nk = new String(Base64.getEncoder().encode(md), "utf-8");

		//	System.out.println(nk);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return nk;
	}
	
	public static void test() {

		char h = 'l';

		// System.out.println(Integer.toHexString(h));

		String key = "/zc6eZ96KuXgJ1SpgExhkA==";

		
		/*
		 * char a[] = new char[] { 0x81, 0x05, 0x48, 0x65, 0x6c, 0x6c, 0x6f };
		 * 
		 * for (int i = 0; i < a.length; i++) { System.out.println(a[i]); }
		 */

	}

	public static boolean isbreak = false;

	public static void start(int port) {
		ServerSocket ss;
		try {
			ss = new ServerSocket(port);

			while (true) {
				Socket clintS = ss.accept();

				new Thread(new Handler(clintS)).start();

				if (isbreak)
					break;
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static String execPython(String param) {
		String line = "";
		String lines = "";

		/*
		 * byte[] sha = sha1(("d359Fdo6omyqfxyYF7Yacw==" +
		 * "258EAFA5-E914-47DA-95CA-C5AB0DC85B11").getBytes());
		 * System.out.println(new String(Base64.getEncoder().encode(sha)));
		 */

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

		private Socket sk;
		private boolean isclose = false;

		public Handler(Socket s) {
			sk = s;
			System.out.println(sk.getInetAddress().getHostAddress() + " "
					+ sk.getPort() + " connect!");
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
						out += "Sec-WebSocket-Accept: " + newkey + "\r\n";
						// out +=
						// "Sec-WebSocket-Extensions: permessage-deflate;client_max_window_bits=15\r\n";
						out += "Date: " + new Date().toString() + "\r\n\r\n";
						// out +="\r\n";
						System.out.println(out);

						sk.getOutputStream().write(out.getBytes("utf-8"));
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

							byte[] sendrply = WsFrame.buildFrame(frame.alldata,OPCODE.TEXT);
							sk.getOutputStream().write(sendrply);

							// send PONG
						} else if (frame.opcode == OPCODE.BIANRY) {
							
							String d="我";
							byte[] bb=d.getBytes();
							
							// send PONG
							System.out.println(frame.getMsgData());
							
							byte[] sendrply = WsFrame.buildFrame(frame.alldata,OPCODE.BIANRY);
							sk.getOutputStream().write(sendrply);
						}

						// sk.getOutputStream().write(("server:"+lines+"")
						// .getBytes());
					}

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					isclose = true;
					try {
						if (sk != null)
							sk.close();
					} catch (Exception e2) {

					}

				}
			}
		}

	}

}
