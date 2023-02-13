package com.shy.rpc.register;

/***
 * @author shy
 * @date 2023-02-13 10:51
 */
public interface ServiceRegistry {

    <T>void register(T object);

    Object getService(String serviceName);
}
