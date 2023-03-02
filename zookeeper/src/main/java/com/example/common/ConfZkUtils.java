package com.example.common;

import com.example.conf.ConfContent;
import com.example.conf.ConfWatcher;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * @author zyc
 */
public class ConfZkUtils {
    public final static String ADDRESS = "localhost:2181";
    public final static String PATH = "/conf";
    public static Watcher watcher = null;

    public static ZooKeeper getZk() {
        ZooKeeper zk;
        try {
            zk = new ZooKeeper(ADDRESS, 1000, new DefaultWatcher());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return zk;
    }

    public static Watcher getWc(ZooKeeper zk, ConfContent conf, CountDownLatch cd){
        if(watcher == null) {
            watcher = new ConfWatcher(zk, conf, cd);
        }

        return watcher;
    }

}
