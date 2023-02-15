package com.shy.rpc.register;

import java.net.InetSocketAddress;

/***
 * 服务发现通用接口
 * @author shy
 * @date 2023-02-15 10:50
 */
public interface ServiceDiscovery {
    /**
     * 根据服务名称查找服务实体的地址
     *
     * @param serviceName 服务名称
     * @return 服务实体
     */
    InetSocketAddress lookupService(String serviceName);
}
