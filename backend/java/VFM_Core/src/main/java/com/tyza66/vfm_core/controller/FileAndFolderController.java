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
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
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
    public JSON checkRoot() {
        JSONObject obj = JSONUtil.createObj();
        if (StpUtil.isLogin()) {
            boolean b = fileAndFolderService.locationIsExists();
            if (b) {
                obj.set("code", 200);
                obj.set("msg", "根路径存在");
            } else {
                obj.set("code", 199);
                obj.set("msg", "根路径不存在");
            }
        } else {
            obj.set("code", 201);
            obj.set("msg", "未登录");
        }
        return obj;
    }

    //传入一个树根下面的路径，并且前缀没有/
    @ApiOperation("获得当前路径下的文件和文件夹")
    @GetMapping("/getIn")
    public JSON getIn(@RequestParam(defaultValue = "") String partPath) {
        JSONObject obj = JSONUtil.createObj();
        if (StpUtil.isLogin()) {
            //获得文件夹树根 并且这个树根后面没有/
            String baseLocation = vfmLocationService.getNowLocation();
            List<FileAndFolder> fileAndFolders = fileAndFolderService.obtainFileAndFolderList(baseLocation + "/" + partPath);
            obj.set("code", 200);
            obj.set("msg", "获取成功");
            obj.set("data", fileAndFolders);
        } else {
            obj.set("code", 201);
            obj.set("msg", "未登录");
        }
        return obj;
    }

    //传入一个树根下面的路径，并且前缀没有/
    //这个操作相对敏感 所以传入值不能为空
    @ApiOperation("删除文件")
    @GetMapping("/deleteFile")
    public JSON deleteFile(@RequestParam(required = true) String partPath) {
        JSONObject obj = JSONUtil.createObj();
        if (StpUtil.isLogin()) {
            //获得文件夹树根 并且这个树根后面没有/
            String baseLocation = vfmLocationService.getNowLocation();
            boolean b = fileAndFolderService.deleteFile(baseLocation + "/" + partPath);
            if (b) {
                obj.set("code", 200);
                obj.set("msg", "删除成功");
            } else {
                obj.set("code", 199);
                obj.set("msg", "删除失败");
            }
        } else {
            obj.set("code", 201);
            obj.set("msg", "未登录");
        }
        return obj;
    }

    //删除文件夹 传入的是基于树根下面的路径
    //这个操作相对敏感 所以传入值不能为空
    @ApiOperation("删除文件夹")
    @GetMapping("/deleteFolder")
    public JSON deleteFolder(@RequestParam(required = true) String partPath) {
        JSONObject obj = JSONUtil.createObj();
        if (StpUtil.isLogin()) {
            //获得文件夹树根 并且这个树根后面没有/
            String baseLocation = vfmLocationService.getNowLocation();
            boolean b = fileAndFolderService.deleteFolder(baseLocation + "/" + partPath);
            if (b) {
                obj.set("code", 200);
                obj.set("msg", "删除成功");
            } else {
                obj.set("code", 199);
                obj.set("msg", "删除失败");
            }
        } else {
            obj.set("code", 201);
            obj.set("msg", "未登录");
        }
        return obj;
    }

    //传入的基于树根以下的路径
    @ApiOperation("新建指定路径文件夹")
    @GetMapping("/createFolder")
    public JSON createFolder(@RequestParam(defaultValue = "") String partPath) {
        JSONObject obj = JSONUtil.createObj();
        if (StpUtil.isLogin()) {
            //获得文件夹树根 并且这个树根后面没有/
            String baseLocation = vfmLocationService.getNowLocation();
            boolean b = fileAndFolderService.createFolder(baseLocation + "/" + partPath);
            if (b) {
                obj.set("code", 200);
                obj.set("msg", "创建成功");
            } else {
                obj.set("code", 199);
                obj.set("msg", "创建失败");
            }
        } else {
            obj.set("code", 201);
            obj.set("msg", "未登录");
        }
        return obj;
    }

    //传入的基于树根以下的路径
    @ApiOperation("创建指定路径文件")
    @GetMapping("/createFile")
    public JSON createFile(@RequestParam(defaultValue = "") String partPath) {
        JSONObject obj = JSONUtil.createObj();
        if (StpUtil.isLogin()) {
            //获得文件夹树根 并且这个树根后面没有/
            String baseLocation = vfmLocationService.getNowLocation();
            boolean b = fileAndFolderService.createFile(baseLocation + "/" + partPath);
            if (b) {
                obj.set("code", 200);
                obj.set("msg", "创建成功");
            } else {
                obj.set("code", 199);
                obj.set("msg", "创建失败");
            }
        } else {
            obj.set("code", 201);
            obj.set("msg", "未登录");
        }
        return obj;
    }

    //传入直接操作的文件夹的关于树根的位置 和文件夹的新名字
    //这个操作相对敏感 所以传入值不能为空
    @ApiOperation("重命名文件夹")
    @GetMapping("/renameFolder")
    public JSON renameFolder(@RequestParam(required = true) String partPath, @RequestParam(required = true) String newName) {
        JSONObject obj = JSONUtil.createObj();
        if (StpUtil.isLogin()) {
            //获得文件夹树根 并且这个树根后面没有/
            String baseLocation = vfmLocationService.getNowLocation();
            boolean b = fileAndFolderService.renameFolder(baseLocation + "/" + partPath, newName);
            if (b) {
                obj.set("code", 200);
                obj.set("msg", "重命名成功");
            } else {
                obj.set("code", 199);
                obj.set("msg", "重命名失败");
            }
        } else {
            obj.set("code", 201);
            obj.set("msg", "未登录");
        }
        return obj;
    }

    //传入直接操作的文件的关于树根的位置 和文件的新名字
    //这个操作相对敏感 所以传入值不能为空
    @ApiOperation("重命名文件")
    @GetMapping("/renameFile")
    public JSON renameFile(@RequestParam(required = true) String partPath, @RequestParam(required = true) String newName) {
        JSONObject obj = JSONUtil.createObj();
        if (StpUtil.isLogin()) {
            //获得文件夹树根 并且这个树根后面没有/
            String baseLocation = vfmLocationService.getNowLocation();
            boolean b = fileAndFolderService.renameFile(baseLocation + "/" + partPath, newName);
            if (b) {
                obj.set("code", 200);
                obj.set("msg", "重命名成功");
            } else {
                obj.set("code", 199);
                obj.set("msg", "重命名失败");
            }
        } else {
            obj.set("code", 201);
            obj.set("msg", "未登录");
        }
        return obj;
    }

    @ApiOperation("下载文件")
    @GetMapping("/download.action")
    public ResponseEntity<byte[]> download(@RequestParam String path) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        String filename = new String(path.getBytes("iso-8859-1"), "utf-8");//为了解决中文名称乱码问题
        String baseLocation = vfmLocationService.getNowLocation();
        File file = new File(baseLocation + "/" + path);
        headers.setContentDispositionFormData("attachment", filename);//不自动打开
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);   //头文件内容类型
        //如果用户未登录 直接返回空文件
        if (StpUtil.isLogin()) {
            return new ResponseEntity<byte[]>("".getBytes(),
                    headers, HttpStatus.CREATED);
        }
        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),
                headers, HttpStatus.CREATED);
    }

    @ApiOperation("上传文件")
    @PostMapping("/upload.action")
    public JSON upload(MultipartFile file, String path) {
        JSONObject obj = JSONUtil.createObj();
        if (StpUtil.isLogin()) {
            if (file != null && !file.isEmpty()) {
                try {
                    String baseLocation = vfmLocationService.getNowLocation();
                    byte[] b = file.getBytes();
                    FileOutputStream out = new FileOutputStream(baseLocation + "/" + path + "/" + file.getOriginalFilename());
                    out.write(b);
                    out.close();
                    obj.set("code", 200);
                    obj.set("msg", "上传成功");
                } catch (IOException e) {
                    e.printStackTrace();
                    obj.set("code", 199);
                    obj.set("msg", "上传失败");
                }
            }
        } else {
            obj.set("code", 201);
            obj.set("msg", "未登录");
        }
        return obj;
    }

    //如果是特定的格式的文件 可以在线预览
    //如果是非特定格式的文件，直接进行下载
    @ApiOperation("在线预览文件")
    @GetMapping("/filesOnline")
    public void downloadFile(@RequestParam String path, HttpServletResponse response) {
        try {
            String baseLocation = vfmLocationService.getNowLocation();
            String filename = baseLocation + "/" + path;
            File file = new File(filename);
            FileInputStream inputStream = new FileInputStream(file);

            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"");
            response.setHeader("Content-Length", String.valueOf(file.length()));

            byte[] buffer = new byte[1024];
            int bytesRead = -1;

            while ((bytesRead = inputStream.read(buffer)) != -1) {
                response.getOutputStream().write(buffer, 0, bytesRead);
            }

            inputStream.close();
            response.getOutputStream().flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
