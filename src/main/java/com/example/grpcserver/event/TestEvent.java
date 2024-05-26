package com.example.grpcserver.event;

import org.openjdk.jol.info.ClassLayout;
import org.springframework.context.ApplicationEvent;

public class TestEvent extends ApplicationEvent {

    public TestEvent(Object source) {
        super(source);
    }

    public static void main(String[] args) {
        Object o = new Test1();
        System.out.println(ClassLayout.parseInstance(o).toPrintable());
        System.out.println("==================");
        System.out.println(ClassLayout.parseInstance(new Object()).toPrintable());
        System.out.println("==================");
        Test1 test1 = new Test1();
        System.out.println(ClassLayout.parseInstance(test1).toPrintable());
    }
}
