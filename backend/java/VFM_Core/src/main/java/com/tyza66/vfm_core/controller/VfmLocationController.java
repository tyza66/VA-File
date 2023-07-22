package com.tyza66.vfm_core.controller;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.tyza66.vfm_core.service.impl.VfmLocationServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Author: tyza66
 * Date: 2023/7/22 15:02
 * Github: https://github.com/tyza66
 **/

@Api(tags = "文件基本位置管理模块")
@RestController
@RequestMapping("/location")
public class VfmLocationController {

    @Autowired
    private VfmLocationServiceImpl vfmLocationService;

    @ApiOperation(value = "获取当前文件基本位置")
    @GetMapping("/get")
    public JSON getNowLocation() {
        JSONObject obj = JSONUtil.createObj();
        if (StpUtil.isLogin()) {
            String nowLocation = vfmLocationService.getNowLocation();
            if (nowLocation == null) {
                obj.set("code", 199);
                obj.set("msg", "获取失败");
            } else {
                obj.set("code", 200);
                obj.set("msg", "获取成功");
                obj.set("data", nowLocation);
            }
        } else {
            obj.set("code", 201);
            obj.set("msg", "未登录");
        }
        return obj;
    }

}
