package com.example.conf;

import com.example.common.ConfZkUtils;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;

/**
 * @author zyc
 */
public class ConfTest {
    String path = "/conf";


    @Test
    public void test(){
        ZooKeeper zk = ConfZkUtils.getZk();
        ConfContent conf = new ConfContent();

        CountDownLatch cd = new CountDownLatch(1);
        Watcher wc = ConfZkUtils.getWc(zk, conf, cd);

        zk.exists(path, wc, new ConfStatCallback(zk, conf, cd), "cba");
        try {
            // 如果不加等待，connecting就完了，到不了connected
            cd.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        ZooKeeper.States state = zk.getState();
        switch (state) {
            case CONNECTING:
                System.out.println("ing......");
                break;
            case ASSOCIATING:
                break;
            case CONNECTED:
                System.out.println("ed........");
                break;
            case CONNECTEDREADONLY:
                break;
            case CLOSED:
                break;
            case AUTH_FAILED:
                break;
            case NOT_CONNECTED:
                break;
        }
        // 此处只会运行一次，且为null
        System.out.println("conf: " + conf.getConf());
        while (true) {

        }
    }
}
