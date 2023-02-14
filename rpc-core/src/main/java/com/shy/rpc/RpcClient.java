package com.shy.rpc;

import com.shy.rpc.pojo.RpcRequest;

/***
 * 通用的客户端接口
 * @author shy
 * @date 2023-02-13 14:45
 */
public interface RpcClient {
    Object sendRequest(RpcRequest rpcRequest);
}
