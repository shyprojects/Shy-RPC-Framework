package com.shy.rpc.transport;

import com.shy.rpc.pojo.RpcRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.UUID;

/***
 * @author shy
 * @date 2023-02-12 19:13
 */
@AllArgsConstructor
@Slf4j
public class RpcClientProxy implements InvocationHandler {

    private RpcClient rpcClient;

    public  <T> T getProxy(Class<T> clazz){
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(),new Class<?>[]{clazz},this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        RpcRequest rpcRequest = new RpcRequest();
        rpcRequest
                .setInterfaceName(method.getDeclaringClass().getName())
                .setParameters(args)
                .setMethodName(method.getName())
                .setParamTypes(method.getParameterTypes())
                .setRequestId(UUID.randomUUID().toString());
        log.info("远程调用:{}#{}",rpcRequest.getInterfaceName(),rpcRequest.getMethodName());
        Object res = rpcClient.sendRequest(rpcRequest);
        return res;
    }
}
