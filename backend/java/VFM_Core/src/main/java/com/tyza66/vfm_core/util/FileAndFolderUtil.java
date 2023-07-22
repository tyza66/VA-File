package com.tyza66.vfm_core.util;

import ch.qos.logback.classic.spi.EventArgUtil;
import com.tyza66.vfm_core.pojo.FileAndFolder;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: tyza66
 * Date: 2023/7/22 19:13
 * Github: https://github.com/tyza66
 **/

@Component
@Scope(ConfigurableListableBeanFactory.SCOPE_SINGLETON)
public class FileAndFolderUtil {
//成员变量


//静态方法

    //获得文件名
    public static String getFileName(String path) {
        return path.substring(path.lastIndexOf("/") + 1);
    }

    //获得文件所在目录的路径
    public static String getFolderName(String path) {
        return path.substring(0, path.lastIndexOf("/"));
    }

    //获得文件所在路径的上level级路径
    public static String getFolderName(String path, int level) {
        //先使用/分割路径
        String[] split = path.split("/");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < split.length - level; i++) {
            sb.append(split[i]).append("/");
        }
        return sb.toString();
    }

    //判断地址指向的位置是否是文件夹，并判断这个文件夹是存在
    //这个方法用在用户初打开工具的时候，还有手动更改文件树根位置的时候判断是否存在和是不是文件夹
    public static boolean folderExists(String path) {
        //检查传入的值是不是空
        if (path == null || path.equals("")) {
            return false;
        }
        //创建File对象
        File file = new File(path);
        //存在且是文件夹
        if (file.exists() && file.isDirectory()) {
            return true;
        }
        return false;
    }

    //判断文件是否存在
    //这个方法用在将打开文件或者打开文件遇到问题的时候 判断是否是因为文件因某些原因出现不存在的现象 好及时做出反馈
    public static boolean fileExists(String path) {
        //检查传入的值是不是空
        if (path == null || path.equals("")) {
            return false;
        }
        //创建File对象
        File file = new File(path);
        //存在且是文件
        if (file.exists() && file.isFile()) {
            return true;
        }
        return false;
    }

    //获取当前文件夹中的所有文件和文件夹
    public List<FileAndFolder> getFolderContent(String path) {
        List<FileAndFolder> fileAndFolders = new ArrayList<>();
        if(folderExists(path)){
            return fileAndFolders;
        }
        File file = new File(path);
        File[] files = file.listFiles();
        for (File fileIn : files) {
            String name = fileIn.getName();
            String end = name.substring(name.lastIndexOf(".") + 1);
            name = name.substring(0, name.lastIndexOf("."));
            String size = String.valueOf(fileIn.length());
            String type = fileIn.isFile() ? "file" : "folder";
            FileAndFolder fileAndFolder = new FileAndFolder(name,end,size,type,fileIn);
            fileAndFolders.add(fileAndFolder);
        }
        return fileAndFolders;
    }

//非静态方法


}
