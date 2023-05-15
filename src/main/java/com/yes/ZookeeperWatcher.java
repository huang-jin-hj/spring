package com.yes;

import org.apache.zookeeper.*;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;

public class ZookeeperWatcher implements Watcher {
    private static final int SESSION_TIMEOUT = 5000;
    private ZooKeeper zooKeeper;
    private CountDownLatch connectedSignal = new CountDownLatch(1);

    public void connect(String host) throws IOException, InterruptedException {
        zooKeeper = new ZooKeeper(host, SESSION_TIMEOUT, this);
        connectedSignal.await();
    }

    public void process(WatchedEvent event) {
        if (event.getState() == Event.KeeperState.SyncConnected) {
            connectedSignal.countDown();
        } else if (event.getType() == Event.EventType.NodeDataChanged) {
            String path = event.getPath();
            byte[] data = null;
            try {
                data = zooKeeper.getData(path, this, null);
            } catch (KeeperException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Node data changed: " + path + " data: " + new String(data));
        }
    }

    public void listen(String path) throws KeeperException, InterruptedException {
        byte[] data = zooKeeper.getData(path, this, null);
        System.out.println("Node data: " + new String(data));
    }

    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        String host = "localhost:2181";
        String path = "/zookeeper/test";
        ZookeeperWatcher watcher = new ZookeeperWatcher();
        watcher.connect(host);
        watcher.listen(path);

        while (true) {
            Thread.sleep(1000);
        }
    }
}
