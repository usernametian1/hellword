package com.zhiyou.user.service.impl;

import java.util.HashMap;

import com.zhiyou.utils.HttpUtils;
import com.zhiyou.utils.SignVerify;

public class TestMD5 {

	public static void main(String[] args) throws Exception {
		testSynGuser();
		testSynOrderInfo();
		synDailyInfo();
	}

	public static void testSynGuser() throws Exception {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("appid", "10006");
		params.put("guid", "1007");
		params.put("nickName", "测试1007");
		params.put("superGuid", "1003");
		params.put("bindTime", "2017-07-12 12:30:30");
		params.put("regTime", "2017-05-12 12:30:30");
		String sign = SignVerify.getSign(params, "a6bdb76e671ff78bfd96cba559a9dd28");
		params.put("sign", sign);
		String returnValue = HttpUtils.post("http://localhost:8081/agent/userinfo/synGuser", null, params);
		System.out.println(returnValue);
	}

	public static void testSynOrderInfo() throws Exception {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("appid", "10006");
		params.put("guid", "1004");
		params.put("orderId", "123456789105");
		params.put("wares", "测试订单005");
		params.put("totalFee", "1000");
		params.put("payTime", "2017-01-12 12:30:30");
		String sign = SignVerify.getSign(params, "a6bdb76e671ff78bfd96cba559a9dd28");
		params.put("sign", sign);
		String returnValue = HttpUtils.post("http://localhost:8081/agent/order/synOrder", null, params);
		System.out.println(returnValue);
	}

	public static void synDailyInfo() throws Exception {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("appid", "10006");
		params.put("day", "2017-05-14");
		params.put("regNum", "100");
		params.put("dau", "1001");
		params.put("tableNum", "100");
		params.put("roundNum", "1000");
		params.put("useDiamond", "1000");
		params.put("totalFee", "100000");
		params.put("online", "500");
		params.put("onlineTime", "2017-05-14 12:30:30");
		// String sign = SignVerify.getSign(params,
		// "a6bdb76e671ff78bfd96cba559a9dd28");
		// params.put("sign", sign);
		String returnValue = HttpUtils.post("http://127.0.0.1:8081/agent/analysis/synDailyInfo", null, params);
		System.out.println(returnValue);
	}
}
