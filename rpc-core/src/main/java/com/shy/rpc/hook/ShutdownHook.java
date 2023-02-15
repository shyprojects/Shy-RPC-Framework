package com.shy.rpc.hook;

import com.shy.rpc.util.NacosUtil;
import com.shy.rpc.util.ThreadPoolFactory;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;

/***
 * 添加钩子函数,在jvm销毁的时候调用,另起一个线程调用NacosUtils注销Nacos中的服务
 * @author shy
 * @date 2023-02-15 10:30
 */
@Slf4j
public class ShutdownHook {
    private static final ShutdownHook shutdownHook = new ShutdownHook();

    private final ExecutorService threadPool = ThreadPoolFactory.createDefaultThreadPool("shutdown-hook");

    public static ShutdownHook getShutdownHook() {
        return shutdownHook;
    }

    public void addClearAllHook() {
        log.info("服务器将关闭,并且注销所有服务...");
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            NacosUtil.clearRegistry();
            threadPool.shutdown();
        }));
    }
}
