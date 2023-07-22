package com.tyza66.vfm_core.controller;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.tyza66.vfm_core.pojo.FileAndFolder;
import com.tyza66.vfm_core.service.impl.FileAndFolderServiceImpl;
import com.tyza66.vfm_core.service.impl.VfmLocationServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import jdk.nashorn.internal.ir.CallNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Author: tyza66
 * Date: 2023/7/22 21:18
 * Github: https://github.com/tyza66
 **/

@Api(tags = "文件和文件夹管理模块")
@RestController
@RequestMapping("/file")
public class FileAndFolderController {

    @Autowired
    private VfmLocationServiceImpl vfmLocationService;

    @Autowired
    private FileAndFolderServiceImpl fileAndFolderService;

    @ApiOperation("检查根路径是否存在")
    @GetMapping("/checkRoot")
    public JSON checkRoot(){
        JSONObject obj = JSONUtil.createObj();
        if(StpUtil.isLogin()){
            boolean b = fileAndFolderService.locationIsExists();
            if(b){
                obj.set("code",200);
                obj.set("msg","根路径存在");
            }else{
                obj.set("code",199);
                obj.set("msg","根路径不存在");
            }
        }else {
            obj.set("code",201);
            obj.set("msg","未登录");
        }
        return obj;
    }

    //传入一个树根下面的路径，并且前缀没有/
    @ApiOperation("获得当前路径下的文件和文件夹")
    @GetMapping("/getIn")
    public JSON getIn(@RequestParam(defaultValue = "") String partPath){
        JSONObject obj = JSONUtil.createObj();
        if(StpUtil.isLogin()){
            //获得文件夹树根 并且这个树根后面没有/
            String baseLocation = vfmLocationService.getNowLocation();
            List<FileAndFolder> fileAndFolders = fileAndFolderService.obtainFileAndFolderList(baseLocation + "/" + partPath);
            obj.set("code",200);
            obj.set("msg","获取成功");
            obj.set("data",fileAndFolders);
        }else {
            obj.set("code",201);
            obj.set("msg","未登录");
        }
        return obj;
    }
}
