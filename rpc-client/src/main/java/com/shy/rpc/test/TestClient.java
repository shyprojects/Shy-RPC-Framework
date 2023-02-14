package com.shy.rpc.test;

import com.shy.rpc.api.HelloObject;
import com.shy.rpc.api.pojo.User;
import com.shy.rpc.api.service.HelloService;
import com.shy.rpc.RpcClientProxy;
import com.shy.rpc.api.service.UserService;
import com.shy.rpc.netty.client.NettyClient;

/***
 * @author shy
 * @date 2023-02-12 19:54
 */
public class TestClient {
    public static void main(String[] args) {
        NettyClient nettyClient = new NettyClient("127.0.0.1", 8080);
        RpcClientProxy clientProxy = new RpcClientProxy(nettyClient);
        HelloService helloService = clientProxy.getProxy(HelloService.class);
        UserService userService = clientProxy.getProxy(UserService.class);
        String res = helloService.hello(new HelloObject(1, "消息"));
        User user = userService.getById(1);
        System.out.println(user);
        System.out.println(res);
    }
}
