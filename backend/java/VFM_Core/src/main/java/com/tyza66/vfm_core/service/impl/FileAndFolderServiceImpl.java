package com.tyza66.vfm_core.service.impl;

import com.tyza66.vfm_core.pojo.FileAndFolder;
import com.tyza66.vfm_core.pojo.VfmUser;
import com.tyza66.vfm_core.service.FileAndFolderService;
import com.tyza66.vfm_core.util.FileAndFolderUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Author: tyza66
 * Date: 2023/7/22 20:35
 * Github: https://github.com/tyza66
 **/

@Service
public class FileAndFolderServiceImpl implements FileAndFolderService {
    @Resource
    private FileAndFolderUtil fileAndFolderUtil;

    @Resource
    private VfmLocationServiceImpl vfmLocationService;

    public Boolean locationIsExists() {
        return FileAndFolderUtil.folderExists(vfmLocationService.getNowLocation());
    }

    @Override
    public Boolean setLocation(String location, VfmUser vfmUser) {
        return  vfmLocationService.setNowLocation(location,vfmUser);
    }

    @Override
    public List<FileAndFolder> obtainFileAndFolderList(String path) {
        //处理这个文件地址，去掉后缀的/
        if(path.endsWith("/")){
            path = path.substring(0,path.length()-1);
        }
        return FileAndFolderUtil.getFolderContent(path);
    }

    @Override
    public boolean deleteFile(String path) {
        return FileAndFolderUtil.deleteFile(path);
    }

    @Override
    public boolean deleteFolder(String path) {
        return FileAndFolderUtil.deleteFolder(path);
    }

    @Override
    public boolean createFolder(String path) {
        return FileAndFolderUtil.createFolder(path);
    }

    @Override
    public boolean createFile(String path) {
        return FileAndFolderUtil.createFile(path);
    }

    @Override
    public boolean renameFolder(String oldPath, String newName) {
        return FileAndFolderUtil.renameFolder(oldPath,newName);
    }

    @Override
    public boolean renameFile(String oldPath, String newName) {
        return FileAndFolderUtil.renameFile(oldPath,newName);
    }


}
