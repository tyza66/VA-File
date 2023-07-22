package com.tyza66.vfm_core.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.File;

/**
 * Author: tyza66
 * Date: 2023/7/22 19:13
 * Github: https://github.com/tyza66
 **/

//这个类是用来存储文件和文件夹的
    //方便之后作为查询结果返回给前端
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileAndFolder {
    //文件名 就是不包括后缀名
    String name;
    //文件后缀 就是文件后缀名
    String end;
    //文件大小
    String size;
    //类型 是文件还是文件夹
    String type;
    //文件原型 就是遍历的时候那些File原型 其中会保存很多的信息
    //但是自带的默认toString方法返回的是文件路径 这里返回前端的结果可以直接当文件路径用 在后端就可以直接通过这个原型来进行文件操作0
    File prototype;
}
