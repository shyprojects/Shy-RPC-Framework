package com.shy.rpc.server;

import com.shy.rpc.enumeration.ResponseCode;
import com.shy.rpc.pojo.RpcRequest;
import com.shy.rpc.pojo.RpcResponse;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/***
 * @author shy
 * @date 2023-02-13 11:25
 */
@Slf4j
public class RequestHandler {
    public Object handler(RpcRequest rpcRequest, Object service) {
        Object res = null;
        try {
            res = invokeTargetMethod(rpcRequest,service);
        } catch (InvocationTargetException | IllegalAccessException e) {
            log.info("方法调用出现异常",e);
        }
        return res;
    }

    private Object invokeTargetMethod(RpcRequest rpcRequest, Object service) throws IllegalAccessException, InvocationTargetException {
        Method method;
        try {
            method = service.getClass().getMethod(rpcRequest.getMethodName(), rpcRequest.getParamTypes());
        } catch (NoSuchMethodException e) {
            return RpcResponse.fail(ResponseCode.METHOD_NOT_FOUND);
        }
        return method.invoke(service, rpcRequest.getParameters());
    }

}
