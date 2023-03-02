package com.example.conf;

import org.apache.zookeeper.AsyncCallback;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.util.concurrent.CountDownLatch;

/**
 * @author zyc
 */
public class ConfDataCallBack implements AsyncCallback.DataCallback {
    ZooKeeper zk;
    ConfContent conf;

    CountDownLatch cd;

    public ConfDataCallBack(ZooKeeper zk, ConfContent conf, CountDownLatch cd) {
        this.zk = zk;
        this.conf = conf;
        this.cd = cd;
    }

    public ZooKeeper getZk() {
        return zk;
    }

    public void setZk(ZooKeeper zk) {
        this.zk = zk;
    }

    public ConfContent getConf() {
        return conf;
    }

    public void setConf(ConfContent conf) {
        this.conf = conf;
    }

    public CountDownLatch getCd() {
        return cd;
    }

    public void setCd(CountDownLatch cd) {
        this.cd = cd;
    }

    @Override
    public void processResult(int rc, String path, Object ctx, byte[] data, Stat stat) {

        if (data != null) {
            conf.setConf(new String(data));
            System.out.println("data不为空：" + new String(data) + "; ctx:" + ctx);
            try {
                cd.countDown();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
