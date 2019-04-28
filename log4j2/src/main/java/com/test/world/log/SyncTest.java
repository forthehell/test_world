package com.test.world.log;

import java.util.concurrent.*;

public class SyncTest {


    volatile int value;

    public  void test() throws InterruptedException {


        int i = value;

        synchronized (this) {
            System.out.println(value);
            value++;
            System.out.println(value);
            TimeUnit.SECONDS.sleep(5);
        }

    }





    public static void main(String[] args) throws InterruptedException {

//        CyclicBarrier barrier = new CyclicBarrier(2);
        SyncTest test = new SyncTest();

        int n = 500;
        CountDownLatch countDownLatch = new CountDownLatch(n);
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        for(int i = 0 ;i <n ;i++){
            executorService.execute(() -> {
                try {
//                barrier.await();
                    test.test();
                    countDownLatch.countDown();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }

        countDownLatch.await();

        System.out.println(test.value);

    }
}
