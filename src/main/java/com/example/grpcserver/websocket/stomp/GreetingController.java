package com.example.grpcserver.websocket.stomp;

import jdk.nashorn.internal.objects.annotations.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

@Controller
public class GreetingController {
    private static final Logger log = LoggerFactory.getLogger(GreetingController.class);
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/greeting")
    @SendTo("/topic/greeting")
    public String handle(String greeting) {
        return "[" + System.currentTimeMillis() + ": " + greeting;
    }


    @PostMapping("/send/greeting")
    @ResponseBody
    public void sendMessage(@RequestBody String greeting) throws InterruptedException {
        TimeUnit.SECONDS.sleep(60);
        log.info("sending greeting: {}", greeting);
        simpMessagingTemplate.convertAndSend("/topic/greeting", greeting);
    }

    @PostMapping("/send/greeting2")
    @ResponseBody
    public void sendMessage2(@RequestBody String greeting) {
        log.info("sending greeting2: {}", greeting);
        simpMessagingTemplate.convertAndSend("/topic/greeting", greeting);
    }

    @PostMapping("/send/username/{username}/{msg}")
    @ResponseBody
    public void sendMessage(@PathVariable("username") String username, @PathVariable("msg") String msg) {
        log.info("sending username:[{}] greeting: [{}]", username, msg);
        simpMessagingTemplate.convertAndSendToUser(username, "/topic/greeting", msg);
    }
}