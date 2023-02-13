package com.shy.rpc.pojo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/***
 * 消费者请求消息封装
 * @author shy
 * @date 2023-02-12 18:42
 */
@Data
@Accessors(chain = true)
public class RpcRequest implements Serializable {
    /**
     *  调用的接口名
     */
    private String interfaceName;
    /**
     *  调用接口的方法名
     */
    private String methodName;

    /**
     * 方法的参数列表
     */
    private Object[] parameters;

    /**
     * 方法的参数类型
     */
    private Class<?>[] paramTypes;
}
