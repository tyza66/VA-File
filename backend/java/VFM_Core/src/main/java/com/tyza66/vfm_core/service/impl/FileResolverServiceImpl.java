package com.tyza66.vfm_core.service.impl;

import com.tyza66.vfm_core.service.FileResolverService;
import com.tyza66.vfm_core.util.FileAndFolderUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * Author: tyza66
 * Date: 2023/7/27 8:49
 * Github: https://github.com/tyza66
 **/

@Service
public class FileResolverServiceImpl implements FileResolverService {

    @Resource
    RestTemplate restTemplate;

    @Override
    public boolean pdf2word(String path) {
        if(!FileAndFolderUtil.fileExists(path)){
            return false;
        }
        String forObject = restTemplate.getForObject("http://localhost:9093/pdf2docx?path=" + path, String.class);
        if (forObject != null && forObject.contains("true")) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean word2pdf(String path) {
        if(!FileAndFolderUtil.fileExists(path)){
            return false;
        }
        String forObject = restTemplate.getForObject("http://localhost:9093/docx2pdf?path=" + path, String.class);
        if (forObject != null && forObject.contains("true")) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String image2text(String path) {
        String forObject = restTemplate.getForObject("http://localhost:9093/img2text?path=" + path, String.class);
        if(forObject!=null) {
            return forObject;
        }else{
            return "";
        }
    }
}
