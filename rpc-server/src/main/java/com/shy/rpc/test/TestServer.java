package com.shy.rpc.test;



import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingFactory;
import com.alibaba.nacos.api.naming.NamingService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shy.rpc.RpcServer;
import com.shy.rpc.api.service.HelloService;
import com.shy.rpc.transport.netty.server.NettyServer;
import com.shy.rpc.test.service.HelloServiceImpl;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import static com.alibaba.nacos.api.annotation.NacosProperties.SERVER_ADDR;


/***
 * @author shy
 * @date 2023-02-12 19:53
 */
public class TestServer {
    public static void main(String[] args) throws NacosException {
        RpcServer server = new NettyServer("127.0.0.1",8080);
        HelloService helloService = new HelloServiceImpl();
        server.publishService(helloService,HelloService.class);
        server.start();
    }



    @AllArgsConstructor
    @Data
    @NoArgsConstructor
    static class Person implements Serializable {
        private String name;
    }
}
