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

//因为这个类对象中会声明公用的输入输出流 并设计悲观锁操作
//所以虽然内部静态方法居多 还是需要注册Bean
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
    public static List<FileAndFolder> getFolderContent(String path) {
        List<FileAndFolder> fileAndFolders = new ArrayList<>();
        //如果文件夹不存在，直接返回空的list
        if (!folderExists(path)) {
            return fileAndFolders;
        }
        File file = new File(path);
        File[] files = file.listFiles();
        for (File fileIn : files) {
            String name = fileIn.getName();
            String end = "";
            String size = "0";
            if (fileIn.isFile()) {
                end = name.substring(name.lastIndexOf(".") + 1);
                name = name.substring(0, name.lastIndexOf("."));
                size = String.valueOf(fileIn.length());
            }
            String type = fileIn.isFile() ? "file" : "folder";
            FileAndFolder fileAndFolder = new FileAndFolder(name, end, size, type, fileIn);
            fileAndFolders.add(fileAndFolder);
        }
        return fileAndFolders;
    }

    //删除指定路径的文件 返回值是知否成功的布尔值
    public static boolean deleteFile(String path) {
        //检查传入的文件是否存在
        if (!fileExists(path)) {
            return false;
        }
        //创建File对象
        File file = new File(path);
        //删除文件
        return file.delete();
    }

    //使用递归的方式删除文件夹
    public static boolean deleteFolder(String path) {
        //检查传入的文件夹是否存在
        if (!folderExists(path)) {
            return false;
        }
        //创建File对象
        File file = new File(path);
        //获得文件夹下的所有文件和文件夹
        File[] files = file.listFiles();
        //遍历文件夹下的所有文件和文件夹
        for (File fileIn : files) {
            //如果是文件夹，递归删除
            if (fileIn.isDirectory()) {
                deleteFolder(fileIn.getAbsolutePath());
            } else {
                //如果是文件，直接删除
                fileIn.delete();
            }
        }
        //删除文件夹
        return file.delete();
    }

    //通过路径创建文件夹
    public static boolean createFolder(String path){
        //首先判断文件夹本身是否存在 如果已经存在就直接返回false
        if(folderExists(path)){
            return false;
        }
        //之后创建File对象
        File file = new File(path);
        //创建文件夹
        return file.mkdirs();
    }


//非静态方法


}
