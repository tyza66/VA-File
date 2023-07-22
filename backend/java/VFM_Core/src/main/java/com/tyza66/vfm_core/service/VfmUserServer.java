package com.tyza66.vfm_core.service;

import com.tyza66.vfm_core.pojo.VfmUser;

/**
 * Author: tyza66
 * Date: 2023/7/22 15:24
 * Github: https://github.com/tyza66
 **/

public interface VfmUserServer {

    VfmUser login(String username, String password);
}
