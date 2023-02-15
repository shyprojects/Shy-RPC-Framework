package com.shy.rpc.loadbalancer;

import com.alibaba.nacos.api.naming.pojo.Instance;

import java.util.List;
import java.util.Random;

/***
 *
 * 随机负载均衡策略
 * @author shy
 * @date 2023-02-15 10:08
 */
public class RandomLoadBalancer implements LoadBalancer{
    @Override
    public Instance select(List<Instance> instances) {
        int id = new Random().nextInt(instances.size());
        return instances.get(id);
    }
}
