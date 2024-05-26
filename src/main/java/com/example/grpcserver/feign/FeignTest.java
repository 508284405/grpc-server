package com.example.grpcserver.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = "test1111")
public interface FeignTest {
    @RequestMapping("/test/test2")
    String test2();
}
