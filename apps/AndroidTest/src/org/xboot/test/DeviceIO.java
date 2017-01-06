package org.xboot.test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class DeviceIO {
	public static void write(String fileName, String buffer)
			throws IOException {
		try {
			FileOutputStream fout = new FileOutputStream(fileName);
			byte[] bytes = buffer.getBytes();
			fout.write(bytes);
			fout.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String read(String fileName) {
		String res = "";
		try {
			FileInputStream fin = new FileInputStream(fileName);
			int length = fin.available();
			byte[] buffer = new byte[length];
			length = fin.read(buffer);
			res = new String(buffer);
			res = String.copyValueOf(res.toCharArray(), 0, length);
			fin.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}
}
