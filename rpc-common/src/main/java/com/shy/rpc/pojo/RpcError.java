package com.shy.rpc.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;

/***
 * Rpc调用的各种异常信息
 * @author shy
 * @date 2023-02-13 10:49
 */
@AllArgsConstructor
@Getter
public enum RpcError {

    SERVICE_INVOCATION_FAILURE("服务调用出现失败"),
    SERVICE_NOT_FOUND("找不到对应的服务"),
    SERVICE_NOT_IMPLEMENT_ANY_INTERFACE("注册的服务未实现接口");

    private final String message;
}