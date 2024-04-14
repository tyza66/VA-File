package com.tyza66.vfm_core.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Author: tyza66
 * Date: 2024/4/14 15:41
 * Github: https://github.com/tyza66
 **/

@Component
public class DingBotActiveCode {
    // 当前的激活码
    private volatile String activeCode;

    @Autowired
    private DingDingPusher dingDingPusher;

    // 注册Bean的初始化
    @PostConstruct
    public void init() {
        // 生成两位数随机数
        activeCode = String.valueOf((int) (Math.random() * 90 + 10));
        dingDingPusher.sendMessageMarkdown("验证消息", "动态码已更新为：" + activeCode);
    }

    // 定时器每两小时更新一次激活码
    @Scheduled(fixedRate = 1000 * 60 * 60 * 2)
    public void updateActiveCode() {
        activeCode = String.valueOf((int) (Math.random() * 90 + 10));
        dingDingPusher.sendMessageMarkdown("验证消息", "动态码已更新为：" + activeCode);
    }

    public String getActiveCode() {
        return activeCode;
    }

    public void setActiveCode(String activeCode) {
        this.activeCode = activeCode;
    }

}
