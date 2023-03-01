package com.shy.rpc.provider;

import com.shy.rpc.enumeration.RpcError;
import com.shy.rpc.exception.RpcException;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/***
 * 默认服务注册表，将服务保存到本地并且对外提供获取服务的功能
 * @author shy
 * @date 2023-02-14 14:11
 */
@Slf4j
public class DefaultServiceProvider implements ServiceProvider{


    private final static Map<String, Object> serviceMap = new ConcurrentHashMap<>();
    private final static Set<String> registeredService = ConcurrentHashMap.newKeySet();
    @Override
    public <T> void addServiceProvider(T service,String serviceName) {
        if (registeredService.contains(serviceName))
            return;
        registeredService.add(serviceName);
        Class<?>[] interfaces = service.getClass().getInterfaces();
        if (interfaces.length == 0) {
            throw new RpcException(RpcError.SERVICE_NOT_IMPLEMENT_ANY_INTERFACE);
        }
        for (Class<?> i : interfaces) {
            serviceMap.put(i.getCanonicalName(), service);
        }
        log.info("向接口: {} 注册服务: {}", interfaces, serviceName);
    }
    @Override
    public Object getServiceProvider(String serviceName) {
        Object service = serviceMap.get(serviceName);
        if (service == null) {
            throw new RpcException(RpcError.SERVICE_NOT_FOUND);
        }
        return service;
    }
}
