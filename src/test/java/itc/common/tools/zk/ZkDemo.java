package itc.common.tools.zk;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

/**
 * @ProjectName: java-tools
 * @Package: itc.common.tools.zk
 * @ClassName: ZkDemo
 * @Description:
 * @Author: Mastercoding
 * @CreateDate: 2019/5/23 16:17
 * ***********************************************************
 * @UpdateUser: Mastercoding
 * @UpdateDate: 2019/5/23 16:17
 * @UpdateRemark: The modified content
 * @Version: 1.0
 * ***********************************************************
 * Copyright: Copyright (c) 2019
 **/
@Slf4j
public class ZkDemo {
    public static void main(String[] args) throws InterruptedException {
        String connectionString = "192.168.1.231";
        ExponentialBackoffRetry retryPolicy = new ExponentialBackoffRetry(1000, 3, Integer.MAX_VALUE);

        /**
         * connectionString zk地址
         * retryPolicy 重试策略
         * 默认的sessionTimeoutMs为60000
         * 默认的connectionTimeoutMs为15000
         */
        CuratorFramework curatorFramework = CuratorFrameworkFactory.newClient(connectionString, retryPolicy);
        curatorFramework.start();
        try {
            String path = curatorFramework.create().withMode(CreateMode.EPHEMERAL_SEQUENTIAL).forPath("/itcZk/dcserver");
            path = curatorFramework.create().withMode(CreateMode.EPHEMERAL_SEQUENTIAL).forPath("/itcZk/dcserver");
            path = curatorFramework.create().withMode(CreateMode.EPHEMERAL_SEQUENTIAL).forPath("/itcZk/dcserver");
            System.out.println(path);
            Thread.currentThread().join();
        } catch (Exception e) {
            e.printStackTrace();
        }
        /**
         * connectionString zk地址
         * sessionTimeoutMs 会话超时时间
         * connectionTimeoutMs 连接超时时间
         * retryPolicy 重试策略
         */
        CuratorFramework curatorFramework1 = CuratorFrameworkFactory.newClient(connectionString, 60000, 15000, retryPolicy);
        curatorFramework1.start();

        /**
         * connectionString zk地址
         * sessionTimeoutMs 会话超时时间
         * connectionTimeoutMs 连接超时时间
         * namespace 每个curatorFramework 可以设置一个独立的命名空间,之后操作都是基于该命名空间，比如操作 /app1/message 其实操作的是/node1/app1/message
         * retryPolicy 重试策略
         */
        CuratorFramework curatorFramework2 = CuratorFrameworkFactory.builder().connectString(connectionString)
                .sessionTimeoutMs(60000)
                .connectionTimeoutMs(15000)
                .namespace("/node1")
                .retryPolicy(retryPolicy)
                .build();
        curatorFramework2.start();
    }
}
