package com.shy.rpc.enumeration;

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
    SERVICE_NOT_IMPLEMENT_ANY_INTERFACE("注册的服务未实现接口"),
    UNKNOWN_SERIALIZER("不识别的(反)序列化器"),
    ERROR_MSG_TYPE("错误的消息类型"),
    FAILED_TO_CONNECT_TO_SERVICE_REGISTRY("连接注册中心失败"),
    REGISTER_SERVICE_FAILED("注册服务失败"),
    CLIENT_CONNECT_SERVER_FAILURE("客户端连接服务端失败"),
    SERVICE_SCAN_PACKAGE_NOT_FOUND("启动类ServiceScan注解缺失"),
    UNKNOWN_ERROR("出现未知错误"),;
    private final String message;
}