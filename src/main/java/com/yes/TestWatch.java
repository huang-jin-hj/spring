package com.yes;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.List;

public class TestWatch implements Watcher {

    // zookeeper 地址, 多个地址以逗号割开
    private static String zkServer = "127.0.0.1:2181";

    // 会话超时时间
    private static int sessionTimeout = 6000;

    ZooKeeper zooKeeper;


    public static void main(String[] args) throws Exception {

        TestWatch testWatch = new TestWatch();
        testWatch.connect();
        testWatch.watch("/demo");

        // 线程休眠, 否则不能监控到数据
        Thread.sleep(Long.MAX_VALUE);
    }

    private void connect() throws IOException {
        this.zooKeeper = new ZooKeeper(zkServer, sessionTimeout, this);
    }

    private void watch(String path) throws InterruptedException, KeeperException {
        try {
            System.out.println("监视节点" + path);
            List<String> children = this.zooKeeper.getChildren(path, true);
            System.out.println("子节点有" + children.toString());
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }


    @Override
    public void process(WatchedEvent event) {
        System.out.println(event);
        if (event.getType() == Event.EventType.NodeChildrenChanged){
            System.out.println("监视节点" + event.getPath());
            List<String> children = null;
            try {
                children = this.zooKeeper.getChildren(event.getPath(), true);
            } catch (KeeperException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("子节点有" + children.toString());
            for (String child : children) {
                try {
                    this.zooKeeper.getChildren(event.getPath() + "/" + child, true);
                } catch (KeeperException e) {
                    throw new RuntimeException(e);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
