package com.test;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.core.util.StatusPrinter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author shishuheng
 * @date 2020/3/20 11:56 上午
 */
public class TestApp {
    public static void main(String[] args) throws Exception {
        Logger logger = LoggerFactory.getLogger(TestApp.class);
        LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss:SSS");
        for (int i = 0; i < 200; i++) {
            logger.warn("这是一个船新的警告" + format.format(new Date()));
            Thread.sleep(1000);
        }
//        StatusPrinter.print(lc);
    }

    interface InterfaceA<T> {
        T get(T t);
    }

    abstract class AbstractA implements InterfaceA<Integer> {
        @Override
        public Integer get(Integer integer) {
            return null;
        }
    }

    public class ImplA extends AbstractA implements InterfaceA<Integer> {

    }
}
