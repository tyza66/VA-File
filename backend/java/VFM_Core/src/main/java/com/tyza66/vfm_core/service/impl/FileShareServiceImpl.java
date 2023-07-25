package com.tyza66.vfm_core.service.impl;

import com.tyza66.vfm_core.pojo.FileShare;
import com.tyza66.vfm_core.pojo.VfmUser;
import com.tyza66.vfm_core.service.FileShareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
}
