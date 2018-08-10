package com.zhiyou.duobao.utils.service;

import java.io.File;
import java.io.IOException;
import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;

import com.zhiyou.duobao.utils.config.WebConfig;

public abstract class BService implements IService {

    private static final long serialVersionUID = -6312119679592648756L;
    
    protected final Logger log = Logger.getLogger(this.getClass());
    
    /**
     * 文件上传
     * @param multipartFile
     * @param filrUrl
     */
    protected void doFileUpload(MultipartFile multipartFile,String filrUrl){
        try {
            File destFile = new File(WebConfig.getImageUploadDir(), filrUrl);
            if (!destFile.isDirectory()) {
                // 如果目录不存在，则创建目录
                destFile.mkdirs();
            }
            multipartFile.transferTo(destFile);
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
