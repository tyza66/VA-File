package com.tyza66.vfm_core.service.impl;

import com.tyza66.vfm_core.pojo.FileShare;
import com.tyza66.vfm_core.pojo.VfmUser;
import com.tyza66.vfm_core.service.FileShareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Author: tyza66
 * Date: 2023/7/25 16:53
 * Github: https://github.com/tyza66
 **/

@Service
public class FileShareServiceImpl implements FileShareService {
    @Resource
    RedisTemplate redisTemplate;

    @Override
    public boolean addFileToShareList(String shareMan,String user, String link) {
        String value = (String) redisTemplate.opsForValue().get("fileShare");
        if (value == null) {
            value = "";
        }
        String putString = value + "|||" +"{shareMan="+shareMan+",name="+user+",file="+link+"}";
        redisTemplate.opsForValue().set("fileShare", putString,1, TimeUnit.DAYS);
        return true;
    }

    @Override
    public List<FileShare> getAllFileShare() {
        String value = (String) redisTemplate.opsForValue().get("fileShare");
        ArrayList<FileShare> fileShares = new ArrayList<>();
        String[] split = value.split("\\|\\|\\|");
        //System.out.println(split.length);
        for(int i=1;i<split.length;i++){
            FileShare fileShare = new FileShare(split[i].split(",")[0].split("shareMan=")[1], split[i].split(",")[1].split("name=")[1], split[i].split(",")[2].split("file=")[1].substring(0,split[i].split(",")[2].split("file=")[1].length()-1));
            fileShares.add(fileShare);
        }
        return fileShares;
    }
}
