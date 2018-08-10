package com.zhiyou.duobao.utils.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.zhiyou.duobao.utils.config.WebConfig;

public class UrlTag extends TagSupport {
    private static final long serialVersionUID = -5418987284914788849L;
    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
    
    public int doStartTag() {
        return SKIP_BODY;
    }

    @Override
    public int doEndTag() throws JspException {
        try {
            String version = WebConfig.getValue("version");
            String url = value;
            if (url.indexOf("?") > -1) {
                url = url +"&v="+version;
            } else {
                url = url +"?v="+version; 
            }
            boolean isRelativePath = true;
            if (url.indexOf("http") > -1 || url.indexOf("https") > -1) {
                isRelativePath = false;
            }
            if (isRelativePath) {
                url = pageContext.getServletContext().getContextPath() + url;
            }
            pageContext.getOut().write(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return EVAL_PAGE;
    }
}
