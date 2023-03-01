package com.shy.rpc.test;

import com.shy.rpc.api.pojo.User;
import com.shy.rpc.api.service.UserService;
import com.shy.rpc.transport.RpcClient;
import com.shy.rpc.api.HelloObject;
import com.shy.rpc.api.service.HelloService;
import com.shy.rpc.transport.RpcClientProxy;
import com.shy.rpc.transport.netty.client.NettyClient;

/***
 * @author shy
 * @date 2023-02-12 19:54
 */
public class TestClient {
    public static void main(String[] args) {
        RpcClient client = new NettyClient();
        RpcClientProxy proxy = new RpcClientProxy(client);
        UserService userService = proxy.getProxy(UserService.class);
        User user = userService.getById(1);
        System.out.println(user);
    }
}
