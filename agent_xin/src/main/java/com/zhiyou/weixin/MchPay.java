package com.zhiyou.weixin;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.KeyStore;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.net.ssl.SSLContext;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.zhiyou.utils.MD5;
import com.zhiyou.utils.RandStrUtils;

public class MchPay {

    private static final String mchPayUrl = "https://api.mch.weixin.qq.com/mmpaymkttransfers/promotion/transfers";
    private static final String apiKey = "j78s7cQ4VBVDhwpgimFFda1lO5VBvqV2";
    private static final String appid = "wx378b6fc928d3e4fa";
    private static final String mchid = "1306601301";
    private static final String PKCS12_file = "F:/cert/apiclient_cert.p12";
    
    private static MchPay instance = new MchPay();
    public static MchPay getInstance(){
        return instance;
    }
    
//    private String getPartnerTradeNo() {
//        return DateUtils.format(new Date(), "yyyyMMddHHmmssSSS") +RandStrUtils.randNumeric(8);
//    }
    
    private Map<String,String>  requestPara(String orderId,String openid,String re_user_name,int amount,String desc,String spbill_create_ip) {
        Map<String,String> params = new HashMap<String,String>();
        params.put("mch_appid", appid);//公众账号appid
        params.put("mchid", mchid);//商户号
        params.put("device_info", null);//设备号
        params.put("nonce_str", RandStrUtils.randAlphanumberic(32));//随机字符串
        params.put("partner_trade_no", orderId);//商户订单号
        params.put("openid", openid);
        params.put("check_name", "FORCE_CHECK");//NO_CHECK：不校验真实姓名 FORCE_CHECK：强校验真实姓名
        params.put("re_user_name", re_user_name);//收款用户姓名
        params.put("amount", String.valueOf(amount));//企业付款金额，单位为分
        params.put("desc", desc);//企业付款描述信息
        params.put("spbill_create_ip", spbill_create_ip);//调用接口的机器Ip地址
        params.put("sign", getSign(params, apiKey));
        return params;
    }
    
    /**签名**/
    private String getSign(Map<String,String> params, String apiKey) {
        StringBuffer buf = new StringBuffer();
        Object[] keys = params.keySet().toArray();
        Arrays.sort(keys);
        for (int index = 0; index < keys.length; index ++) {
            String key = (String)keys[index];
            String value = params.get(key);
            if (!"sign".equals(key) && value != null && !value.isEmpty()) {
                buf.append(key).append("=").append(value).append("&");
            }
        }
        buf.append("key="+apiKey);
        String md5 = MD5.encodeUTF8(buf.toString()).toUpperCase();
        return md5;
    }
    
    /***
     * 获取发送数据
     * 
     * @param params
     * @return
     */
    private String getReqXmlData(Map<String,String> params) {
        StringBuffer buf = new StringBuffer();
        buf.append("<xml>");
        Iterator<String> iter = params.keySet().iterator();
        for (;iter.hasNext();) {
            String key = iter.next();
            String value = params.get(key);
            if (value != null && !value.isEmpty()) {
                buf.append("<"+key+">").append(value).append("</"+key+">");
            }
        }
        buf.append("</xml>");
        return buf.toString();
    }
       
    /**
     * 
     * @param orderId 订单号
     * @param openid  OPENID
     * @param re_user_name 真实姓名
     * @param amount 金额，单位分
     * @param desc  描述
     * @param spbill_create_ip IP地址
     * @return
     * @throws Exception
     */
    public MchPayResp pay(String orderId,String openid,String re_user_name,int amount,String desc,String spbill_create_ip) throws Exception {
        
        KeyStore keyStore  = KeyStore.getInstance("PKCS12");
        //FileInputStream instream = new FileInputStream(new File(PKCS12_file));
        InputStream instream = MchPay.class.getClassLoader().getResourceAsStream("cert/apiclient_cert.p12");
        try {
            keyStore.load(instream, mchid.toCharArray());
        } finally {
            instream.close();
        }

        // Trust own CA and all self-signed certs
        SSLContext sslcontext = SSLContexts.custom()
                .loadKeyMaterial(keyStore, mchid.toCharArray())
                .build();
        // Allow TLSv1 protocol only
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                sslcontext,
                new String[] { "TLSv1" },
                null,
                SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
        CloseableHttpClient httpclient = HttpClients.custom()
                .setSSLSocketFactory(sslsf)
                .build();
        try {

            HttpPost httppost = new HttpPost(mchPayUrl);
            Map<String,String> params = this.requestPara(orderId,openid, re_user_name, amount, desc, spbill_create_ip);
            String reqxml = this.getReqXmlData(params);
            StringEntity  reqEntity  = new StringEntity(reqxml, "utf-8");
            // 设置类型 
            reqEntity.setContentType("application/x-www-form-urlencoded"); 
            httppost.setEntity(reqEntity);
            
            CloseableHttpResponse response = httpclient.execute(httppost);
            try {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(entity.getContent(),"UTF-8"));
                    StringBuffer respxml = new StringBuffer();
                    String text;
                    while ((text = bufferedReader.readLine()) != null) {
                        respxml.append(text);
                    }
                    return this.parseRespXmlData(respxml.toString());
                   
                }
                EntityUtils.consume(entity);
            } finally {
                response.close();
            }
        } finally {
            httpclient.close();
        }
        return null;
    }
    
    private MchPayResp parseRespXmlData(String respxml) throws Exception{
        Document doc = DocumentHelper.parseText(respxml);
        Element xmlRoot = doc.getRootElement();
        String return_code = xmlRoot.elementText("return_code");
        String return_msg = xmlRoot.elementText("return_msg");
        MchPayResp resp = new MchPayResp();
        resp.setOk(false);
        resp.setReturn_code(return_code);
        resp.setReturn_msg(return_msg);
        
        if ("SUCCESS".equals(return_code)) {
            String result_code = xmlRoot.elementText("result_code");
            if ("SUCCESS".equals(result_code)) {
                String partner_trade_no = xmlRoot.elementText("partner_trade_no");
                String payment_no = xmlRoot.elementText("payment_no");
                String payment_time = xmlRoot.elementText("payment_time");
                resp.setResult_code(result_code);
                resp.setPartner_trade_no(partner_trade_no);
                resp.setPayment_no(payment_no);
                resp.setPayment_time(payment_time);
                
                resp.setOk(true);
            } else {
                String err_code = xmlRoot.elementText("err_code");
                String err_code_des = xmlRoot.elementText("err_code_des");
                resp.setErr_code(err_code);
                resp.setErr_code_des(err_code_des);
            }
        } 
        return resp;
    }
    
    public static void main(String[] args) throws Exception {
        MchPayResp pay = MchPay.getInstance().pay(String.valueOf(System.currentTimeMillis()),"ovRTywOk2olRh1QRy9bHnna3JOLE", "谭贵源", 354, "测试", "180.167.69.202");
        if (pay == null) {
            System.err.println("Exception");
        } else {
            System.out.println(pay.toString());
        }
        
    }
}
