package com.shy.rpc.loadbalancer;

import com.alibaba.nacos.api.naming.pojo.Instance;

import java.util.List;

/***
 * 负载均衡接口
 * @author shy
 * @date 2023-02-15 10:07
 */
public interface LoadBalancer {
    Instance select(List<Instance> instances);
}