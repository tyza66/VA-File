package com.tyza66.vfm_core.service;

import com.tyza66.vfm_core.pojo.FileAndFolder;

import java.util.List;

/**
 * Author: tyza66
 * Date: 2023/7/23 20:14
 * Github: https://github.com/tyza66
 **/

public interface FileAndFolderSearchService {
    //根据名称搜索
    List<FileAndFolder> searchFileAndFolderByName(String path,String keyword);

    //根据文件内容搜索
    List<FileAndFolder> searchFileAndFolderByContent(String path,String keyword);
}
