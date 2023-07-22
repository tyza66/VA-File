package com.tyza66.vfm_core.service;

import com.tyza66.vfm_core.pojo.VfmUser;

/**
 * Author: tyza66
 * Date: 2023/7/22 14:56
 * Github: https://github.com/tyza66
 **/

public interface VfmLocationService {
    //获取当前的根文件夹位置
    String getNowLocation();

    //设置当前文件夹位置
    Boolean setNowLocation(String location, VfmUser vfmUser);
}
