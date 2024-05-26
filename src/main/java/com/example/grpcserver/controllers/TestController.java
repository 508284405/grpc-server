package com.example.grpcserver.controllers;

import com.example.grpcserver.config.TestBean;
import com.example.grpcserver.feign.FeignTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/test")
public class TestController {
    @Resource
    private TestBean testBean;

    @Autowired
    private FeignTest feignTest;

    @RequestMapping("/test1")
    private String test1(){
        return testBean.test();
    }

    @RequestMapping("/test2")
    private String test2(){
        return testBean.test2();
    }
}
