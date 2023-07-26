package com.tyza66.vfm_core.controller;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.tyza66.vfm_core.pojo.FileAndFolder;
import com.tyza66.vfm_core.pojo.FileShare;
import com.tyza66.vfm_core.pojo.VfmUser;
import com.tyza66.vfm_core.service.FileShareService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Author: tyza66
 * Date: 2023/7/25 19:21
 * Github: https://github.com/tyza66
 **/

@Api(tags = "文件栈分享模块")
@RestController
@RequestMapping("/share")
public class FileShareController {

    @Autowired
    private FileShareService fileShareService;

    @ApiOperation("分享文件")
    @PostMapping ("/file")
    public JSON shareFile(@RequestBody FileShare fileShare) {
        System.out.println(fileShare);
        JSONObject obj = JSONUtil.createObj();
        if (StpUtil.isLogin()) {
            if (fileShareService.addFileToShareList(((VfmUser) StpUtil.getSession().get("user")).getVfmuUsername(), fileShare.getName(), fileShare.getDownloadUrl())) {
                obj.set("code", 200);
                obj.set("msg", "分享成功");
            } else {
                obj.set("code", 199);
                obj.set("msg", "分享失败");
            }
        } else {
            obj.set("code", 201);
            obj.set("msg", "未登录");
        }
        return obj;
    }

    @ApiOperation("获取所有分享")
    @GetMapping("/all")
    public JSON getAllFileShare() {
        JSONObject obj = JSONUtil.createObj();
        if (StpUtil.isLogin()) {
            obj.set("code", 200);
            obj.set("msg", "获取成功");
            obj.set("data", fileShareService.getAllFileShare());
        } else {
            obj.set("code", 201);
            obj.set("msg", "未登录");
        }
        return obj;
    }
}
