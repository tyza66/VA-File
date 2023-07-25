package com.tyza66.vfm_core.service;

import com.tyza66.vfm_core.pojo.FileShare;
import com.tyza66.vfm_core.pojo.VfmUser;

/**
 * Author: tyza66
 * Date: 2023/7/25 16:53
 * Github: https://github.com/tyza66
 **/

public interface FileShareService {

    boolean addFileToShareList(String shareMan,String user, String link);
}
