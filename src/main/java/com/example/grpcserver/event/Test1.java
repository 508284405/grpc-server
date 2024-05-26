package com.example.grpcserver.event;


import lombok.Data;
import org.aspectj.lang.reflect.LockSignature;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

@Data
public class Test1 {
    private Long a = Long.MAX_VALUE;
    private Long b = Long.MAX_VALUE;
    private Long c = Long.MAX_VALUE;
    private static Long d = Long.MAX_VALUE;

    public static void main(String[] args) throws InterruptedException {
        float f = 3.2f;
        double d = 3.2;
        long l = 2L;
        int i = 2;
        short s = 1;
        byte b = 1;
        s = (short) (s + 1);
        b = (byte) (b + 1);
        switch (b) {

        }
        new Thread();
    }

    public synchronized static void test() {
        String a = "aa";
        for (int i = 0; i < 100; i++) {
            a += i;
        }
    }
}
