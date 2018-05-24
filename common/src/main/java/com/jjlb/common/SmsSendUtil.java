package com.jjlb.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dahantc.api.sms.json.JSONHttpClient;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.log4j.Logger;

import java.net.URLEncoder;

public class SmsSendUtil {
	private static Logger log = Logger.getLogger(SmsSendUtil.class);
	// 正式环境配置，后台访问地址www.10690221.com/210.5.158.31
	private static final String url = "http://210.5.158.31/hy/";

	private static final String companyCode = "bsjr";// 企业代码

	private static final String username = "90333";// 短信账号

	private static final String pwd = "9W3c#x8";// 密码

	// 大汉三通短信云平台
	private static final String account = "dh39841";// 用户名（必填）
	private static final String password = "0EnpJmBQ";// 密码（必填）
	public static final String sign = "【金吉利宝】"; // 短信签名（必填）
	public static final String subcode = ""; // 子号码（可选）



	/**
	 * 即时发送-希奥
	 * 
	 * @param mobile 手机号码
	 * @param content 短信内容
	 * @return 发送成功返回99，失败返回-99
	 * @throws Exception
	 */
	public static int sendMsgByXiAo(String mobile, String content) throws Exception {
		HttpClient httpClient = new HttpClient();
		content = URLEncoder.encode(content, "gbk");
		String auth = SecurityUtils.MD5(companyCode + pwd);
		PostMethod postMethod = new PostMethod(url);

		NameValuePair[] data = { new NameValuePair("uid", username), new NameValuePair("auth", auth), new NameValuePair("mobile", mobile), new NameValuePair("expid", "0"), new NameValuePair("msg", content) };
		// postMethod.setRequestBody(data);
		// httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(5000);
		// httpClient.getHttpConnectionManager().getParams().setSoTimeout(5000);
		// int statusCode = httpClient.executeMethod(postMethod);
		//
		// if (statusCode == HttpStatus.SC_OK) {
		// BufferedReader br = new BufferedReader(new
		// InputStreamReader(postMethod.getResponseBodyAsStream()));
		// StringBuffer stringBuffer = new StringBuffer();
		// String str= "";
		// while((str = br.readLine()) != null){
		// stringBuffer .append(str );
		// }
		// String[] array = stringBuffer.toString().split(",");
		// return Integer.parseInt(array[0])==0?99:Integer.parseInt(array[0]);
		// }
//		return -99;
		return 99;
	}

	/**
	 * 即时发送-大汉三通
	 * 
	 */
	public static int sendMsgByDaHan(String mobile, String content) throws Exception {
		// 大汉三通
		JSONHttpClient jsonHttpClient = new JSONHttpClient("http://www.dh3t.com");
		jsonHttpClient.setRetryCount(1);
		String sendhRes = jsonHttpClient.sendSms(account, password, mobile, content, sign, subcode);
		JSONObject jsonObject = JSON.parseObject(sendhRes);
		return Integer.parseInt(jsonObject.getString("result"));
	}

	/**
	 * 定时短信发送
	 * @param mobile 手机号码
	 * @param content 短信内容
	 * @param time 短信发送时间
	 * @return 发送成功返回88，失败返回-88
	 * @throws Exception
	 */
	public static int sendTimeMsg(String mobile, String content, String time) throws Exception {
		HttpClient httpClient = new HttpClient();
		content = URLEncoder.encode(content, "gbk");
		String auth = SecurityUtils.MD5(companyCode + pwd);
		PostMethod postMethod = new PostMethod(url);

		NameValuePair[] data = { new NameValuePair("uid", username), 
				new NameValuePair("auth", auth),
				new NameValuePair("mobile", mobile), 
				new NameValuePair("expid", "0"), 
				new NameValuePair("msg", content),
				new NameValuePair("time", time) };
//		postMethod.setRequestBody(data);
//		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(5000);  
//		httpClient.getHttpConnectionManager().getParams().setSoTimeout(5000);
//		int statusCode = httpClient.executeMethod(postMethod);
//		
//		if (statusCode == HttpStatus.SC_OK) {
//			BufferedReader br = new BufferedReader(new InputStreamReader(postMethod.getResponseBodyAsStream()));
//			StringBuffer stringBuffer = new StringBuffer();
//			String str= "";
//			while((str = br.readLine()) != null){
//				stringBuffer .append(str );
//			}
//			String[] array = stringBuffer.toString().split(",");
//			return Integer.parseInt(array[0])==0?88:Integer.parseInt(array[0]);
//		}
		return 88;
	}

	public static void main(String[] args) {
		// System.out.println(Utils.parseDate(Utils.format(new Date(),
		// "yyyy-MM-dd 10:00:00"),"yyyy-MM-dd HH:mm:ss"));
		// sendMsg("15801868241", "测试及时");
		try {
			// int result = sendMsgByQxt("15000896419", "恭喜您，已经通过认证！");
			// System.out.println("result:" + result);
			// sendMsgByXiAo("15801868241", "测试及时，验证码：2383930");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
