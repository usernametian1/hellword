package com.zhiyou.common.constance;



/**
 * 配置常量类，主要配置有支付方式、短信通道、快递100、邮箱等等
 *                       
 *
 */
public class EjavashopConfig {


    /**
     * 用户每日最多获取的短信验证码数量
     */
    public static final int    SMS_MAX_GIVEN_NUM         = 5;

    public static final String SMS_URL                   = "http://sz.ipyy.com/smsJson.aspx";
    public static final String SMS_VERIFY_CODE           = "你的验证码是： @，请及时填写你的验证码！【尚游网络】";
    public static final String SMS_ACCOUNT				="szzd00272";
    public static final String SMS_PASSWORD				="szzd0027233";//密钥
    
    public static final Integer TRACKING_TYPE_1			  = 1;
    
}
