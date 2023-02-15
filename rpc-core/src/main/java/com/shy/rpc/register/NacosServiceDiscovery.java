package com.shy.rpc.register;

import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.shy.rpc.loadbalancer.LoadBalancer;
import com.shy.rpc.loadbalancer.RandomLoadBalancer;
import com.shy.rpc.util.NacosUtil;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.util.List;

/***
 * Nacos服务发现
 * @author shy
 * @date 2023-02-15 10:51
 */
@Slf4j
public class NacosServiceDiscovery implements ServiceDiscovery{

    private LoadBalancer loadBalancer;

    public NacosServiceDiscovery(LoadBalancer loadBalancer) {
        if(loadBalancer == null) this.loadBalancer = new RandomLoadBalancer();
        else this.loadBalancer = loadBalancer;
    }
    @Override
    public InetSocketAddress lookupService(String serviceName) {
        try {
            //根据服务名获取全部服务
            List<Instance> instances =
                    NacosUtil.getAllInstance(serviceName);
            Instance instance = loadBalancer.select(instances);
            return new InetSocketAddress(instance.getIp(),instance.getPort());
        } catch (NacosException e) {
            log.error("获取服务时有错误发生:", e);
        }
        return null;
    }
}
