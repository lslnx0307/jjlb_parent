package com.jjlb.common;

import java.util.HashMap;
import java.util.Map;

public class ConfigUtil {

	//正式环境
//	private static final String domainName = "https://www.jinjilibao.com";
//	private static final String IMG_FILE_URL = "/alidata/www";
	//测试环境
	private static final String domainName = "http://192.168.1.226";
	private static final String IMG_FILE_URL = "/data/www";
	//阿里测试环境
//	private static final String domainName = "http://172.16.138.228";
//	private static final String IMG_FILE_URL = "/alidata/www";


	public static final String ADMIN_LOGIN_USER = "adminLoginUser";// 后台登陆用户

	//加载数据字典
	public static Map<String,Map<Integer,String>> dictionaryMap = new HashMap<String,Map<Integer,String>>();
	
	// 文章存放路劲
	public static final String articleImgPath = "/upload/atricle/";
	// 广告存放路劲
	public static final String bannerImgPath = "/upload/banner/";
	//风控图片
	public static final String claimsPic = "/upload/claimsPic/";
	public static final String productPic = "/upload/productPic/";
	
	public static final String investSendPic = "/upload/investSendPic/";
	

	public static String getImgFileUrl() {
		return IMG_FILE_URL;
	}
	
	public static String getDomainname() {
		return domainName;
	}
}
