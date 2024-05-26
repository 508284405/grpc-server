package com.example.grpcserver.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Data
@Component
@Qualifier
public class TestBean {
    private Integer a;
    public String test(){
        return "testbean";
    }
    public String test2(){
        return "testbean2";
    }
}
