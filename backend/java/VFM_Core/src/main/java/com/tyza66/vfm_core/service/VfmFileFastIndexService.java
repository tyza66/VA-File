package com.tyza66.vfm_core.service;

import com.tyza66.vfm_core.pojo.VfmFilelist;

import java.util.List;

/**
 * Author: tyza66
 * Date: 2023/7/27 17:34
 * Github: https://github.com/tyza66
 **/

public interface VfmFileFastIndexService {
    //通过文件地址添加快速索引
    boolean addFastIndex(String path);

    //通过索引表进行查询
    List<VfmFilelist> searchByFastIndex(String keyword);
}
