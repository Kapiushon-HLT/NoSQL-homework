package com.bjtu.redis;

import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.apache.commons.io.monitor.FileAlterationObserver;


import java.io.File;


import org.apache.log4j.Logger;

public class Listener extends FileAlterationListenerAdaptor {
    public static final Logger logger = Logger.getLogger(Listener.class);
    @Override
    public void onStart(FileAlterationObserver observer) {
        super.onStart(observer);
    }
    @Override
    public void onFileCreate(File file) {
        logger.info("文件: " + file.getName() + " 已被创建");
    }
    @Override
    public void onFileChange(File file) {
        logger.info("文件: " + file.getName() + " 已被修改");
        System.out.println("文件: " + file.getName() + " 已被修改");
        System.out.println("重载配置文件");
        if (file.getName().equals("Counter.json") ) {
            RedisDemoApplication.readCounterJson();
        }
        System.out.println("配置文件已重载");
    }

    @Override
    public void onFileDelete(File file) {
        logger.info("文件: " + file.getName() + " 已被删除");
    }

    @Override
    public void onStop(FileAlterationObserver observer) {
        super.onStop(observer);
    }
}