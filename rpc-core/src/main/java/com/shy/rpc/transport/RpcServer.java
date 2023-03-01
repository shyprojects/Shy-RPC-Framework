package com.shy.rpc.transport;

/***
 * 通用的服务端接口
 * @author shy
 * @date 2023-02-13 14:45
 */
public interface RpcServer {
    /**
     * 启动服务
     * @param
     */
    void start();

    /**
     * 注册服务
     * @param service
     * @param serviceName
     * @param <T>
     */
    <T>void publishService(T service, String serviceName);
}
