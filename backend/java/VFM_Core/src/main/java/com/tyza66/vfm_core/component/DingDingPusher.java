package com.tyza66.vfm_core.component;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiRobotSendRequest;
import lombok.SneakyThrows;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;

@Component
public class DingDingPusher {

    @Value("${dingding.token}")
    private String dingToken;
    @Value("${dingding.secret}")
    private String dingSecret;
    @Value("${dingding.name}")
    private String dingName;

    @PostConstruct
    public void init() {
        sendMessageMarkdown("启动信息", dingName + "已启动");
    }

    @SneakyThrows
    private String dingUrl() {
        String webhook = "https://oapi.dingtalk.com/robot/send?access_token=" + dingToken;
        String secret = dingSecret;
        long timestamp = Instant.now().toEpochMilli();
        String stringToSign = timestamp + "\n" + secret;
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
        byte[] signData = mac.doFinal(stringToSign.getBytes(StandardCharsets.UTF_8));
        String sign = URLEncoder.encode(new String(Base64.encodeBase64(signData)), "UTF-8");
        return webhook + "&timestamp=" + timestamp + "&sign=" + sign;
    }

    /**
     * 发送消息到钉钉群，不 @ 任何人
     *
     * @param message 消息内容 text格式
     */
    @SneakyThrows
    public void sendMessageWithoutUser(String message) {
        DingTalkClient client = new DefaultDingTalkClient(dingUrl());
        OapiRobotSendRequest request = new OapiRobotSendRequest();
        // 消息类型，此时固定为：text
        request.setMsgtype("text");
        OapiRobotSendRequest.Text text = new OapiRobotSendRequest.Text();
        // 消息内容
        text.setContent(dingName + "：" + message);
        request.setText(text);
        client.execute(request);
    }

    /**
     * 发送消息到钉钉群，不 @ 任何人
     *
     * @param title   标题
     * @param message 消息内容 Markdown格式
     */
    @SneakyThrows
    public void sendMessageMarkdown(String title, String message) {
        DingTalkClient client = new DefaultDingTalkClient(dingUrl());
        OapiRobotSendRequest request = new OapiRobotSendRequest();
        // 消息类型，此时固定为：markdown
        request.setMsgtype("markdown");
        OapiRobotSendRequest.Markdown markdown = new OapiRobotSendRequest.Markdown();
        // 首屏会话透出的展示内容
        markdown.setTitle(title);
        // markdown格式的消息
        markdown.setText(dingName + "：" +message);
        request.setMarkdown(markdown);
        client.execute(request);
    }

    /**
     * 发送消息到钉钉群，并 @ 指定的群成员
     *
     * @param message  消息内容 text格式
     * @param isNotAll 是否 @ 群内所有人
     */
    @SneakyThrows
    public void sendMessageWithUser(String message, boolean isNotAll) {
        DingTalkClient client = new DefaultDingTalkClient(dingUrl());
        OapiRobotSendRequest request = new OapiRobotSendRequest();
        // 消息类型，此时固定为：text
        request.setMsgtype("text");
        OapiRobotSendRequest.Text text = new OapiRobotSendRequest.Text();
        // 消息内容
        text.setContent(message);
        request.setText(text);
        OapiRobotSendRequest.At at = new OapiRobotSendRequest.At();
        // @ 群成员list
        List<String> list = Arrays.asList("被@人的手机号");
        at.setAtMobiles(list);
        // 不 @ 所有人
        at.setIsAtAll(isNotAll);
        request.setAt(at);
        client.execute(request);
    }
}