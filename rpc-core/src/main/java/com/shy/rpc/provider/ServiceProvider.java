package com.shy.rpc.provider;

/***
 * 注册服务并且保存/获取服务的通用接口
 * @author shy
 * @date 2023-02-14 14:10
 */
public interface ServiceProvider {

    <T>void addServiceProvider(T object,String serviceName);

    Object getServiceProvider(String serviceName);
}
