package com.nettyrpc.test.util;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author wangff
 * @date 2019/8/8 16:05
 */
public class Test {
    public static void main(String[] args) {
        final CountDownLatch latch = new CountDownLatch(1);

        new Thread(()->{
            try {
                Thread.sleep(80000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            latch.countDown();
        }).start();


        try {
            latch.await(2, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("111111111111111");
    }
}
