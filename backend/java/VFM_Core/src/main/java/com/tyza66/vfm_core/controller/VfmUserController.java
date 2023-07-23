package com.tyza66.vfm_core.controller;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.tyza66.vfm_core.pojo.VfmUser;
import com.tyza66.vfm_core.service.impl.VfmUserServerImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Author: tyza66
 * Date: 2023/7/22 15:26
 * Github: https://github.com/tyza66
 **/

@Api(tags = "用户管理模块")
@RestController
@RequestMapping("/user")
public class VfmUserController {

    @Autowired
    private VfmUserServerImpl vfmUserServer;

    @ApiOperation("用户登录")
    @PostMapping("/login")
    public JSON login(@RequestBody VfmUser user) {
        JSONObject obj = JSONUtil.createObj();
        VfmUser login = vfmUserServer.login(user.getVfmuUsername(), user.getVfmuPassword());
        if (login == null) {
            obj.set("code", 199);
            obj.set("msg", "登录失败");
        } else {
            StpUtil.login(0);
            //将保存信息中的秘密替换
            login.setVfmuPassword("000000");
            StpUtil.getSession().set("user", login);
            obj.set("code", 200);
            obj.set("msg", "登录成功");
            obj.set("token", StpUtil.getTokenValue());
        }
        return obj;
    }

    @ApiOperation("退出登录")
    @GetMapping("/logout")
    public JSON logout() {
        JSONObject obj = JSONUtil.createObj();
        if (StpUtil.isLogin()) {
            StpUtil.logout();
        }
        obj.set("code", 200);
        obj.set("msg", "退出成功");
        return obj;
    }

    @ApiOperation("用户注册")
    @PostMapping("/register")
    public JSON register(@RequestBody VfmUser user) {
        JSONObject obj = JSONUtil.createObj();
        if(StpUtil.isLogin()){
            Boolean user1 = vfmUserServer.register(user.getVfmuUsername(), user.getVfmuPassword(), (VfmUser) StpUtil.getSession().get("user"));
            if (user1){
                obj.set("code", 200);
                obj.set("msg", "注册成功");
            }else{
                obj.set("code", 199);
                obj.set("msg", "注册失败");
            }
        }else{
            obj.set("code", 201);
            obj.set("msg", "请先登录");
        }
        return obj;
    }

    //检查当前登陆状态 并且将当前用户信息返回
    @ApiOperation("检查当前登陆状态")
    @GetMapping("/checkLogin")
    public JSON checkLogin() {
        JSONObject obj = JSONUtil.createObj();
        if (StpUtil.isLogin()) {
            obj.set("code", 200);
            obj.set("msg", "已登录");
            obj.set("user", StpUtil.getSession().get("user"));
            obj.set("token", StpUtil.getTokenValue());
        } else {
            obj.set("code", 199);
            obj.set("msg", "未登录");
        }
        return obj;
    }

}
