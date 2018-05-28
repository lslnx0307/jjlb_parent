package com.jjlb.common;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author lslnx0307
 */
public class PropertyUtil {
	public static String getProperties(String key) throws IOException{
		Properties pro = new Properties();
		InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("resources/informContent.properties");
		pro.load(in);
		in.close();
		InputStream in1 = Thread.currentThread().getContextClassLoader().getResourceAsStream("resources/dictionary.properties");
		pro.load(in1);
		in1.close();
		if(key == null){
			return "";
		}else {
			return pro.getProperty(key);
		}
	}
	
}
