package com.example.grpcserver.websocket.stomp.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.converter.StringMessageConverter;
import org.springframework.messaging.simp.stomp.*;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.RestTemplateXhrTransport;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import java.lang.reflect.Type;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
public class ClientDemo {
    public static void main(String[] args) throws InterruptedException, URISyntaxException {
        ConcurrentTaskScheduler taskScheduler = new ConcurrentTaskScheduler();
        List<Transport> transports = new ArrayList<>(2);
        transports.add(new WebSocketTransport(new StandardWebSocketClient()));
        transports.add(new RestTemplateXhrTransport());

        WebSocketClient webSocketClient = new SockJsClient(transports);

        WebSocketStompClient stompClient = new WebSocketStompClient(webSocketClient);
        stompClient.setMessageConverter(new StringMessageConverter());
        stompClient.setTaskScheduler(taskScheduler); // for heartbeats

        StompHeaders connectHeaders = new StompHeaders();
        connectHeaders.set("userName", "zhangsan");
//        connectHeaders.set("userName", "lisi");

        WebSocketHttpHeaders headers = new WebSocketHttpHeaders();
//        headers.add("userName", "zhangsan");
//        headers.add("userName", "lisi");

        String url = "ws://localhost:8080/portfolio";
        StompSessionHandler sessionHandler = new StompSessionHandlerAdapter() {
            @Override
            public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
                session.send("/app/greeting", "payload");
                session.subscribe("/topic/greeting", new StompFrameHandler() {
                    @Override
                    public Type getPayloadType(StompHeaders headers) {
                        return String.class;
                    }

                    @Override
                    public void handleFrame(StompHeaders headers, Object payload) {
                        //
                        log.info("payload===: {}", payload);
                    }
                });
            }
        };
        stompClient.connect(new URI(url), headers, connectHeaders, sessionHandler);
        TimeUnit.HOURS.sleep(30);
    }
}
