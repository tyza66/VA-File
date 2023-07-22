package com.tyza66.vfm_core.service.impl;

import com.tyza66.vfm_core.service.FileAndFolderService;
import com.tyza66.vfm_core.util.FileAndFolderUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Author: tyza66
 * Date: 2023/7/22 20:35
 * Github: https://github.com/tyza66
 **/

@Service
public class FileAndFolderServiceImpl implements FileAndFolderService {
    @Resource
    private FileAndFolderUtil fileAndFolderUtil;
}
