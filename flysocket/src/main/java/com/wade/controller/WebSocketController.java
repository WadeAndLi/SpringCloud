package com.wade.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

import javax.xml.ws.Response;

@Controller
public class WebSocketController {

    private int count = 0;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/hello")
    public void openSocket() {
        messagingTemplate.convertAndSend("/topic/message", "成功连接");
    }

    @MessageMapping("/sendTest")
    public void sendDemo(String message) {
        messagingTemplate.convertAndSend("/topic/message", message);
    }

    @SubscribeMapping("/topic/message")
    public Response sub() {
        return "感谢您订阅了我";
    }
}
