package com.test.world.log;

import com.lmax.disruptor.ExceptionHandler;
import lombok.extern.log4j.Log4j2;


public class LogExceptionHandler implements ExceptionHandler {


    @Override
    public void handleEventException(Throwable throwable, long l, Object o) {
    }

    @Override
    public void handleOnStartException(Throwable throwable) {

    }

    @Override
    public void handleOnShutdownException(Throwable throwable) {

    }
}
