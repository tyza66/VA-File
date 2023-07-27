package com.tyza66.vfm_core.controller;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * Author: tyza66
 * Date: 2023/7/27 17:09
 * Github: https://github.com/tyza66
 **/

@Api(tags = "问答模块")
@RestController
@RequestMapping("/qr")
public class QAController {

    @Autowired
    RestTemplate restTemplate;

    @ApiOperation("问答")
    @GetMapping("/q")
    public JSON q(@RequestParam String context, @RequestParam String q) {
        JSONObject obj = JSONUtil.createObj();
        if (StpUtil.isLogin()) {
            String forObject = restTemplate.getForObject("http://localhost:9091/qa?text=" + context + "&q_text=" + q, String.class);
            if (forObject == null) {
                obj.set("code", 199);
                obj.set("msg", "获取失败");
            } else {
                obj.set("code", 200);
                obj.set("msg", "获取成功");
                obj.set("data", forObject);
            }
        } else {
            obj.set("code", 201);
            obj.set("msg", "未登录");
        }
        return obj;
    }

}
