package com.example.conf;

import com.example.common.ConfZkUtils;
import org.apache.zookeeper.AsyncCallback;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.util.concurrent.CountDownLatch;

/**
 * @author zyc
 */
public class ConfStatCallback implements AsyncCallback.StatCallback {
    ZooKeeper zk;
    ConfContent conf;
    CountDownLatch cd;

    public ConfStatCallback(ZooKeeper zk, ConfContent conf, CountDownLatch cd) {
        this.zk = zk;
        this.conf = conf;
        this.cd = cd;
    }

    public CountDownLatch getCd() {
        return cd;
    }

    public void setCd(CountDownLatch cd) {
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

    @Override
    public void processResult(int rc, String path, Object ctx, Stat stat) {
        // stat不为空，说明path存在
        if (stat != null) {
            System.out.println("stat 不为空");
            Watcher wc = ConfZkUtils.getWc(zk, conf, cd);
            zk.getData(ConfZkUtils.PATH, wc, new ConfDataCallBack(zk, conf,cd), "stat");
            try {
                cd.countDown();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
