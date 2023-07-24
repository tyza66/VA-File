package com.tyza66.vfm_core.service;

import com.tyza66.vfm_core.pojo.VfmUser;

/**
 * Author: tyza66
 * Date: 2023/7/22 15:24
 * Github: https://github.com/tyza66
 **/

public interface VfmUserServer {

    //使用账号密码验证登录
    VfmUser login(String username, String password);

    //已经登录的用户通过设置添加其他用户
    Boolean register(String username,String password,VfmUser vfmUser);

    //修改用户密码
    Boolean changePassword(String username,String newPassword);
}
