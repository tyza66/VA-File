package com.tyza66.vfm_core.service.impl;

import com.tyza66.vfm_core.pojo.FileAndFolder;
import com.tyza66.vfm_core.service.FileAndFolderSearchService;
import com.tyza66.vfm_core.service.FileAndFolderService;
import com.tyza66.vfm_core.util.FileAndFolderUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: tyza66
 * Date: 2023/7/23 20:15
 * Github: https://github.com/tyza66
 **/

@Service
public class FileAndFolderSearchServiceImpl implements FileAndFolderSearchService {

    @Override
    public List<FileAndFolder> searchFileAndFolderByName(String path, String keyword) {
        return FileAndFolderUtil.getFolderContent(path,keyword,new ArrayList<>());
    }
}
