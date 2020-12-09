package com.bjtu.redis;


import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import java.util.concurrent.TimeUnit;

@Component
public class Monitor {

    @PostConstruct
    public void initFileMonitor(String fileName) {
        // 监控目录
        String rootDir = Monitor.class.getClassLoader().getResource("").getPath();
        int time = 1;
        long interval = TimeUnit.SECONDS.toMillis(time);
        FileAlterationObserver observer = new FileAlterationObserver(rootDir);
        //设置文件监听器
        observer.addListener(new Listener());
        //创建文件监听器
        FileAlterationMonitor monitor = new FileAlterationMonitor(interval);
        monitor.addObserver(observer);

        monitor(monitor);
    }
    // 开始监控
    public static void monitor(FileAlterationMonitor monitor){

        try {
            monitor.start();
            System.out.println("开始监控");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}