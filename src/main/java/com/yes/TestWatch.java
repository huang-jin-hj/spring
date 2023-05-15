package com.yes;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.util.List;

public class TestWatch {

    // zookeeper 地址, 多个地址以逗号割开
    private static String zkServer = "127.0.0.1:2181";

    // 会话超时时间
    private static int sessionTimeout = 6000;


    public static void main(String[] args) throws Exception {
        // 创建zookeeper 连接时, 设置全局监听器
        ZooKeeper zooKeeper = new ZooKeeper(zkServer, sessionTimeout, watchedEvent -> {
            // 获取发生事件的节点路径
            String path = watchedEvent.getPath();
            System.out.println(path);
        });

        // 一旦注册便会一直监听,只要有变化, 就会通知全局监听器
        zooKeeper.getChildren("/", true);

        // 线程休眠, 否则不能监控到数据
        Thread.sleep(Long.MAX_VALUE);
    }

}
