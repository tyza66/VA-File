package com.tyza66.vfm_core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tyza66.vfm_core.mapper.VfmFilelistMapper;
import com.tyza66.vfm_core.pojo.VfmFilelist;
import com.tyza66.vfm_core.service.FileResolverService;
import com.tyza66.vfm_core.service.VfmFileFastIndexService;
import com.tyza66.vfm_core.util.FileAndFolderUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.nio.channels.FileLock;
import java.util.List;

/**
 * Author: tyza66
 * Date: 2023/7/27 17:35
 * Github: https://github.com/tyza66
 **/

@Service
public class VfmFileFastIndexServiceImpl implements VfmFileFastIndexService {

    @Resource
    VfmFilelistMapper vfmFilelistMapper;

    @Resource
    FileResolverService fileResolverService;

    @Override
    public boolean addFastIndex(String path) {
        if (!FileAndFolderUtil.fileExists(path)) {
            return false;
        }
        //获得总价
        String summarize = fileResolverService.summarize(path);
        //创建File对象
        File file = new File(path);
        //创建文件列表信息
        VfmFilelist vfmFilelist = new VfmFilelist();
        vfmFilelist.setId(0);
        vfmFilelist.setName(file.getName().substring(0, file.getName().lastIndexOf(".")));
        vfmFilelist.setEnd(file.getName().substring(file.getName().lastIndexOf(".") + 1));
        vfmFilelist.setPrototype(file.getAbsolutePath());
        vfmFilelist.setAbout(summarize);
        vfmFilelist.setSize(String.valueOf(file.length()));
        //插入数据库
        int insert = vfmFilelistMapper.insert(vfmFilelist);
        return insert > 0;
    }

    @Override
    public List<VfmFilelist> searchByFastIndex(String keyword) {
        QueryWrapper<VfmFilelist> vfmFilelistQueryWrapper = new QueryWrapper<>();
        vfmFilelistQueryWrapper.like("name", keyword).or().like("about", keyword);
        return vfmFilelistMapper.selectList(vfmFilelistQueryWrapper);
    }
}
