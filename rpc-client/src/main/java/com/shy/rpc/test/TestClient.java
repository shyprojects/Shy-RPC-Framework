package com.shy.rpc.test;

import com.shy.rpc.RpcClient;
import com.shy.rpc.api.HelloObject;
import com.shy.rpc.api.pojo.User;
import com.shy.rpc.api.service.HelloService;
import com.shy.rpc.RpcClientProxy;
import com.shy.rpc.transport.netty.client.NettyClient;

/***
 * @author shy
 * @date 2023-02-12 19:54
 */
public class TestClient {
    public static void main(String[] args) {
        RpcClient client = new NettyClient();
        RpcClientProxy proxy = new RpcClientProxy(client);
        HelloService helloService = proxy.getProxy(HelloService.class);
        String hello = helloService.hello(new HelloObject(1, "hello"));
        System.out.println(hello);
    }
}
