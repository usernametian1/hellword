//package com.zhiyou.utils;
//
//import java.io.IOException;
//
//import org.apache.commons.httpclient.HttpClient;
//import org.apache.commons.httpclient.HttpException;
//import org.apache.commons.httpclient.NameValuePair;
//import org.apache.commons.httpclient.methods.PostMethod;
//
//
//public class SendMsg_webchinese {
//
//	/**
//	 * @author tcr
//	 * @date Sep 18, 2012
//	 * @time 9:38:25 AM
//	 * @param args
//	 * @return
//	 * @throws IOException
//	 * @throws HttpException
//	 * @description
//	 */
//
//	public int MobileVerify(String mobile, String verifyCode) {
//		String CONTENT = EjavashopConfig.SMS_VERIFY_CODE.replace("@", verifyCode);
//		HttpClient client = new HttpClient();
//		PostMethod post = new PostMethod("http://sz.ipyy.net/smsJson.aspx");
//		post.addRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");// 在头文件中设置转码
//		NameValuePair[] data = { new NameValuePair("action", "send"), new NameValuePair("account", EjavashopConfig.SMS_ACCOUNT), new NameValuePair("password", EjavashopConfig.SMS_PASSWORD),
//				new NameValuePair("mobile", mobile), new NameValuePair("content", CONTENT) };
//		post.setRequestBody(data);
//		try {
//			client.executeMethod(post);
//		} catch (IOException e) {
//			e.getMessage();
//		}
//		return post.getStatusCode();// 成功返回200
//	}
//
//	// 测试 手机 验证
//	/*
//	 * public static void main(String[] args) { SendMsg_webchinese sms = new
//	 * SendMsg_webchinese();
//	 * 
//	 * sms.MobileVerify("17317872312","123456");
//	 * 
//	 * }
//	 */
//	/*
//	 * public static void main(String[] args)throws Exception{
//	 * 
//	 * String verifyCode = RandomUtil.generateDigitalString(6); String CONTENT =
//	 * EjavashopConfig.SMS_VERIFY_CODE.replace("@", verifyCode); HttpClient
//	 * client = new HttpClient(); PostMethod post = new
//	 * PostMethod("http://sz.ipyy.com/sms.aspx");
//	 * post.addRequestHeader("Content-Type"
//	 * ,"application/x-www-form-urlencoded;charset=utf-8");//在头文件中设置转码
//	 * 
//	 * NameValuePair[] data ={new NameValuePair("action","send"), new
//	 * NameValuePair("account", "szzd00272"), // new NameValuePair("password",
//	 * "szzd0027233"), new NameValuePair("mobile","17317872312"), new
//	 * NameValuePair("content",CONTENT)}; post.setRequestBody(data);
//	 * 
//	 * client.executeMethod(post); Header[] headers = post.getResponseHeaders();
//	 * int statusCode = post.getStatusCode();
//	 * System.out.println("statusCode:"+statusCode); for(Header h : headers) {
//	 * System.out.println(h.toString()); } String result = new
//	 * String(post.getResponseBodyAsString().getBytes("utf-8"));
//	 * System.out.println(result);
//	 * 
//	 * 
//	 * post.releaseConnection();
//	 * 
//	 * }
//	 */
//
//	public SendMsg_webchinese() {
//		super();
//		// TODO Auto-generated constructor stub
//	}
//
//}