package com.example.grpcserver;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;


public class Test1 {
    @Test
    public void a() {
        String a = "a";
        for (int i = 0; i < 10000; i++) {
            a += i;
        }
    }

    @Test
    public void b() {
        Integer a = 1;
        Integer b = 2;
        Integer c = null;
        boolean flag = false;
// a*b的结果是int类型，那么c会强制拆箱成int类型，抛出NPE异常
        Integer result = (flag ? a * b : c);
        List a = new ArrayList();
        a.forEach(x->{});
    }

    public static int test1() {
        int i = 0;
        try {
            i = 2;
            return i;
        } finally {
            i = 3;
        }
    }
}
