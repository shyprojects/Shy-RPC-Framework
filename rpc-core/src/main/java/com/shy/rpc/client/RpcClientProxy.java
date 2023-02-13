package com.shy.rpc.client;

import com.shy.rpc.pojo.RpcRequest;
import com.shy.rpc.pojo.RpcResponse;
import lombok.AllArgsConstructor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/***
 * @author shy
 * @date 2023-02-12 19:13
 */
@AllArgsConstructor
public class RpcClientProxy implements InvocationHandler {
    private Integer port;
    private String host;

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
                .setParamTypes(method.getParameterTypes());
        RpcClient rpcClient = new RpcClient();
        Object res = ((RpcResponse) rpcClient.sendRequest(rpcRequest, port, host)).getData();
        return res;
    }
}
