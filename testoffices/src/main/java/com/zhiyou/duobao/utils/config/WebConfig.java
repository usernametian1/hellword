package com.zhiyou.duobao.utils.config;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class WebConfig {
    
    private static Map<String,String> config = new HashMap<String,String>();
    static {
        init();
    }
    
    @SuppressWarnings("unchecked")
    protected static void init(){
        InputStream in = null;
        BufferedReader br = null;
        
        Document doc = null;
        try {
            StringBuffer buf = new StringBuffer();
            in = WebConfig.class.getClassLoader().getResourceAsStream("web-config.xml");
            br = new BufferedReader(new InputStreamReader(in));
            
            String line = null;
            while((line = br.readLine()) != null) {
                buf.append(line);
            }
            doc = DocumentHelper.parseText(buf.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (br != null) {
                try {
                    br.close();
                } catch (Exception e2) {
                }
                
            }
            if (in != null) {
                try {
                    in.close();
                } catch (Exception e2) {
                }
            }
        }
        Element root = doc.getRootElement();
        List<Element> list = root.elements("item");
        for (Element item: list) {
            config.put(item.attributeValue("name"), item.attributeValue("value"));
        }
    }
    
    /***
     * @return
     */
    public static String getValue(String key) {
        return config.get(key);
    }
    
    /***
     * 图片上传地址
     * 
     * @return
     */
    public static String getImageUploadDir() {
        return config.get("ImageUploadDir");
    }

    /**
     * 图片访问地址
     * 
     * @return
     */
    public static String getImageVisitPath() {
        return config.get("ImageVisitPath");
    }
    
    /**是否本地运行*/
//    public static boolean isLocalOperate() {
//        String mode = config.get("local_operate").trim();
//        if (mode == null || "".equals(mode)) {
//            return false;
//        }
//        return Boolean.parseBoolean(mode);
//    }
}
