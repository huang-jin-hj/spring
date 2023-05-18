package com.yes;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.List;

/**
 * Created by huangJin on 2023/5/18.
 */
public class ZookeeperWatch implements Watcher {
    private static String zkServer = "127.0.0.1:2181";

    // 会话超时时间
    private static int sessionTimeout = 6000;

    ZooKeeper zooKeeper;

    public ZookeeperWatch() throws IOException {
        this.zooKeeper = new ZooKeeper(zkServer, sessionTimeout, this);
    }

    public void watch(String path) throws InterruptedException, KeeperException {
        this.zooKeeper.getChildren(path, true);
    }
    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        ZookeeperWatch zookeeperWatch = new ZookeeperWatch();
        zookeeperWatch.watch("/demo");

        Thread.sleep(10000000);
    }

    @Override
    public void process(WatchedEvent event) {
        if (event.getType() == Event.EventType.NodeChildrenChanged){
            System.out.println(event.getPath());
            try {
                List<String> children = this.zooKeeper.getChildren(event.getPath(), true);
                System.out.println("子节点为 --->" + children);
            } catch (KeeperException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
