package com.test.world.log;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@Slf4j
public class Strategy1 implements Strategy {

    public static final int value = 6;
    volatile long lastNum = 0;
    AtomicInteger secends = new AtomicInteger();


    public static final Logger LOG_1 = LoggerFactory.getLogger("LOG_1");


    List<Task> runnableList = new ArrayList<>();

    volatile boolean isRun = false;


    ExecutorService executors = Executors.newFixedThreadPool(value, new ThreadFactory() {

        private AtomicInteger atomicInteger = new AtomicInteger();

        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r, "StrategyOne_" + atomicInteger.getAndIncrement());
        }
    });


    static class Task implements Runnable {
        volatile int count = 0;
        String msg;

        public Task(String msg) {
            this.msg = msg;
        }

        @Override
        public void run() {

            for (; ; ) {
//                log.info(msg);
                log.info("strategy1 ,test ={},  thread ={}", count, Thread.currentThread().getName());
                count++;
            }
        }

        long getCount() {
            return count;
        }
    }


    @Scheduled(fixedRate = 1000, initialDelay = 1000)
    public void test() {

        if (!isRun) {
            return;
        }

        long total = runnableList.stream().mapToLong(item -> item.getCount()).sum();
        int seconds = secends.incrementAndGet();

        LOG_1.info("1 sed output ,added = {},average ={},total = {} , ", total - lastNum, total / seconds, total);

        lastNum = total;
    }

    @Override
    public void run(String msg) {
        isRun = true;
        for (int i = 0; i < value; i++) {
            Task task = new Task(msg);
            runnableList.add(task);
            executors.execute(task);
        }

    }


}
