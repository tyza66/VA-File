package com.tyza66.vfm_core.service;

/**
 * Author: tyza66
 * Date: 2023/7/27 8:48
 * Github: https://github.com/tyza66
 **/

public interface FileResolverService {

    //pdf转换成word
    boolean pdf2word(String path);

    //word转换成pdf
    boolean word2pdf(String path);

    //识别图片内容
    String image2text(String path);

    //对文件内容进行总结，之后将总结结果放入数据库中方便之后的快速查询
    String summarize(String path);
}
