package com.zhiyou.weixin;

import java.util.regex.Pattern;

import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSONObject;

public class WxOauth {
    
    public static final String appid = "wx378b6fc928d3e4fa";
    public static final String secret = "dbc8e4568898b4384cc4375e0db3067e";

    public static String httpGet(String url) throws Exception {
        //设置请求和传输超时时间
        RequestConfig config = RequestConfig.custom()
                .setSocketTimeout(6000).setConnectTimeout(6000).build();
        HttpGet request = new HttpGet(url);
        request.setConfig(config);
        CloseableHttpClient client = HttpClientBuilder.create().build();
        CloseableHttpResponse response = client.execute(request);
        
        // 判断网络连接状态码是否正常(0--200都数正常)
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            return EntityUtils.toString(response.getEntity(),"utf-8");
        } 
        return null;
    }
    
    /** 用户同意授权，获取code */
    public static String getWeixinURL(String appid,String url) {
        String scope = "snsapi_userinfo";
//        scope = "snsapi_base";
        String oauth2URL = "https://open.weixin.qq.com/connect/oauth2/authorize?appid="+appid+"&redirect_uri="+url+"&response_type=code&scope="+scope+"&state=STATE#wechat_redirect";
        System.out.println(oauth2URL);
        return oauth2URL;
    }
    
    /** 通过code换取网页授权access_token */
    public static JSONObject getAccessToken(String appid,String secret,String code){
        String accessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="+ appid +"&secret="+ secret +"&code="+ code +"&grant_type=authorization_code";
        try {
            String text = httpGet(accessTokenUrl);
            return JSONObject.parseObject(text);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    /** 刷新access_token（如果需要）*/
    public static JSONObject refreshAccessToken(String appid,String refreshToken){
        String accessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/refresh_token?appid="+appid+"&grant_type=refresh_token&refresh_token="+refreshToken;
        try {
            String text = httpGet(accessTokenUrl);
            return JSONObject.parseObject(text);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static String filterEmoji(String input) {
        if (input == null) {
            return "";
        }
        Pattern emoji = Pattern.compile ("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]",
        Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE );
        return emoji.matcher(input).replaceAll("");
    }
    
    /**拉取用户信息(需scope为 snsapi_userinfo)*/
    public static JSONObject getSnsapiUserinfo(String accessToken,String openId) {
        String snsapiUserinfoUrl = "https://api.weixin.qq.com/sns/userinfo?access_token="+accessToken+"&openid="+openId+"&lang=zh_CN";
        try {
            String text = httpGet(snsapiUserinfoUrl);
            return JSONObject.parseObject(text);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    /**检验授权凭证（access_token）是否有效*/
    public static boolean verify(String accessToken,String openid) {
        String url = "https://api.weixin.qq.com/sns/auth?access_token="+accessToken+"&openid="+openid;
        try {
            String text = httpGet(url);
            JSONObject json = JSONObject.parseObject(text);
            int code = json.getIntValue("errcode");
            return (code == 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public static void main(String[] args) {
        getWeixinURL(WxOauth.appid, "http://wx.sunyoo51.com/weixin/wxoauth");
    }
}
