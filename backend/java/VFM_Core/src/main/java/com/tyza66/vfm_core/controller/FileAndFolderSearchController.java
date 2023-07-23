package com.tyza66.vfm_core.controller;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.tyza66.vfm_core.pojo.FileAndFolder;
import com.tyza66.vfm_core.service.FileAndFolderSearchService;
import com.tyza66.vfm_core.service.VfmLocationService;
import com.tyza66.vfm_core.service.impl.FileAndFolderSearchServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Author: tyza66
 * Date: 2023/7/23 20:23
 * Github: https://github.com/tyza66
 **/

@Api(tags = "文件和文件夹搜索")
@RestController
@RequestMapping("/search")
public class FileAndFolderSearchController {

    @Autowired
    private VfmLocationService vfmLocationService;

    @Autowired
    private FileAndFolderSearchService fileAndFolderSearchService;

    @ApiOperation("按名称搜索文件和文件夹")
    @GetMapping("/byName")
    public JSON searchByName(@RequestParam String name) {
        JSONObject obj = JSONUtil.createObj();
        if (StpUtil.isLogin()){
            String nowLocation = vfmLocationService.getNowLocation();
            if (nowLocation == null) {
                obj.set("code", 199);
                obj.set("msg", "获取失败");
            } else {
                List<FileAndFolder> fileAndFolders = fileAndFolderSearchService.searchFileAndFolderByName(nowLocation, name);
                obj.set("code", 200);
                obj.set("msg", "获取成功");
                obj.set("data", fileAndFolders);
            }
        }else{
            obj.set("code", 201);
            obj.set("msg", "未登录");
        }
        return obj;
    }

}
