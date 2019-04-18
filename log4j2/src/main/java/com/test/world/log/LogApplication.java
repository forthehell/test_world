package com.test.world.log;

import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.event.ApplicationStartingEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Random;


//The throughput performance results below were derived from running the PerfTest, MTPerfTest and PerfTestDriver classes which can be found in the Log4j 2 unit test source directory. For throughput tests, the methodology used was:
//
//        First, warm up the JVM by logging 200,000 log messages of 500 characters.
//        Repeat the warm-up 10 times, then wait 10 seconds for the I/O thread to catch up and buffers to drain.
//        Measure how long it takes to execute 256 * 1024 / threadCount calls to Logger.log and express the result in messages per second.
//        Repeat the test 5 times and average the results.
//        The results below were obtained with log4j-2.0-beta5, disruptor-3.0.0.beta3, log4j-1.2.17 and logback-1.0.10.


@SpringBootApplication
@EnableScheduling
public class LogApplication implements ApplicationRunner, ApplicationListener<ApplicationStartingEvent> {


    @Autowired
    @Qualifier("strategy2")
    private Strategy strategy;


    public static void main(String[] args) {
//        System.setProperty("log4j2.contextSelector", "org.apache.logging.log4j.core.async.AsyncLoggerContextSelector");
        System.setProperty("log4j2.asyncLoggerRingBufferSize", "1024*1024");
        System.setProperty("log4j2.asyncLoggerWaitStrategy","Block");
        new SpringApplicationBuilder().listeners(new LogApplication()).sources(LogApplication.class).run(args);
    }

    private String warmup() {

        long start = System.currentTimeMillis();


        String msg = new String(create());

        int size = 200000;
        int times = 10;

        for (int i = 0; i < size * times; i++) {
            LogManager.getLogger(LogApplication.class).info(msg);
        }

        try {
            Thread.sleep(10 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("warmup time = {}" + (System.currentTimeMillis() - start));
        return msg;
    }

    private char[] create() {
        char[] chars = new char[500];
        Random random = new Random();

        for (int i = 0; i < chars.length; i++) {
            chars[i] = (char) random.nextInt();
        }
        return chars;
    }

//    private void loggingConfig() {
//        LogManager
//
//    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        strategy.run(warmup());

    }

    @Override
    public void onApplicationEvent(ApplicationStartingEvent event) {

    }

}
