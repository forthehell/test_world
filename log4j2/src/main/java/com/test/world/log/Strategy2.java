package com.test.world.log;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@Slf4j
public class Strategy2 implements Strategy {

    public static final int value = 2;

    public static final int SIZE = 1024 * 1024;

    public static final Logger LOG_2 = LoggerFactory.getLogger("LOG_2");


    ExecutorService executors = Executors.newFixedThreadPool(value, new ThreadFactory() {

        private AtomicInteger atomicInteger = new AtomicInteger();

        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r, "Strategy2_" + atomicInteger.getAndIncrement());
        }
    });


    static class Task implements Runnable {
        int count = SIZE;
        long time = 0;
        String msg;

        private CountDownLatch countDownLatch;

        public Task(CountDownLatch countDownLatch, String msg) {
            this.countDownLatch = countDownLatch;
            this.msg = msg;
        }

        @Override
        public void run() {

            long start = System.currentTimeMillis();
            while (count-- >= 0) {
                log.info("strategy2 ,test ={},  thread ={}", msg.length(), Thread.currentThread().getName());
//                log.info("afdafafsafasfdsfsssssdffdsfsfdsfadfasdfasdfasfdasfasssss,length= {}", msg.length());
//                log.info( msg);
            }
            time = System.currentTimeMillis() - start;
            countDownLatch.countDown();

        }

        long getTime() {
            return time;
        }
    }


    @Override
    public void run(String msg) {

        LOG_2.info("Test start - ********************");

        for (int i = 0; i < 5; i++) {
            doRun(msg);
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        LOG_2.info("Test end - ********************");
        executors.shutdownNow();
    }

    public void doRun(String msg) {


        CountDownLatch countDownLatch = new CountDownLatch(value);
        List<Task> tasks = new ArrayList<>();


        long start = System.currentTimeMillis();

        for (int i = 0; i < value; i++) {
            Task task = new Task(countDownLatch, msg);
            tasks.add(task);
            executors.execute(task);
        }

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long cost = System.currentTimeMillis() - start;

        LOG_2.info("thread={},SIZE_THREAD={} ,time={},average_per_second = {}, total = {}  ", value, SIZE, cost, (SIZE * value / cost) * 1000, SIZE * value);

    }
}
