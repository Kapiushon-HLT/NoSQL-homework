package com.bjtu.redis;

import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.apache.commons.io.monitor.FileAlterationObserver;


import java.io.File;


import org.apache.log4j.Logger;

public class Listener extends FileAlterationListenerAdaptor {
    public static final Logger logger = Logger.getLogger(Listener.class);
    @Override
    public void onStart(FileAlterationObserver observer) {
        //System.out.println("Monitor start!");
        super.onStart(observer);
    }
    @Override
    public void onFileCreate(File file) {
        logger.info("Files were created: " + file.getName());
    }
    @Override
    public void onFileChange(File file) {
        logger.info("Files were modified: " + file.getName());
        System.out.println("Some files have been modified:" + file.getName());
        System.out.println("Reloading configuration file...");
        if (file.getName().equals("Counter.json") ) {
            RedisDemoApplication.readCounterConfig();
        }
        System.out.println("Configuration file has been reloaded.");
    }

    @Override
    public void onFileDelete(File file) {
        logger.info("Files were deleted: " + file.getName());
    }

    @Override
    public void onStop(FileAlterationObserver observer) {
        super.onStop(observer);
    }
}