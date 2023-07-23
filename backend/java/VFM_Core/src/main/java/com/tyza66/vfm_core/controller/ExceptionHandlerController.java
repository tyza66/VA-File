package com.tyza66.vfm_core.controller;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Author: tyza66
 * Date: 2023/7/23 9:58
 * Github: https://github.com/tyza66
 **/

@ControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public JSON exceptionHandler(Exception e) {
        JSONObject obj = JSONUtil.createObj();
        obj.set("code","198");
        obj.set("msg",e.getMessage());
        return obj;
    }
}
