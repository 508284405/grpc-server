package com.example.grpcserver;

import com.example.grpcserver.config.TestBean;
import com.example.grpcserver.event.TestEvent;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;

@EnableFeignClients
@SpringBootApplication
public class GrpcServerApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext configurableApplicationContext = SpringApplication.run(GrpcServerApplication.class, args);
        configurableApplicationContext.publishEvent(new TestEvent(1));
//        TestBean bean = configurableApplicationContext.getBean(TestBean.class);
//        System.out.println(bean.getClass());
    }

}
