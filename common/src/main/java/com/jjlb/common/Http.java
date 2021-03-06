package com.jjlb.common;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;

public class Http {
	public static String httpPost(String strURL, String params, String charset) {
		return Http.httpPost(strURL, params, charset, null);
	}

	public static String httpPost(String strURL, String params, String charset,
			HashMap<String, String> header) {
		String rStr = null;
		byte[] buf = null;
		URL url = null;
		HttpURLConnection urlConn = null;
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		ByteArrayOutputStream baos = null;

		try {
			StringBuffer sb = new StringBuffer();
			url = new URL(strURL);
			urlConn = (HttpURLConnection) url.openConnection();
			urlConn.setConnectTimeout(10000);
			urlConn.setReadTimeout(10000);
			urlConn.setDoInput(true);
			urlConn.setDoOutput(true);
			urlConn.setRequestMethod("POST");
			if (header != null) {
				Iterator<String> iterator = header.keySet().iterator();
				while (iterator.hasNext()) {
					String key = iterator.next();
					String value = header.get(key);
					urlConn.setRequestProperty(key, value);
				}
			}
			bos = new BufferedOutputStream(urlConn.getOutputStream());
			bos.write(params.getBytes());
			bos.flush();
			baos = new ByteArrayOutputStream();
			bis = new BufferedInputStream(urlConn.getInputStream());
			buf = new byte[1024 * 1024];
			int i = 0;
			while ((i = bis.read(buf, 0, buf.length)) != -1) {
				baos.write(buf, 0, i);
			}
			byte[] buf1 = baos.toByteArray();
			rStr = new String(buf1, charset);

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (baos != null)
					baos.close();
			} catch (Exception e) {

			}
			try {
				if (bos != null)
					bos.close();
			} catch (Exception e) {

			}
			try {
				if (bis != null)
					bis.close();
			} catch (Exception e) {

			}
			try {
				if (urlConn != null)
					urlConn.disconnect();
			} catch (Exception e) {

			}
		}
		return rStr;

	}

	public static String httpGet(String strURL, String charset)
			throws Exception {
		String rStr = null;
		byte[] buf = null;
		URL url = null;
		HttpURLConnection urlConn = null;
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;

		try {
			StringBuffer sb = new StringBuffer();
			url = new URL(strURL);
			urlConn = (HttpURLConnection) url.openConnection();
			urlConn.setConnectTimeout(10000);
			urlConn.setReadTimeout(10000);
			bis = new BufferedInputStream(urlConn.getInputStream());
			
			buf = new byte[1024 * 100];
			int i = 0;
			int total = 0;
			while ((i = bis.read(buf, total, buf.length - total)) != -1) {
				total += i;
			}
			rStr = new String(buf, 0, total, charset);

		} catch (Exception e) {
			throw e;
		} finally {
			try {
				if (bos != null)
					bos.close();
			} catch (Exception e) {

			}
			try {
				if (bis != null)
					bis.close();
			} catch (Exception e) {

			}
			try {
				if (urlConn != null)
					urlConn.disconnect();
			} catch (Exception e) {

			}
		}
		return rStr;

	}

}
