package com.test.world.log;

import com.github.benmanes.caffeine.cache.*;

import java.util.Random;
import java.util.concurrent.*;

public class CaffineTest {


    final static AsyncLoadingCache<Integer, Integer> cache = Caffeine
            .newBuilder()
            .refreshAfterWrite(5, TimeUnit.SECONDS)
            .expireAfterWrite(5, TimeUnit.SECONDS)
            .buildAsync(item -> {
                TimeUnit.MICROSECONDS.sleep(20);
                return new Random().nextInt();
            });

//
//    static AsyncCacheLoader cacheLoader = (item,executor) -> {
//        TimeUnit.MICROSECONDS.sleep(20);
//        ExecutorService executorService1 = Executors.newSingleThreadExecutor();
//        return executor.(()->new Random().nextInt());
//    };
////

//    FutureTask futureTask ;

    //            <Key, Graph> loader = (key, executor) -> createExpensiveGraphAsync(key, executor);

//
//    final static AsyncLoadingCache<Integer, Integer> cache2 = Caffeine
//            .newBuilder()
//            .refreshAfterWrite(5, TimeUnit.SECONDS)
//            .expireAfterWrite(5, TimeUnit.SECONDS)
//            .buildAsync(cacheLoader);


    public static final int n = 5;

    static ExecutorService executorService = Executors.newFixedThreadPool(n);


    public static void main(String[] args) {

//        cache.put(0,0);
//        for(int j =0 ;j <n ;j++){
        CyclicBarrier cyclicBarrier = new CyclicBarrier(n);

        int v = 0;
        for (int i = 0; i < 5; i++) {
            executorService.execute(() -> {
                try {
                    cyclicBarrier.await();
                    System.out.println(cache.get(v).get());
                    TimeUnit.SECONDS.sleep(3);
                } catch (Exception e) {

                }

            });
        }


        for (int i = 0; i < 5; i++) {
            executorService.execute(() -> {
                try {
                    cyclicBarrier.await();
                    System.out.println(cache.get(v).get());
                    TimeUnit.SECONDS.sleep(3);
                } catch (Exception e) {

                }

            });
        }

        for (int i = 0; i < 5; i++) {
            executorService.execute(() -> {
                try {
                    cyclicBarrier.await();
                    System.out.println(cache.get(v).get());
                    TimeUnit.SECONDS.sleep(3);
                } catch (Exception e) {

                }

            });
        }

        for (int i = 0; i < 5; i++) {
            executorService.execute(() -> {
                try {
                    cyclicBarrier.await();
                    System.out.println(cache.get(v).get());
                    TimeUnit.SECONDS.sleep(3);
                } catch (Exception e) {

                }

            });
        }
//        }


    }

}

