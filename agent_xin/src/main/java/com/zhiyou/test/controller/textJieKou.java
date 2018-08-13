package com.zhiyou.test.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;
import com.zhiyou.utils.HttpUtils;
import com.zhiyou.utils.SignVerify;

public class textJieKou {
	
	
 public static void main(String[] args) {
	 final Logger log = Logger .getLogger(textJieKou.class);
	 /**
	  * 测试接口
	  */
	 
	 	int appid =10006;
	 	String guid = "1003";
	 	String superGuid = "4444";
	 	String tableId = "1456";
	 	String useDiamond = "20";
	 	String roundNum = "10";
	 	String url = "http://127.0.0.1:8080/agent/analysis/agent/synTableInfo";
	 	Map<String ,String> params = new HashMap<String ,String>();
	 	params.put("appid", String.valueOf(appid));
	 	params.put("guid", guid);
	 	params.put("superGuid", superGuid);
	 	params.put("tableId", tableId);
	 	params.put("useDiamond", useDiamond);
	 	params.put("roundNum", roundNum);
	 	params.put("day", "2017-05-22");
	 	
		params.put("sign", SignVerify.getSign(params, "a6bdb76e671ff78bfd96cba559a9dd28"));
	 	try {
	 		
			String returnValue = HttpUtils.post(url, null, params);
			JSONObject jsonObject =JSONObject.parseObject( returnValue);
			
			String strCode =jsonObject.getString("code");
			System.out.println("strCode" + strCode);
			if(!strCode.equals(null)){
				if ("OK".equals(strCode)) {
					log.info("success of syn");;
			} else {
				log.error("appid:" + appid + ";guId:" + guid + ";同步结果:" + returnValue);
			}
			}
			log.error("appid:" + appid + ";guId:" + guid + ";同步结果:" + returnValue);
		} catch (Exception e) {
			log.error("====同步异常===");
		}

	
}
 
}
