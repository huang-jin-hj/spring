package com.yes;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

//@Component
public class HJHSFWatch implements Watcher {

    @Autowired
    HJHSFConfigServer hjhsfConfigServer;

    // zookeeper 地址, 多个地址以逗号割开
    private static String zkServer = "127.0.0.1:2181";

    // 会话超时时间
    private static int sessionTimeout = 6000;

    private static String HJHSF_NODE = "/HJHSF_NODE";

    ZooKeeper zooKeeper;

    public HJHSFWatch() throws Exception {
        this.connect();
        this.createPersistentNode(HJHSF_NODE, false);
        this.watch(HJHSF_NODE);
    }

    public void createService(String pathInfo){
        String[] path_info = pathInfo.split("/");


    }


    public static void main(String[] args) throws Exception {

        HJHSFWatch testWatch = new HJHSFWatch();


        // 线程休眠, 否则不能监控到数据
        Scanner scanner = new Scanner(System.in);
        while (scanner.next().equals("bye")){
            break;
        }

    }


    private void createPersistentNode(String path, boolean watcher) throws InterruptedException, KeeperException {
        Stat exists = this.zooKeeper.exists(path, watcher);
        if (exists == null){
            this.zooKeeper.create(path, null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        }
    }

    private void createEphemeralNode(String path, boolean watcher) throws InterruptedException, KeeperException {
        Stat exists = this.zooKeeper.exists(path, watcher);
        if (exists == null){
            this.zooKeeper.create(path, null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        }
    }

    private void connect() throws Exception {
        this.zooKeeper = new ZooKeeper(zkServer, sessionTimeout, this);
    }

    private void disConnect() throws InterruptedException {
        this.zooKeeper.close();
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
