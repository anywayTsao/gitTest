package _00_init;

import java.io.*;
import java.security.*;

public class GlobalService {
	public static final int RECORDS_PER_PAGE = 3;
	public static final String host = "127.0.0.1";
	public static final String SYSTEM_NAME = "書豪網路購物商城";
	public static final String JNDI_DB_NAME = "java:comp/env/jdbc/BookDataSQLver";
	public static final int IMAGE_FILENAME_LENGTH = 20;

	public String getSystemName() {
		return SYSTEM_NAME;
	}

	public static String getMD5Endocing(String str) {
		final StringBuffer buffer = new StringBuffer();
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(str.getBytes());
			byte[] digest = md.digest();
			
			for (int i = 0; i < digest.length; ++i) {
				final byte b = digest[i];
				final int value = (b & 0x7F) + (b < 0 ? 128 : 0);
				buffer.append(value < 16 ? "0" : "");
				buffer.append(Integer.toHexString(value));
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
		return buffer.toString();
	}

	public static String getMD5Endocing(File file)
			throws NoSuchAlgorithmException, IOException {
		MessageDigest md = MessageDigest.getInstance("MD5");
		FileInputStream fis = new FileInputStream(file);
		byte[] ba = new byte[1024];
		int len = 0;
		while ((len = fis.read(ba)) != -1) {
			md.update(ba, 0, len);
		}
		byte[] digest = md.digest();
		final StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < digest.length; ++i) {
			byte b = digest[i];
			int value = (b & 0x7F) + (b < 0 ? 128 : 0);
			buffer.append(value < 16 ? "0" : "");
			buffer.append(Integer.toHexString(value));
		}
		return buffer.toString();
	}

	public static void main(String[] args) throws Exception {
		File file = new File("d:\\apache-tomcat-6.0.35-windows-x64.zip");
		// 842de0a085372bcd8c85457635ccb3e4
		System.out.println(getMD5Endocing(file));

		file = new File("d:\\apache-tomcat-6.0.18.exe");
		// fb827381b1eca44bf32273db548157d3
		System.out.println(getMD5Endocing(file));

	}
    // 本方法調整fileName的長度小於或等於maxLength。
	// 如果fileName的長度小於或等於maxLength，直接傳回fileName
	// 否則保留最後一個句點與其後的附檔名，縮短主檔名使得fileName的總長度
	// 等於maxLength。
	public static String adjustFileName(String fileName, int maxLength) {
		  int length = fileName.length();
		  if ( length <= maxLength ) {
			  return fileName ;
		  }
    	  int n      = fileName.lastIndexOf(".");
          int sub    = fileName.length() - n - 1;
          fileName = fileName.substring(0, maxLength-1-sub) + "." 
                       + fileName.substring(n+1); 
		return fileName;
	}
}