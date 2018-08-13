package com.zhiyou.weixin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;

@SuppressWarnings("serial")
public class OAuthServlet extends javax.servlet.http.HttpServlet{

    
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
        
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String code = req.getParameter("code");
        JSONObject result = WxOauth.getAccessToken(WxOauth.appid, WxOauth.secret, code);
        resp.getWriter().write(result.toJSONString());
        resp.getWriter().flush();
    }
    
}
