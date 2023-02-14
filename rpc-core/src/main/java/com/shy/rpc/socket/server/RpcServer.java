package com.shy.rpc.socket.server;

import com.shy.rpc.RequestHandler;
import com.shy.rpc.register.ServiceRegistry;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

/***
 * @author shy
 * @date 2023-02-12 19:26
 */
@Slf4j
public class RpcServer {
    private final ExecutorService threadPool;
    private final ServiceRegistry serviceRegistry;

    private RequestHandler handler = new RequestHandler();

    public RpcServer(ServiceRegistry serviceRegistry) {
        int corePoolSize = 5;
        int maximumPoolSize = 50;
        long keepAliveTime = 60;
        BlockingQueue<Runnable> workingQueue = new ArrayBlockingQueue<>(100);
        ThreadFactory threadFactory = Executors.defaultThreadFactory();
        threadPool = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.SECONDS, workingQueue, threadFactory);
        this.serviceRegistry = serviceRegistry;
    }
    public void start(Object service,int port){
        try (ServerSocket serverSocket = new ServerSocket(port)){
            log.info("服务器正在启动...");
            Socket socket;
            while ((socket = serverSocket.accept()) != null){
                log.info("客户端连接成功ip:{}",socket.getInetAddress());
                threadPool.execute(new RequestHandlerThread(service,socket,handler));
            }
        } catch (IOException e) {
            log.info("连接发生错误",e);
        }
    }
}
