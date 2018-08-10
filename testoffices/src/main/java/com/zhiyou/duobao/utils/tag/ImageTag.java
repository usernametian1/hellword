package com.zhiyou.duobao.utils.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.zhiyou.duobao.utils.config.WebConfig;

public class ImageTag extends TagSupport {

    private static final long serialVersionUID = 5757584135715119120L;
    
    private String url;
    private String type;
    
    public int doStartTag() {
        return SKIP_BODY;
    }

    @Override
    public int doEndTag() throws JspException {
        try {
            pageContext.getOut().write(WebConfig.getImageVisitPath()+url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return EVAL_PAGE;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    

}
