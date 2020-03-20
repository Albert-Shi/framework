package com.test;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.core.util.StatusPrinter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author shishuheng
 * @date 2020/3/20 11:56 上午
 */
public class TestApp {
    public static void main(String[] args) {
        Logger logger = LoggerFactory.getLogger(TestApp.class);
        LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
        logger.trace("123");
        logger.debug("it's end");
        logger.info("this is a info");
        logger.info("new log forever");
//        StatusPrinter.print(lc);
    }
}
