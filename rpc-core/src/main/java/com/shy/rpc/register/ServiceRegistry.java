package com.shy.rpc.register;

import java.net.InetSocketAddress;

/***
 * 服务注册通用接口
 * @author shy
 * @date 2023-02-14 14:16
 */
public interface ServiceRegistry {

    /**
     * 将一个服务注册进注册表
     *
     * @param serviceName 服务名称
     * @param inetSocketAddress 提供服务的地址
     */
    void register(String serviceName, InetSocketAddress inetSocketAddress);

}
