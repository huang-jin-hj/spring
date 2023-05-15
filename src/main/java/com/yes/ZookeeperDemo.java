package com.yes;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.List;

public class ZookeeperDemo implements Watcher {
    private static final String HOST = "localhost:2181";
    private static final int SESSION_TIMEOUT = 5000;
    private static final String NODE_PATH = "/demo";
    private ZooKeeper zooKeeper;

    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
        ZookeeperDemo demo = new ZookeeperDemo();
        demo.connect();
        demo.createNode(NODE_PATH);
        demo.watchChildren(NODE_PATH);

        Thread.sleep(100000000);
    }

    private void connect() throws IOException {
        zooKeeper = new ZooKeeper(HOST, SESSION_TIMEOUT, this);
        System.out.println("Connected to ZooKeeper");
    }

    private void close() throws InterruptedException {
        zooKeeper.close();
        System.out.println("Closed connection to ZooKeeper");
    }

    private void createNode(String path) throws KeeperException, InterruptedException {
        zooKeeper.create(path, null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        System.out.println("Created node: " + path);
    }

    private void deleteNode(String path) throws KeeperException, InterruptedException {
        zooKeeper.delete(path, -1);
        System.out.println("Deleted node: " + path);
    }

    private void watchChildren(String path) throws KeeperException, InterruptedException {
        List<String> children = zooKeeper.getChildren(path, true);
        if (children.isEmpty()) {
            System.out.println("No children for path: " + path);
        } else {
            System.out.println("Children for path " + path + ":");
            for (String child : children) {
                System.out.println(child);
            }
        }
    }

    @Override
    public void process(WatchedEvent event) {
        String path = event.getPath();
        Event.EventType type = event.getType();
        switch (type) {
            case NodeChildrenChanged:
                System.out.println("Children changed for path: " + path);
                try {
                    watchChildren(path);
                } catch (KeeperException | InterruptedException e) {
                    e.printStackTrace();
                }
                break;
            case NodeDeleted:
                System.out.println("Node deleted: " + path);
                break;
            case NodeCreated:
                System.out.println("Node created: " + path);
                break;
            default:
                break;
        }
    }
}
