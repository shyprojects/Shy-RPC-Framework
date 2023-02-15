package com.shy.rpc.loadbalancer;

import com.alibaba.nacos.api.naming.pojo.Instance;

import java.util.List;

/***
 * 轮询负载均衡策略
 * @author shy
 * @date 2023-02-15 10:08
 */
public class RoundRobinLoadBalancer implements LoadBalancer{
    private Integer next = 0;

    @Override
    public Instance select(List<Instance> instances) {
        Instance instance = instances.get(next++ % instances.size());
        return instance;
    }
}
