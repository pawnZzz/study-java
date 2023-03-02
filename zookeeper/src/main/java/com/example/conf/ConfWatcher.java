package com.example.conf;

import com.example.common.ConfZkUtils;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.util.concurrent.CountDownLatch;

/**
 * @author zyc
 */
public class ConfWatcher implements Watcher {
    ZooKeeper zk;
    ConfContent conf;

    CountDownLatch cd;

    public ConfWatcher(ZooKeeper zk, ConfContent conf, CountDownLatch cd) {
        this.zk = zk;
        this.conf = conf;
        this.cd = cd;
    }

    public ConfContent getConf() {
        return conf;
    }

    public void setConf(ConfContent conf) {
        this.conf = conf;
    }

    public ZooKeeper getZk() {
        return zk;
    }

    public void setZk(ZooKeeper zk) {
        this.zk = zk;
    }

    public CountDownLatch getCd() {
        return cd;
    }

    public void setCd(CountDownLatch cd) {
        this.cd = cd;
    }

    @Override
    public void process(WatchedEvent event) {

        switch (event.getType()) {
            case None:
                break;
            case NodeCreated:
                // 一开始
                System.out.println("created...");
                zk.getData(ConfZkUtils.PATH, this, new ConfDataCallBack(zk, conf, cd), "abc");
                cd.countDown();
                break;
            case NodeDeleted:
                conf.setConf("");
                // 删除后如何处理，根据业务定, 容忍性
//                zk.exists(ZkUtils.PATH, new ConfWatcher(zk, conf), new ConfStatCallback(zk, conf), "cba");
                System.out.println("node被删除...");
                cd = new CountDownLatch(1); // cd重新设置
                break;
            case NodeDataChanged:
                System.out.println("data changed...");
                // 更新内容后重新获取数据
                zk.getData(ConfZkUtils.PATH, this, new ConfDataCallBack(zk, conf, cd), "change");
                cd.countDown();
                break;
            case NodeChildrenChanged:
                break;
        }
    }
}
