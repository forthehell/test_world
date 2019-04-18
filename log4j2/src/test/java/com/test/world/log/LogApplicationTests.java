package com.test.world.log;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LogApplicationTests {

    @Test
    public void contextLoads() {
    }


    public static void main(String[] args) {
        System.out.println("strategy2 ,test =749235,  thread =Strategy2_0".length());
    }
}
