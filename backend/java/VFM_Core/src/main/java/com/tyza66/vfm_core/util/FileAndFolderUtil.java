package com.tyza66.vfm_core.util;

import ch.qos.logback.classic.spi.EventArgUtil;
import com.tyza66.vfm_core.pojo.FileAndFolder;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import java.io.*;
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
@Slf4j
public class FileAndFolderUtil {
    //成员变量
    //公用输出流
    FileOutputStream fileOutputStream;

    //公用输入流
    FileInputStream fileInputStream;

    @Autowired
    private RestTemplate restTemplate;

//静态方法

    //获得文件名
    public static String getFileName(String path) {
        return path.substring(path.lastIndexOf("/") + 1);
    }

    //获得文件或文件夹所在目录的路径
    public static String getFolderName(String path) {
        return path.substring(0, path.lastIndexOf("/"));
    }

    //获得文件所在路径或文件夹的上level级路径
    public static String getFolderName(String path, int level) {
        //先使用/分割路径
        String[] split = path.split("/");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < split.length - level; i++) {
            sb.append(split[i]).append("/");
        }
        return sb.toString();
    }

    //获得文件后缀名
    public static String getFileSuffix(String path) {
        return path.substring(path.lastIndexOf(".") + 1);
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
                if (name.contains(".")) {
                    name = name.substring(0, name.lastIndexOf("."));
                }
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
    public static boolean createFolder(String path) {
        //首先判断文件夹本身是否存在 如果已经存在就直接返回false
        if (folderExists(path)) {
            return false;
        }
        //之后创建File对象
        File file = new File(path);
        //创建文件夹
        return file.mkdirs();
    }

    //通过完整路径（带拓展名）创建文件
    public static boolean createFile(String path) {
        //首先判断文件是否存在 如果已经存在就直接返回false
        if (fileExists(path)) {
            return false;
        }
        //之后创建File对象
        File file = new File(path);
        //创建文件
        try {
            return file.createNewFile();
        } catch (IOException e) {
            log.info("创建文件过程中失败");
            return false;
        }
    }

    //通过旧文件夹路径和新文件名称修改文件夹名称
    public static boolean renameFolder(String path, String newName) {
        //首先判断文件夹是否存在 如果不存在就直接返回false
        if (!folderExists(path)) {
            return false;
        }
        //计算旧文件夹的所在路径地址
        String folderName = getFolderName(path, 1);
        //计算新文件夹的完整路径地址
        String newPath = folderName + "/" + newName;
        //创建File对象
        File oldFile = new File(path);
        File newFile = new File(newPath);
        return oldFile.renameTo(newFile);
    }

    //通过旧文件路径和新文件名修改文件名
    public static boolean renameFile(String path, String newName) {
        //首先判断文件是否存在 如果不存在就直接返回false
        if (!fileExists(path)) {
            return false;
        }
        //计算旧文件的所在路径地址
        String folderName = getFolderName(path);
        //计算新文件的完整路径地址
        String newPath = folderName + "/" + newName;
        //创建File对象
        File oldFile = new File(path);
        File newFile = new File(newPath);
        return oldFile.renameTo(newFile);
    }

    //通过关键字搜索文件夹中名称包含关键字的文件和文件夹
    public static List<FileAndFolder> getFolderContent(String path, String key, List<FileAndFolder> fileAndFolders) {
        //如果文件夹不存在，直接返回空的list
        if (!folderExists(path)) {
            return fileAndFolders;
        }
        File file = new File(path);
        File[] files = file.listFiles();
        for (File fileIn : files) {
            //进行保存操作之前先判断当前文件或文件夹的名称中是否包含关键字
            //有关键字的话才可以保存进去
            if (fileIn.getName().contains(key)) {
                String name = fileIn.getName();
                String end = "";
                String size = "0";
                if (fileIn.isFile()) {
                    end = name.substring(name.lastIndexOf(".") + 1);
                    if (name.contains(".")) {
                        name = name.substring(0, name.lastIndexOf("."));
                    }
                    size = String.valueOf(fileIn.length());
                }
                String type = fileIn.isFile() ? "file" : "folder";
                FileAndFolder fileAndFolder = new FileAndFolder(name, end, size, type, fileIn);
                fileAndFolders.add(fileAndFolder);
            }
            //如果是文件夹 就进入深层
            if (fileIn.isDirectory()) {
                getFolderContent(fileIn.getAbsolutePath(), key, fileAndFolders);
            }
        }
        return fileAndFolders;
    }

//非静态方法

    //使用py后端MIND1进行判断文件内容
    public boolean checkIfFileInclude(String path, String key) {
        //如果文件夹不存在，直接返回false
        if (!fileExists(path)) {
            return false;
        }
        boolean end = false;
        String fileName = getFileName(path);
        String type = fileName.substring(fileName.lastIndexOf(".") + 1);
        //检查txt文件内容
        if (type.equals("txt") || type.equals("java") || type.equals("c")) {
            String result = restTemplate.getForObject("http://localhost:9092/text?path=" + path + "&key=" + key, String.class);
            //System.out.println( path+" "+result);
            if (result != null && result.contains("true")) {
                end = true;
            }
            //System.out.println(path+" "+end);
        }
        //检查word文件内容
        if (type.equals("docx")) {
            String result = restTemplate.getForObject("http://localhost:9092/docx?path=" + path + "&key=" + key, String.class);
            if (result != null && result.contains("true")) {
                end = true;
            }
        }
        //检查pdf文件内容
        if (type.equals("pdf")) {
            String result = restTemplate.getForObject("http://localhost:9092/pdf?path=" + path + "&key=" + key, String.class);
            if (result != null && result.contains("true")) {
                end = true;
            }
        }
        return end;
    }

    //获得文件内容中包含指定信息的文件列表
    public List<FileAndFolder> getFileAndFoldersByKeyWordInside(String path, String key, List<FileAndFolder> fileAndFolders) {
        //如果文件夹不存在，直接返回空的list
        if (!folderExists(path)) {
            return fileAndFolders;
        }
        File file = new File(path);
        File[] files = file.listFiles();
        for (File fileIn : files) {
            /*if(fileIn.isFile()) {
                System.out.println(fileIn.getAbsolutePath() + " " + checkIfFileInclude(fileIn.getAbsolutePath(), key));
            }*/
            //如果是文件 就先判断文件内容是否包含关键字 包含的话就要
            if (fileIn.isFile() && checkIfFileInclude(fileIn.getAbsolutePath(), key)) {
                String name = fileIn.getName();
                String end = "";
                String size = "0";
                if (fileIn.isFile()) {
                    end = name.substring(name.lastIndexOf(".") + 1);
                    if (name.contains(".")) {
                        name = name.substring(0, name.lastIndexOf("."));
                    }
                    size = String.valueOf(fileIn.length());
                }
                String type = fileIn.isFile() ? "file" : "folder";
                FileAndFolder fileAndFolder = new FileAndFolder(name, end, size, type, fileIn);
                fileAndFolders.add(fileAndFolder);
            }
            //如果是文件夹 就进入深层
            if (fileIn.isDirectory()) {
                getFolderContent(fileIn.getAbsolutePath(), key, fileAndFolders);
            }
        }
        return fileAndFolders;
    }

    //检查目标位置的文件是否存在
    public static boolean checkIfFileExists(String path) {
        return fileExists(path);
    }

    //获得docx文件中内容的文本
    public static String getDocxFileInnerText(String path) throws IOException {
        File file = new File(path);
        FileInputStream fis = new FileInputStream(file.getAbsolutePath());
        XWPFDocument document = new XWPFDocument(fis);
        List<XWPFParagraph> paragraphs = document.getParagraphs();
        //构建可见长度字符串
        StringBuilder end = new StringBuilder();
        for (XWPFParagraph para : paragraphs) {
            end.append(para.getText());
        }
        fis.close();
        return end.toString();
    }


//Bean生命周期

    //销毁Bean的时候关闭公用输入输出流
    @PreDestroy
    public void cleanup() {
        if (fileOutputStream != null) {
            try {
                fileOutputStream.close();
            } catch (IOException e) {
                log.info("关闭公用输出流失败");
            }
        }
        if (fileInputStream != null) {
            try {
                fileInputStream.close();
            } catch (IOException e) {
                log.info("关闭公用输入流失败");
            }
        }
    }
}
