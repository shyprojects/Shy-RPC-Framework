package com.shy.rpc.test;

import com.shy.rpc.api.HelloObject;
import com.shy.rpc.api.service.HelloService;
import com.shy.rpc.client.RpcClientProxy;

/***
 * @author shy
 * @date 2023-02-12 19:54
 */
public class TestClient {
    public static void main(String[] args) {
        RpcClientProxy proxy = new RpcClientProxy(8080, "localhost");
        HelloService helloService = proxy.getProxy(HelloService.class);
        String res = helloService.hello(new HelloObject(10,"message"));
        System.out.println(res);
    }
}
