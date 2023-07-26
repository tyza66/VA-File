package com.tyza66.vfm_core.service;

import com.tyza66.vfm_core.pojo.FileShare;
import com.tyza66.vfm_core.pojo.VfmUser;

import java.util.List;

/**
 * Author: tyza66
 * Date: 2023/7/25 16:53
 * Github: https://github.com/tyza66
 **/

public interface FileShareService {

    //添加文件分享
    boolean addFileToShareList(String shareMan,String user, String link);

    //获得所有文件分享
    List<FileShare> getAllFileShare();

}
