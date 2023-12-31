package com.tyza66.vfm_core.service;

import com.tyza66.vfm_core.pojo.FileAndFolder;
import com.tyza66.vfm_core.pojo.VfmUser;

import java.util.List;

/**
 * Author: tyza66
 * Date: 2023/7/22 20:35
 * Github: https://github.com/tyza66
 **/

public interface FileAndFolderService {
    //判断当前文件树根位置是否存在
    Boolean locationIsExists();

    //修改当前文件树根位置
    Boolean setLocation(String location, VfmUser vfmUser);

    //获得文件夹下的文件和文件夹列表
    List<FileAndFolder> obtainFileAndFolderList(String path);

    //删除指定的文件
    boolean deleteFile(String path);

    //删除指定的文件夹
    boolean deleteFolder(String path);

    //创建路径指定的文件夹
    boolean createFolder(String path);

    //创建指定路径的文件的方法
    boolean createFile(String path);

    //重命名文件夹
    boolean renameFolder(String oldPath,String newName);

    //重命名文件
    boolean renameFile(String oldPath,String newName);

}
