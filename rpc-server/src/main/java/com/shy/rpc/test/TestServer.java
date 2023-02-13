package com.shy.rpc.test;


import com.shy.rpc.api.service.HelloService;
import com.shy.rpc.register.DefaultServiceRegistry;
import com.shy.rpc.register.ServiceRegistry;
import com.shy.rpc.server.RpcServer;
import com.shy.rpc.test.service.HelloServiceImpl;


/***
 * @author shy
 * @date 2023-02-12 19:53
 */
public class TestServer {
    public static void main(String[] args) {
        HelloService service = new HelloServiceImpl();
        ServiceRegistry registry = new DefaultServiceRegistry();
        registry.register(service);
        RpcServer rpcServer = new RpcServer(registry);
        rpcServer.start(service,8080);
    }
}
